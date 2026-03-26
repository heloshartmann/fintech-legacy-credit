package br.com.nogueiranogueira.aularefatoracao.core.processamento;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;
import br.com.nogueiranogueira.aularefatoracao.core.ports.SolicitacaoRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class ProcessadorCreditoCore {

    private final AnaliseStrategyFactory strategyFactory;
    private final BureauGateway bureauGateway;
    private final ScoreEngine scoreEngine;
    private final SolicitacaoRepositoryPort repositoryPort;

    public ProcessadorCreditoCore(AnaliseStrategyFactory strategyFactory,
                                  BureauGateway bureauGateway,
                                  ScoreEngine scoreEngine,
                                  SolicitacaoRepositoryPort repositoryPort) {
        this.strategyFactory = strategyFactory;
        this.bureauGateway = bureauGateway;
        this.scoreEngine = scoreEngine;
        this.repositoryPort = repositoryPort;
    }

    public ResultadoAnalise processar(SolicitacaoCreditoCore solicitacao) {
        ResultadoAnalise resultado = strategyFactory
                .obter(solicitacao.pais())
                .analisar(solicitacao, bureauGateway, scoreEngine);
        repositoryPort.salvar(solicitacao, resultado);
        return resultado;
    }
}
