package br.com.nogueiranogueira.aularefatoracao.core.ports;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;

public interface SolicitacaoRepositoryPort {
    void salvar(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado);
}
