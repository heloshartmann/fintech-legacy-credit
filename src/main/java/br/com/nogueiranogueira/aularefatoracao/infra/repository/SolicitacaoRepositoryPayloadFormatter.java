package br.com.nogueiranogueira.aularefatoracao.infra.repository;

import br.com.nogueiranogueira.aularefatoracao.core.analise.ResultadoAnalise;
import br.com.nogueiranogueira.aularefatoracao.core.analise.SolicitacaoCreditoCore;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

final class SolicitacaoRepositoryPayloadFormatter {

    private static final DateTimeFormatter ISO_UTC = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private SolicitacaoRepositoryPayloadFormatter() {
    }

    static Map<String, Object> toMap(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("timestamp", ISO_UTC.format(Instant.now().atOffset(ZoneOffset.UTC)));
        payload.put("cliente", solicitacao.cliente());
        payload.put("valor", solicitacao.valor());
        payload.put("score", solicitacao.score());
        payload.put("negativado", solicitacao.negativado());
        payload.put("pais", solicitacao.pais() != null ? solicitacao.pais().name() : null);
        payload.put("documento", solicitacao.documento() != null ? solicitacao.documento().valor() : null);
        payload.put("tipoDocumento", solicitacao.documento() != null
                ? solicitacao.documento().getClass().getSimpleName()
                : null);
        payload.put("aprovado", resultado.aprovado());
        payload.put("motivo", resultado.motivo());
        return payload;
    }

    static String toJsonLike(SolicitacaoCreditoCore solicitacao, ResultadoAnalise resultado) {
        Map<String, Object> payload = toMap(solicitacao, resultado);
        return payload.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":" + formatValue(entry.getValue()))
                .collect(Collectors.joining(",", "{", "}"));
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        }
        return "\"" + String.valueOf(value).replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}