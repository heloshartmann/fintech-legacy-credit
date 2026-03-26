package br.com.nogueiranogueira.aularefatoracao.infra.validator;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Documento;
import br.com.nogueiranogueira.aularefatoracao.core.documento.Nif;
import br.com.nogueiranogueira.aularefatoracao.core.ports.DocumentValidator;
import org.springframework.stereotype.Component;

@Component
public class NifValidator implements DocumentValidator {

    @Override
    public boolean validar(Documento documento) {
        if (!(documento instanceof Nif nif)) {
            return false;
        }
        String valor = nif.valor();
        return isValidNif(valor);
    }

    private boolean isValidNif(String nif) {
        if (nif == null || nif.length() != 9 || !nif.matches("\\d{9}")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 8; i++) {
            int digit = Character.getNumericValue(nif.charAt(i));
            sum += digit * (9 - i);
        }

        int checkDigit = sum % 11;
        if (checkDigit == 10) {
            checkDigit = 0;
        }

        int lastDigit = Character.getNumericValue(nif.charAt(8));
        return checkDigit == lastDigit;
    }
}