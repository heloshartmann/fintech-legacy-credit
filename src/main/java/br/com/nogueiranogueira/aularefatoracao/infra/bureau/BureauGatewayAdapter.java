package br.com.nogueiranogueira.aularefatoracao.infra.bureau;

import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;
import br.com.nogueiranogueira.aularefatoracao.core.ports.BureauGateway;
import org.springframework.stereotype.Component;

@Component
public class BureauGatewayAdapter implements BureauGateway {

    @Override
    public int ajustarScore(int score, Pais pais) {
        return switch (pais) {
            case BR -> score + 20;
            case MX -> score + 10;
            case US -> score + 15;
            case PT -> score + 10;
        };
    }
}
