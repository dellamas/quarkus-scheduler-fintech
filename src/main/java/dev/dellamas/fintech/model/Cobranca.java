package dev.dellamas.fintech.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cobranca {

    private String id;
    private String cliente;
    private BigDecimal valor;
    private LocalDate vencimento;
    private StatusCobranca status;

    public enum StatusCobranca {
        PENDENTE, PROCESSANDO, PAGA, VENCIDA
    }

    public Cobranca(String id, String cliente, BigDecimal valor, LocalDate vencimento) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = StatusCobranca.PENDENTE;
    }

    public String getId() { return id; }
    public String getCliente() { return cliente; }
    public BigDecimal getValor() { return valor; }
    public LocalDate getVencimento() { return vencimento; }
    public StatusCobranca getStatus() { return status; }
    public void setStatus(StatusCobranca status) { this.status = status; }
}
