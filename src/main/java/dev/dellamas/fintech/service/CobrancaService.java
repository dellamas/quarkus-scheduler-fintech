package dev.dellamas.fintech.service;

import dev.dellamas.fintech.model.Cobranca;
import dev.dellamas.fintech.model.Cobranca.StatusCobranca;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class CobrancaService {

    private final Map<String, Cobranca> cobrancas = new ConcurrentHashMap<>();

    public CobrancaService() {
        cobrancas.put("C001", new Cobranca("C001", "Empresa Alpha", new BigDecimal("1500.00"), LocalDate.now().minusDays(1)));
        cobrancas.put("C002", new Cobranca("C002", "Startup Beta", new BigDecimal("890.50"), LocalDate.now()));
        cobrancas.put("C003", new Cobranca("C003", "Loja Gamma", new BigDecimal("3200.00"), LocalDate.now().plusDays(5)));
        cobrancas.put("C004", new Cobranca("C004", "Consultoria Delta", new BigDecimal("650.00"), LocalDate.now().minusDays(3)));
    }

    public List<Cobranca> listarTodas() {
        return new ArrayList<>(cobrancas.values());
    }

    public List<Cobranca> listarVencidas() {
        return cobrancas.values().stream()
                .filter(c -> c.getVencimento().isBefore(LocalDate.now()) && c.getStatus() == StatusCobranca.PENDENTE)
                .collect(Collectors.toList());
    }

    public List<Cobranca> listarPendentes() {
        return cobrancas.values().stream()
                .filter(c -> c.getStatus() == StatusCobranca.PENDENTE)
                .collect(Collectors.toList());
    }

    public int marcarVencidasComoVencidas() {
        List<Cobranca> vencidas = listarVencidas();
        vencidas.forEach(c -> c.setStatus(StatusCobranca.VENCIDA));
        return vencidas.size();
    }

    public int processarPendentes() {
        List<Cobranca> elegiveis = cobrancas.values().stream()
                .filter(c -> c.getStatus() == StatusCobranca.PENDENTE)
                .filter(c -> !c.getVencimento().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        elegiveis.forEach(c -> c.setStatus(StatusCobranca.PROCESSANDO));
        return elegiveis.size();
    }

    public BigDecimal totalVencido() {
        return cobrancas.values().stream()
                .filter(c -> c.getStatus() == StatusCobranca.VENCIDA)
                .map(Cobranca::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
