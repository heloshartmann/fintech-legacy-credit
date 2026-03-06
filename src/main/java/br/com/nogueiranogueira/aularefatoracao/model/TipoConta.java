package br.com.nogueiranogueira.aularefatoracao.model;

import java.util.Arrays;

public enum TipoConta {
    PF,
    PJ,
    DESCONHECIDO;

    public static TipoConta from(String valor) {
        if (valor == null || valor.isBlank()) {
            return DESCONHECIDO;
        }

        return Arrays.stream(values())
                .filter(tipo -> tipo != DESCONHECIDO)
                .filter(tipo -> tipo.name().equalsIgnoreCase(valor))
                .findFirst()
                .orElse(DESCONHECIDO);
    }
}
