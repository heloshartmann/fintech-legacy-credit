package br.com.nogueiranogueira.aularefatoracao.infra.repository;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;
import br.com.nogueiranogueira.aularefatoracao.core.ports.SolicitacaoRepositoryPort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ArquivoSolicitacaoAdapter implements SolicitacaoRepositoryPort {

    private static final Path DEFAULT_ARQUIVO = Path.of("target", "solicitacoes.log");

    private final Path arquivoSaida;

    public ArquivoSolicitacaoAdapter() {
        this(DEFAULT_ARQUIVO);
    }

    public ArquivoSolicitacaoAdapter(Path arquivoSaida) {
        this.arquivoSaida = arquivoSaida;
    }

    @Override
    public void salvar(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        String linha = SolicitacaoRepositoryPayloadFormatter.toJsonLike(solicitacao, resultado) + System.lineSeparator();
        try {
            Path parent = arquivoSaida.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(
                    arquivoSaida,
                    linha,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Falha ao persistir solicitacao em arquivo", exception);
        }
    }
}
