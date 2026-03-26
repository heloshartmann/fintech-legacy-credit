package br.com.nogueiranogueira.aularefatoracao.dto;

import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;

import java.math.BigDecimal;

public record SolicitacaoCreditoRecord(
        String cliente,
        BigDecimal valor,
        int score,
        boolean negativado,
        TipoConta tipo,
        Pais pais
) {
}
