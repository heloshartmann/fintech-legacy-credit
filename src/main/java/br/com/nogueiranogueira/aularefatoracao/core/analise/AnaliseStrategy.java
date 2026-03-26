package br.com.nogueiranogueira.aularefatoracao.core.analise;

import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;

public sealed interface AnaliseStrategy permits AnaliseBrasilStrategy, AnaliseMexicoStrategy, AnaliseUsaStrategy {

    Pais pais();

    ResultadoAnalise analisar(SolicitacaoCreditoCore solicitacao, BureauGateway bureauGateway, ScoreEngine scoreEngine);
}
