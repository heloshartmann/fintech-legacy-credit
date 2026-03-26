package br.com.nogueiranogueira.aularefatoracao.core.ports;

import br.com.nogueiranogueira.aularefatoracao.core.documento.Documento;

public interface DocumentValidator {
    boolean validar(Documento documento);
}