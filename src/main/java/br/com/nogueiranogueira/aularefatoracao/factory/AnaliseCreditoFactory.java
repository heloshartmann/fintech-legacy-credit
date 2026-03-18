package br.com.nogueiranogueira.aularefatoracao.factory;

import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategy;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPF;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPJ;
import org.springframework.stereotype.Service;

@Service
public class AnaliseCreditoFactory {

    public AnaliseStrategy criarEstrategia(TipoConta tipo) {
        return switch (tipo) {
            case PF -> new AnaliseStrategyPF();
            case PJ -> new AnaliseStrategyPJ();
        };
    }
}