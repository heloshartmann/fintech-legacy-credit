package br.com.nogueiranogueira.aularefatoracao.infra.repository;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.ports.SolicitacaoRepositoryPort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DbSolicitacaoAdapter implements SolicitacaoRepositoryPort {

    private static final List<Map<String, Object>> TABELA_SOLICITACOES =
            Collections.synchronizedList(new ArrayList<>());

    @Override
    public void salvar(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        TABELA_SOLICITACOES.add(SolicitacaoRepositoryPayloadFormatter.toMap(solicitacao, resultado));
    }
}
