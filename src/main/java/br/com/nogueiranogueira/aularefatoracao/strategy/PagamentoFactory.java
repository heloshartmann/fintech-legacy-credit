package br.com.nogueiranogueira.aularefatoracao.strategy;

public class PagamentoFactory {

    public PagamentoStrategy criarEstrategia(String metodo) {
        if (metodo == null || metodo.trim().isEmpty()) {
            throw new IllegalArgumentException("Método de pagamento não informado!");
        }
        return switch (metodo.toUpperCase()) {
            case "PIX" -> new PixStrategy();
            case "CARTAO_CREDITO" -> new CartaoCreditoStrategy();
            case "PAYPAL" -> new PaypalStrategy();
            case "BOLETO" -> new BoletoStrategy();
            default -> throw new IllegalArgumentException("Método de pagamento não suportado: " + metodo);
        };
    }
}
