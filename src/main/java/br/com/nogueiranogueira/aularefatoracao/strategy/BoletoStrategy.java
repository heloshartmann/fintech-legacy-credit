package br.com.nogueiranogueira.aularefatoracao.strategy;

public class BoletoStrategy implements PagamentoStrategy {

    @Override
    public void pagar(double valor) {
        double valorBoleto = valor + 3.50;
        System.out.println("Registrando boleto no banco emissor...");
        System.out.println("Gerando código de barras com vencimento para 3 dias úteis.");
        System.out.println("Pagamento via Boleto processado. Total cobrado: R$ " + valorBoleto);
    }
}
