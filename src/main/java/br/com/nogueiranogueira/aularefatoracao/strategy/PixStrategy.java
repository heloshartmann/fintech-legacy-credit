package br.com.nogueiranogueira.aularefatoracao.strategy;

public class PixStrategy implements PagamentoStrategy {

    @Override
    public void pagar(double valor) {
        double valorComDesconto = valor * 0.95;
        System.out.println("Calculando desconto do PIX...");
        System.out.println("Gerando chave Copia e Cola.");
        System.out.println("Pagamento via PIX processado. Total cobrado: R$ " + valorComDesconto);
    }
}
