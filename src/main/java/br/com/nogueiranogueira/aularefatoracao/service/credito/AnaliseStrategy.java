package br.com.nogueiranogueira.aularefatoracao.service.credito;

import br.com.nogueiranogueira.aularefatoracao.model.SolicitacaoAnalise;
import br.com.nogueiranogueira.aularefatoracao.model.TipoConta;

public interface AnaliseStrategy {

    boolean elegivel(TipoConta tipoConta);

    boolean analisar(SolicitacaoAnalise solicitacao);
}
