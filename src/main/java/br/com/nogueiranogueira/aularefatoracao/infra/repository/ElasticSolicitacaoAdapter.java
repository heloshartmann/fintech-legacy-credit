package br.com.nogueiranogueira.aularefatoracao.infra.repository;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.ports.SolicitacaoRepositoryPort;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ElasticSolicitacaoAdapter implements SolicitacaoRepositoryPort {

    private static final AtomicLong SEQUENCE = new AtomicLong(1);
    private static final Map<String, Map<String, Object>> INDICE_SOLICITACOES = new ConcurrentHashMap<>();

    @Override
    public void salvar(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        String documentId = "solicitacao-" + SEQUENCE.getAndIncrement();
        INDICE_SOLICITACOES.put(documentId, SolicitacaoRepositoryPayloadFormatter.toMap(solicitacao, resultado));
    }
}
