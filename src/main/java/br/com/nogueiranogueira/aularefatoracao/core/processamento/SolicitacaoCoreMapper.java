package br.com.nogueiranogueira.aularefatoracao.core.processamento;

import br.com.nogueiranogueira.aularefatoracao.core.analise.Pais;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.documento.Cpf;
import br.com.nogueiranogueira.aularefatoracao.core.documento.Curp;
import br.com.nogueiranogueira.aularefatoracao.core.documento.Documento;
import br.com.nogueiranogueira.aularefatoracao.core.documento.Ssn;
import br.com.nogueiranogueira.aularefatoracao.dto.SolicitacaoCreditoRecord;
import br.com.nogueiranogueira.aularefatoracao.dto.TipoConta;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoCoreMapper {

    public SolicitacaoCreditoCore toCore(SolicitacaoCreditoRecord solicitacao) {
    Pais pais = mapPais(solicitacao);
        Documento documento = mapDocumento(solicitacao.cliente(), pais);
        return new SolicitacaoCreditoCore(
                solicitacao.cliente(),
                solicitacao.valor(),
                solicitacao.score(),
                solicitacao.negativado(),
                documento,
                pais
        );
    }

    private Pais mapPais(SolicitacaoCreditoRecord solicitacao) {
        if (solicitacao.pais() != null) {
            return solicitacao.pais();
        }
        TipoConta tipoConta = solicitacao.tipo();
        return switch (tipoConta) {
            case PF -> Pais.BR;
            case PJ -> Pais.US;
        };
    }

    private Documento mapDocumento(String cliente, Pais pais) {
        String documentoId = cliente == null ? "NA" : cliente.trim();
        return switch (pais) {
            case BR -> new Cpf(documentoId);
            case MX -> new Curp(documentoId);
            case US -> new Ssn(documentoId);
        };
    }
}
