package br.com.nogueiranogueira.aularefatoracao.service;

import org.springframework.stereotype.Service;

@Service
public class ProcessadorVendaService {

    private static final String CEP_PARANA_PREFIXO = "85";
    private static final String CEP_SAO_PAULO_PREFIXO = "01";
    private static final String TIPO_PRODUTO = "PRODUTO";
    private static final String TIPO_SERVICO = "SERVICO";
    private static final double FRETE_PARANA = 10.0;
    private static final double FRETE_SAO_PAULO = 20.0;
    private static final double FRETE_OUTROS = 50.0;
    private static final double ALIQUOTA_PRODUTO = 0.18;
    private static final double ALIQUOTA_SERVICO = 0.05;

    public void processar(String cliente, double valor, String tipo, String cep) {
        if (!clienteValido(cliente)) {
            System.out.println("Erro: Cliente inválido");
            return;
        }

        if (!valorValido(valor)) {
            System.out.println("Erro: Valor inválido");
            return;
        }

        double frete = calcularFrete(cep);
        double imposto = calcularImposto(valor, tipo);
        double valorTotal = valor + frete + imposto;

        persistirPedido(cliente, valorTotal);
        notificarCliente(cliente);
    }

    private boolean clienteValido(String cliente) {
        return cliente != null && !cliente.isBlank();
    }

    private boolean valorValido(double valor) {
        return valor > 0;
    }

    private double calcularFrete(String cep) {
        if (cep == null || cep.isBlank()) {
            return FRETE_OUTROS;
        }

        if (cep.startsWith(CEP_PARANA_PREFIXO)) {
            return FRETE_PARANA;
        }

        if (cep.startsWith(CEP_SAO_PAULO_PREFIXO)) {
            return FRETE_SAO_PAULO;
        }

        return FRETE_OUTROS;
    }

    private double calcularImposto(double valor, String tipo) {
        if (TIPO_PRODUTO.equalsIgnoreCase(tipo)) {
            return valor * ALIQUOTA_PRODUTO;
        }

        if (TIPO_SERVICO.equalsIgnoreCase(tipo)) {
            return valor * ALIQUOTA_SERVICO;
        }

        return 0;
    }

    private void persistirPedido(String cliente, double valorTotal) {
        System.out.println("Conectando no banco...");
        System.out.println("INSERT INTO PEDIDOS VALUES (" + cliente + ", " + valorTotal + ")");
    }

    private void notificarCliente(String cliente) {
        System.out.println("Enviando recibo para " + cliente);
    }
}
