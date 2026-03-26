package br.com.nogueiranogueira.aularefatoracao.core.analise;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Curp;
import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;
import org.springframework.stereotype.Component;

@Component
public final class AnaliseMexicoStrategy implements AnaliseStrategy {

    private static final int SCORE_MINIMO = 580;

    @Override
    public Pais pais() {
        return Pais.MX;
    }

    @Override
    public ResultadoAnalise analisar(SolicitacaoCreditoCore solicitacao, BureauGateway bureauGateway, ScoreEngine scoreEngine) {
        if (!(solicitacao.documento() instanceof Curp)) {
            return ResultadoAnalise.reprovado("Documento invalido para MX");
        }
        if (solicitacao.negativado()) {
            return ResultadoAnalise.reprovado("Cliente negativado");
        }
        int scoreAjustado = bureauGateway.ajustarScore(solicitacao.score(), Pais.MX);
        boolean aprovado = scoreEngine.aprovar(solicitacao.valor(), scoreAjustado, SCORE_MINIMO);
        return aprovado ? ResultadoAnalise.aprovadoComSucesso() : ResultadoAnalise.reprovado("Score insuficiente para MX");
    }
}
