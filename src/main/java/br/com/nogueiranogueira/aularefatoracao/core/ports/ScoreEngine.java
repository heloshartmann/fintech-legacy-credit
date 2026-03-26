package br.com.nogueiranogueira.aularefatoracao.core.ports;

import java.math.BigDecimal;

public interface ScoreEngine {
    boolean aprovar(BigDecimal valor, int scoreAjustado, int scoreMinimo);
}
