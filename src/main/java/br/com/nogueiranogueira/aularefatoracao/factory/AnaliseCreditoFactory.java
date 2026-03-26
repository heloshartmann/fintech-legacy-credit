package br.com.nogueiranogueira.aularefatoracao.factory;

import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategy;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPF;
import br.com.nogueiranogueira.aularefatoracao.strategy.AnaliseStrategyPJ;
import org.springframework.stereotype.Service;

@Service
public class AnaliseCreditoFactory {

    public AnaliseStrategy criarEstrategia(TipoConta tipo, Pais pais) {
        // Reutiliza estratégias PF/PJ para todos os países por padrão.
        // Se Portugal tiver regras diferentes, crie AnaliseStrategyPTPF/PTPJ e descomente/adapte abaixo.
        /*
        if (pais == Pais.PT) {
            return switch (tipo) {
                case PF -> new AnaliseStrategyPTPF();  // Estratégia específica para PF em PT
                case PJ -> new AnaliseStrategyPTPJ();  // Estratégia específica para PJ em PT
            };
        }
        */
        return switch (tipo) {
            case PF -> new AnaliseStrategyPF();
            case PJ -> new AnaliseStrategyPJ();
        };
    }
}