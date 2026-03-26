package br.com.nogueiranogueira.aularefatoracao.core.processamento;

import br.com.nogueiranogueira.aularefatoracao.core.analise.AnaliseStrategy;
import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class AnaliseStrategyFactory {

    private final Map<Pais, AnaliseStrategy> estrategiasPorPais = new EnumMap<>(Pais.class);

    public AnaliseStrategyFactory(List<AnaliseStrategy> estrategias) {
        for (AnaliseStrategy estrategia : estrategias) {
            estrategiasPorPais.put(estrategia.pais(), estrategia);
        }
    }

    public AnaliseStrategy obter(Pais pais) {
        AnaliseStrategy strategy = estrategiasPorPais.get(pais);
        if (strategy == null) {
            throw new IllegalArgumentException("Nao existe estrategia para pais " + pais);
        }
        return strategy;
    }
}
