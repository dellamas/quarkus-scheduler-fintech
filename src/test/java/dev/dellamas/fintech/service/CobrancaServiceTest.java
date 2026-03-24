package dev.dellamas.fintech.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.dellamas.fintech.model.Cobranca;
import dev.dellamas.fintech.model.Cobranca.StatusCobranca;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CobrancaServiceTest {

    @Test
    void shouldKeepOverdueChargesVisibleAfterMarkingThemAsOverdue() {
        CobrancaService cobrancaService = new CobrancaService();

        int vencidas = cobrancaService.marcarVencidasComoVencidas();

        assertEquals(2, vencidas);
        assertEquals(2, cobrancaService.listarVencidas().size());
        assertTrue(cobrancaService.listarVencidas().stream()
                .allMatch(c -> c.getStatus() == StatusCobranca.VENCIDA));
    }

    @Test
    void shouldNotMoveOverdueChargesToProcessing() {
        CobrancaService cobrancaService = new CobrancaService();

        int processadas = cobrancaService.processarPendentes();

        assertEquals(2, processadas);
        long overdueStillPending = cobrancaService.listarTodas().stream()
                .filter(c -> c.getVencimento().isBefore(java.time.LocalDate.now()))
                .filter(c -> c.getStatus() == StatusCobranca.PENDENTE)
                .count();

        assertEquals(2, overdueStillPending);
    }

    @Test
    void shouldIgnoreMalformedChargesWithoutDueDateWhenListingOverdue() throws Exception {
        CobrancaService cobrancaService = new CobrancaService();
        inserirCobranca(cobrancaService, new Cobranca("C999", "Cliente sem data", new java.math.BigDecimal("10.00"), null));

        assertEquals(2, cobrancaService.listarVencidas().size());
    }

    @SuppressWarnings("unchecked")
    private void inserirCobranca(CobrancaService cobrancaService, Cobranca cobranca) throws Exception {
        Field field = CobrancaService.class.getDeclaredField("cobrancas");
        field.setAccessible(true);
        Map<String, Cobranca> cobrancas = (Map<String, Cobranca>) field.get(cobrancaService);
        cobrancas.put(cobranca.getId(), cobranca);
    }

}
