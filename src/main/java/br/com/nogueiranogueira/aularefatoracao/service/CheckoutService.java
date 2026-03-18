package br.com.nogueiranogueira.aularefatoracao.service;

import br.com.nogueiranogueira.aularefatoracao.strategy.PagamentoFactory;
import br.com.nogueiranogueira.aularefatoracao.strategy.PagamentoStrategy;

public class CheckoutService {

    private final PagamentoFactory pagamentoFactory;

    public CheckoutService(PagamentoFactory pagamentoFactory) {
        this.pagamentoFactory = pagamentoFactory;
    }

    /**
     * BAD SMELLS PRESENTES NESTE CÓDIGO:
     * 1. Violação do OCP (Open/Closed Principle): Para adicionar "CRIPTO", precisamos modificar este método.
     * 2. Violação do SRP (Single Responsibility Principle): A classe valida, calcula descontos/taxas e simula integrações.
     * 3. Magic Numbers: O que é 0.95? O que é 1.05? (Deveriam ser constantes).
     * 4. Código Duplicado: A estrutura de log e impressão se repete muito.
     */
    public void pagar(double valor, String metodo) {

        System.out.println("=== Iniciando processamento de pagamento ===");

        PagamentoStrategy strategy = pagamentoFactory.criarEstrategia(metodo);
        strategy.pagar(valor);

        System.out.println("=== Finalizando transação ===");
    }
}