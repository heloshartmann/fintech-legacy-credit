package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.repository.SolicitacaoCreditoRepository;
import br.com.nogueiranogueira.aularefatoracao.service.credito.AnaliseStrategy;
import br.com.nogueiranogueira.aularefatoracao.service.credito.AnaliseStrategyPF;
import br.com.nogueiranogueira.aularefatoracao.service.credito.AnaliseStrategyPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ProcessadorCreditoService {

    private static final int SCORE_MINIMO = 500;
    private static final double VALOR_PADRAO_LOTE = 1_000.0;
    private static final int SCORE_PADRAO_LOTE = 600;
    private static final boolean NEGATIVADO_PADRAO_LOTE = false;
    private static final TipoConta TIPO_CONTA_PADRAO_LOTE = TipoConta.PF;
    private static final String MOTIVO_VALOR_INVALIDO = "Valor inválido";
    private static final String MOTIVO_CLIENTE_NEGATIVADO = "Cliente negativado";
    private static final String MOTIVO_SCORE_BAIXO = "Score baixo";
    private static final String MOTIVO_TIPO_CONTA_DESCONHECIDO = "Tipo de conta desconhecido";
    private static final String MOTIVO_REGRAS_DA_ESTRATEGIA = "Reprovado pelas regras da estratégia";

    private final List<AnaliseStrategy> estrategias;
    private final SolicitacaoCreditoRepository repository;

    @Autowired
    public ProcessadorCreditoService(List<AnaliseStrategy> estrategias, SolicitacaoCreditoRepository repository) {
        this.estrategias = estrategias;
        this.repository = repository;
    }

    public ProcessadorCreditoService() {
        this.estrategias = List.of(new AnaliseStrategyPF(), new AnaliseStrategyPJ());
        this.repository = null;
    }

    public boolean processarSolicitacao(SolicitacaoAnalise solicitacao) {
        if (solicitacao.valor() <= 0) {
            salvarSolicitacao(solicitacao, false, MOTIVO_VALOR_INVALIDO);
            return false;
        }

        if (solicitacao.negativado()) {
            salvarSolicitacao(solicitacao, false, MOTIVO_CLIENTE_NEGATIVADO);
            return false;
        }

        if (solicitacao.score() <= SCORE_MINIMO) {
            salvarSolicitacao(solicitacao, false, MOTIVO_SCORE_BAIXO);
            return false;
        }

        if (solicitacao.tipoContaInvalido()) {
            salvarSolicitacao(solicitacao, false, MOTIVO_TIPO_CONTA_DESCONHECIDO);
            return false;
        }

        AnaliseStrategy strategy = encontrarStrategyElegivel(solicitacao.tipoConta());
        if (strategy == null) {
            salvarSolicitacao(solicitacao, false, MOTIVO_TIPO_CONTA_DESCONHECIDO);
            return false;
        }

        boolean aprovado = strategy.analisar(solicitacao);
        salvarSolicitacao(solicitacao, aprovado, aprovado ? null : MOTIVO_REGRAS_DA_ESTRATEGIA);
        return aprovado;
    }

    public void processarLote(List<String> clientes) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Boolean>> tarefas = clientes.stream()
                .map(cliente -> executor.submit(() -> processarSolicitacao(new SolicitacaoAnalise(
                    cliente,
                    VALOR_PADRAO_LOTE,
                    SCORE_PADRAO_LOTE,
                    NEGATIVADO_PADRAO_LOTE,
                    TIPO_CONTA_PADRAO_LOTE))))
                    .toList();

            for (Future<Boolean> tarefa : tarefas) {
                try {
                    tarefa.get();
                } catch (Exception e) {
                    throw new IllegalStateException("Erro ao processar cliente do lote", e);
                }
            }
        }
    }

    private AnaliseStrategy encontrarStrategyElegivel(TipoConta tipoConta) {
        if (tipoConta == null || tipoConta == TipoConta.DESCONHECIDO) {
            return null;
        }

        return estrategias.stream()
                .filter(strategy -> strategy.elegivel(tipoConta))
                .findFirst()
                .orElse(null);
    }

    private void salvarSolicitacao(SolicitacaoAnalise solicitacaoAnalise,
                                  boolean aprovado,
                                  String motivoReprovacao) {
        if (repository == null) {
            return;
        }

        SolicitacaoCredito solicitacao = new SolicitacaoCredito();
        solicitacao.setCliente(solicitacaoAnalise.cliente());
        solicitacao.setValor(solicitacaoAnalise.valor());
        solicitacao.setScore(solicitacaoAnalise.score());
        solicitacao.setNegativado(solicitacaoAnalise.negativado());
        solicitacao.setTipoConta(solicitacaoAnalise.tipoConta());
        solicitacao.setAprovado(aprovado);
        solicitacao.setMotivoReprovacao(motivoReprovacao);
        repository.save(solicitacao);
    }
}
