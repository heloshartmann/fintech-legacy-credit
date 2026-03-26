package br.com.nogueiranogueira.aularefatoracao.service.credito;

import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnaliseCreditoFactory {

    private final List<AnaliseStrategy> estrategias;

    public AnaliseCreditoFactory(List<AnaliseStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public AnaliseStrategy criarEstrategia(TipoConta tipoConta) {
        if (tipoConta == null || tipoConta == TipoConta.DESCONHECIDO) {
            return null;
        }

        return estrategias.stream()
                .filter(strategy -> strategy.elegivel(tipoConta))
                .findFirst()
                .orElse(null);
    }
}
