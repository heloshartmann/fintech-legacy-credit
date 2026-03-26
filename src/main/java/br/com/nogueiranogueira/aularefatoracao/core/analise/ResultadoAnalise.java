package br.com.nogueiranogueira.aularefatoracao.core.analise;

public record ResultadoAnalise(boolean aprovado, String motivo) {

    public static ResultadoAnalise aprovadoComSucesso() {
        return new ResultadoAnalise(true, null);
    }

    public static ResultadoAnalise reprovado(String motivo) {
        return new ResultadoAnalise(false, motivo);
    }
}
