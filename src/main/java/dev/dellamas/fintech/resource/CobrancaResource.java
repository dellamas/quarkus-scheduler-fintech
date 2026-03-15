package dev.dellamas.fintech.resource;

import dev.dellamas.fintech.model.Cobranca;
import dev.dellamas.fintech.service.CobrancaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/cobrancas")
@Produces(MediaType.APPLICATION_JSON)
public class CobrancaResource {

    @Inject
    CobrancaService cobrancaService;

    @GET
    public List<Cobranca> listarTodas() {
        return cobrancaService.listarTodas();
    }

    @GET
    @Path("/vencidas")
    public List<Cobranca> listarVencidas() {
        return cobrancaService.listarVencidas();
    }

    @GET
    @Path("/pendentes")
    public List<Cobranca> listarPendentes() {
        return cobrancaService.listarPendentes();
    }

    @GET
    @Path("/resumo")
    @Produces(MediaType.TEXT_PLAIN)
    public String resumo() {
        return String.format("Total vencido: R$ %s | Vencidas: %d | Pendentes: %d",
                cobrancaService.totalVencido(),
                cobrancaService.listarVencidas().size(),
                cobrancaService.listarPendentes().size());
    }
}
