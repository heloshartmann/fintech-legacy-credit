package br.com.nogueiranogueira.aularefatoracao.infra.score;

import br.com.nogueiranogueira.aularefatoracao.core.ports.ScoreEngine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConservadorScoreEngine implements ScoreEngine {

    private static final BigDecimal LIMITE_CONSERVADOR = new BigDecimal("50000");

    @Override
    public boolean aprovar(BigDecimal valor, int scoreAjustado, int scoreMinimo) {
        return scoreAjustado >= scoreMinimo && valor.compareTo(LIMITE_CONSERVADOR) <= 0;
    }
}
