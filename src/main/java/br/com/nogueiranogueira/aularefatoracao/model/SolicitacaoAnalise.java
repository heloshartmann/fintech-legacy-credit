package br.com.nogueiranogueira.aularefatoracao.model;

import java.util.Objects;

public record SolicitacaoAnalise(
        String cliente,
        double valor,
        int score,
        boolean negativado,
    TipoConta tipoConta) {

    public SolicitacaoAnalise {
        Objects.requireNonNull(cliente, "cliente nao pode ser nulo");
        Objects.requireNonNull(tipoConta, "tipoConta nao pode ser nulo");
    }

    public static SolicitacaoAnalise of(String cliente,
                                        double valor,
                                        int score,
                                        boolean negativado,
                                        String tipoConta) {
        String tipoContaNormalizado = tipoConta == null ? "" : tipoConta.trim();
        return new SolicitacaoAnalise(
                cliente,
                valor,
                score,
                negativado,
            TipoConta.from(tipoContaNormalizado));
    }

    public boolean tipoContaInvalido() {
        return tipoConta == TipoConta.DESCONHECIDO;
    }
}
