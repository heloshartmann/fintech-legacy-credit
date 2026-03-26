package br.com.nogueiranogueira.aularefatoracao.core.ports;

import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;

public interface BureauGateway {
    int ajustarScore(int score, Pais pais);
}
