package br.com.nogueiranogueira.aularefatoracao.core.processamento;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.ports.SolicitacaoRepositoryPort;
import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;
import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.repository.SolicitacaoCreditoRepository;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoRepositoryBridge implements SolicitacaoRepositoryPort {

    private final SolicitacaoCreditoRepository repository;

    public SolicitacaoRepositoryBridge(SolicitacaoCreditoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        SolicitacaoCredito entidade = new SolicitacaoCredito();
        entidade.setCliente(solicitacao.cliente());
        entidade.setValor(solicitacao.valor().doubleValue());
        entidade.setScore(solicitacao.score());
        entidade.setNegativado(solicitacao.negativado());
        entidade.setTipoConta(mapTipoConta(solicitacao.pais()));
        entidade.setAprovado(resultado.aprovado());
        entidade.setMotivoReprovacao(resultado.motivo());
        repository.save(entidade);
    }

    private TipoConta mapTipoConta(Pais pais) {
        return switch (pais) {
            case BR, MX -> TipoConta.PF;
            case US -> TipoConta.PJ;
        };
    }
}
