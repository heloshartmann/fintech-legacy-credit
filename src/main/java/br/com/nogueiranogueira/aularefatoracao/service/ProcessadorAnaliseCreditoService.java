package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import br.com.nogueiranogueira.aularefatoracao.core.processamento.ProcessadorCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.processamento.SolicitacaoCoreMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;

@Service
public class ProcessadorAnaliseCreditoService {

    private final ProcessadorCreditoCore processadorCreditoCore;
    private final SolicitacaoCoreMapper solicitacaoCoreMapper;

    public ProcessadorAnaliseCreditoService(ProcessadorCreditoCore processadorCreditoCore,
                                            SolicitacaoCoreMapper solicitacaoCoreMapper) {
        this.processadorCreditoCore = processadorCreditoCore;
        this.solicitacaoCoreMapper = solicitacaoCoreMapper;
    }

    public void processarLote(List<SolicitacaoCreditoRecord> solicitacoes) {
        System.out.println("=== Iniciando Processamento Paralelo (Virtual Threads) ===");
        long inicio = System.currentTimeMillis();

        // Tenta criar uma thread virtual para CADA solicitação.
        // Se houver 10.000 solicitações, ele dispara as 10.000 de uma vez.
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (SolicitacaoCreditoRecord solicitacao : solicitacoes) {
                executor.submit(() -> processarIndividual(solicitacao));
            }

        } // O try-with-resources aguarda TODAS as threads terminarem aqui.

        long fim = System.currentTimeMillis();
        System.out.println("Tempo Total: " + (fim - inicio) + " ms");
    }

    public void processarIndividual(SolicitacaoCreditoRecord solicitacao) {
        processadorCreditoCore.processar(solicitacaoCoreMapper.toCore(solicitacao));
    }
}