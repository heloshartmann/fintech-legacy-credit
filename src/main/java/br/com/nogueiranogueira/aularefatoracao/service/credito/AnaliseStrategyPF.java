package br.com.nogueiranogueira.aularefatoracao.service.credito;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class AnaliseStrategyPF implements AnaliseStrategy {

    private static final double LIMITE_VALOR_ALTO_PF = 5_000.0;
    private static final int SCORE_MINIMO_PARA_VALOR_ALTO_PF = 800;

    @Override
    public boolean elegivel(TipoConta tipoConta) {
        return TipoConta.PF == tipoConta;
    }

    @Override
    public boolean analisar(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > LIMITE_VALOR_ALTO_PF && solicitacao.score() < SCORE_MINIMO_PARA_VALOR_ALTO_PF) {
            return false;
        }
        return !isFimDeSemana();
    }

    private boolean isFimDeSemana() {
        DayOfWeek diaAtual = LocalDate.now().getDayOfWeek();
        return diaAtual == DayOfWeek.SATURDAY || diaAtual == DayOfWeek.SUNDAY;
    }
}
