package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoCredito;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.repository.SolicitacaoCreditoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnaliseCreditoService {

    private final ProcessadorCreditoService processadorCreditoService;
    private final SolicitacaoCreditoRepository solicitacaoCreditoRepository;

    @Autowired
    public AnaliseCreditoService(ProcessadorCreditoService processadorCreditoService,
                                 SolicitacaoCreditoRepository solicitacaoCreditoRepository) {
        this.processadorCreditoService = processadorCreditoService;
        this.solicitacaoCreditoRepository = solicitacaoCreditoRepository;
    }

    public AnaliseCreditoService() {
        this.processadorCreditoService = new ProcessadorCreditoService();
        this.solicitacaoCreditoRepository = null;
    }

    public boolean analisarSolicitacao(SolicitacaoAnalise solicitacao) {
        return processadorCreditoService.processarSolicitacao(solicitacao);
    }

    public boolean analisarSolicitacao(String cliente, double valor, int score, boolean negativado, String tipoConta) {
        return analisarSolicitacao(SolicitacaoAnalise.of(cliente, valor, score, negativado, tipoConta));
    }

    public void processarLote(List<String> clientes) {
        processadorCreditoService.processarLote(clientes);
    }

    public List<SolicitacaoCredito> obterSolicitacoesPorCliente(String cliente) {
        return solicitacaoCreditoRepository.findByCliente(cliente);
    }

    public List<SolicitacaoCredito> obterSolicitacoesAprovadas() {
        return solicitacaoCreditoRepository.findByAprovado(true);
    }

    public List<SolicitacaoCredito> obterSolicitacoesReprovadas() {
        return solicitacaoCreditoRepository.findByAprovado(false);
    }

    public Long obterTotalAprovadosPorTipo(String tipoConta) {
        return solicitacaoCreditoRepository.countAprovadosByTipoConta(TipoConta.from(tipoConta));
    }

    public List<SolicitacaoCredito> obterSolicitacoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return solicitacaoCreditoRepository.findByDataSolicitacaoBetween(inicio, fim);
    }
}