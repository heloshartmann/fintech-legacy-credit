package br.com.nogueiranogueira.aularefatoracao.core.analise;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Cpf;
import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;
import org.springframework.stereotype.Component;

@Component
public final class AnaliseBrasilStrategy implements AnaliseStrategy {

    private static final int SCORE_MINIMO = 600;

    @Override
    public Pais pais() {
        return Pais.BR;
    }

    @Override
    public ResultadoAnalise analisar(SolicitacaoCreditoCore solicitacao, BureauGateway bureauGateway, ScoreEngine scoreEngine) {
        if (!(solicitacao.documento() instanceof Cpf)) {
            return ResultadoAnalise.reprovado("Documento invalido para BR");
        }
        if (solicitacao.negativado()) {
            return ResultadoAnalise.reprovado("Cliente negativado");
        }
        int scoreAjustado = bureauGateway.ajustarScore(solicitacao.score(), Pais.BR);
        boolean aprovado = scoreEngine.aprovar(solicitacao.valor(), scoreAjustado, SCORE_MINIMO);
        return aprovado ? ResultadoAnalise.aprovadoComSucesso() : ResultadoAnalise.reprovado("Score insuficiente para BR");
    }
}
