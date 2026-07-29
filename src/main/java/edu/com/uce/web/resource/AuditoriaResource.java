package edu.com.uce.web.resource;

import edu.com.uce.application.service.AuditoriaService;
import edu.com.uce.domain.model.Auditoria;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/auditoria")
public class AuditoriaResource {

    @Inject
    private AuditoriaService auditoriaService;

    @Path("/buscarTodos")
    @GET
    public List<Auditoria> buscarTodos() {
        return this.auditoriaService.listarTodos();
    }

    @Path("/buscarPorEntidad/{entidad}")
    @GET
    public List<Auditoria> buscarPorEntidad(@PathParam("entidad") String entidad) {
        return this.auditoriaService.listarPorEntidad(entidad);
    }

    @Path("/buscarPorOperacion/{operacion}")
    @GET
    public List<Auditoria> buscarPorOperacion(@PathParam("operacion") String operacion) {
        return this.auditoriaService.listarPorOperacion(operacion);
    }
}