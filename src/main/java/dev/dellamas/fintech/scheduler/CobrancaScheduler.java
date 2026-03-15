package dev.dellamas.fintech.scheduler;

import dev.dellamas.fintech.service.CobrancaService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CobrancaScheduler {

    private static final Logger LOG = Logger.getLogger(CobrancaScheduler.class);

    @Inject
    CobrancaService cobrancaService;

    @Scheduled(every = "30s")
    void verificarVencimentos() {
        int total = cobrancaService.marcarVencidasComoVencidas();
        if (total > 0) {
            LOG.infof("Vencimentos processados: %d cobrança(s) marcadas como vencidas", total);
        }
    }

    @Scheduled(cron = "0 0 8 * * ?")
    void processarCobrancasDiarias() {
        int total = cobrancaService.processarPendentes();
        LOG.infof("Processamento diário iniciado: %d cobrança(s) em processamento", total);
    }

    @Scheduled(cron = "0 0 18 * * ?")
    void gerarRelatorioDiario() {
        var vencidas = cobrancaService.listarVencidas().size();
        var totalVencido = cobrancaService.totalVencido();
        LOG.infof("Relatório diário — Cobranças vencidas: %d | Total em aberto: R$ %s", vencidas, totalVencido);
    }
}
