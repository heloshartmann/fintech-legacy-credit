package br.com.nogueiranogueira.aularefatoracao.service.credito;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;

import org.springframework.stereotype.Component;

@Component
public class AnaliseStrategyPJ implements AnaliseStrategy {

    private static final double LIMITE_VALOR_ALTO_PJ = 50_000.0;
    private static final int SCORE_MINIMO_PARA_VALOR_ALTO_PJ = 700;

    @Override
    public boolean elegivel(TipoConta tipoConta) {
        return TipoConta.PJ == tipoConta;
    }

    @Override
    public boolean analisar(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() > LIMITE_VALOR_ALTO_PJ && solicitacao.score() < SCORE_MINIMO_PARA_VALOR_ALTO_PJ) {
            return false;
        }
        return true;
    }
}
