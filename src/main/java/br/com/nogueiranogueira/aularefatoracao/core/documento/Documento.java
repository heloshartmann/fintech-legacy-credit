package br.com.nogueiranogueira.aularefatoracao.core.documento;

public sealed interface Documento permits Cpf, Curp, Ssn, Nif {
    String valor();
}
