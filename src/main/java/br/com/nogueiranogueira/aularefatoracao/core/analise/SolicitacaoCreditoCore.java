package br.com.nogueiranogueira.aularefatoracao.core.analise;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Documento;

import java.math.BigDecimal;

public record SolicitacaoCreditoCore(
        String cliente,
        BigDecimal valor,
        int score,
        boolean negativado,
        Documento documento,
        Pais pais
) {
}
