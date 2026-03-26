package br.com.nogueiranogueira.aularefatoracao.core.analise;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Ssn;
import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;
import org.springframework.stereotype.Component;

@Component
public final class AnaliseUsaStrategy implements AnaliseStrategy {

    private static final int SCORE_MINIMO = 620;

    @Override
    public Pais pais() {
        return Pais.US;
    }

    @Override
    public ResultadoAnalise analisar(SolicitacaoCreditoCore solicitacao, BureauGateway bureauGateway, ScoreEngine scoreEngine) {
        if (!(solicitacao.documento() instanceof Ssn)) {
            return ResultadoAnalise.reprovado("Documento invalido para US");
        }
        if (solicitacao.negativado()) {
            return ResultadoAnalise.reprovado("Cliente negativado");
        }
        int scoreAjustado = bureauGateway.ajustarScore(solicitacao.score(), Pais.US);
        boolean aprovado = scoreEngine.aprovar(solicitacao.valor(), scoreAjustado, SCORE_MINIMO);
        return aprovado ? ResultadoAnalise.aprovadoComSucesso() : ResultadoAnalise.reprovado("Score insuficiente para US");
    }
}
