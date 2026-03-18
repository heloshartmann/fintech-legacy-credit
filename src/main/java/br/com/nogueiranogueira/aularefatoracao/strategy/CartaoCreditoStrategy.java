package br.com.nogueiranogueira.aularefatoracao.strategy;

public class CartaoCreditoStrategy implements PagamentoStrategy {

    @Override
    public void pagar(double valor) {
        double valorComAcrescimo = valor * 1.05;
        System.out.println("Conectando com a adquirente (Cielo/Rede)...");
        System.out.println("Validando limite e risco de fraude.");
        System.out.println("Pagamento via Cartão processado. Total cobrado: R$ " + valorComAcrescimo);
    }
}
