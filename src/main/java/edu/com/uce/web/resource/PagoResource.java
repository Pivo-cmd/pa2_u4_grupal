package edu.com.uce.web.resource;

import edu.com.uce.application.service.PagoService;
import edu.com.uce.domain.model.Pago;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/pago")
public class PagoResource {

    @Inject
    private PagoService pagoService;

    @Path("/guardar")
    @POST
    public void guardar(PagoRequest request) {
        this.pagoService.registrar(request.getPago(), request.getReservaId());
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Pago buscarPorId(@PathParam("id") Integer id) {
        return this.pagoService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Pago> buscarTodos() {
        return this.pagoService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(PagoRequest request, @PathParam("id") Integer id) {
        this.pagoService.actualizar(id, request.getPago(), request.getReservaId());
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.pagoService.eliminar(id);
    }

    @Path("/buscarPorReserva/{reservaId}")
    @GET
    public List<Pago> buscarPorReserva(@PathParam("reservaId") Integer reservaId) {
        return this.pagoService.listarPorReserva(reservaId);
    }

    @Path("/buscarPorEstado/{estado}")
    @GET
    public List<Pago> buscarPorEstado(@PathParam("estado") String estado) {
        return this.pagoService.listarPorEstadoPago(estado);
    }

    @Path("/buscarPorMetodo/{metodo}")
    @GET
    public List<Pago> buscarPorMetodo(@PathParam("metodo") String metodo) {
        return this.pagoService.listarPorMetodoPago(metodo);
    }

    @Path("/sumarPagosPorReserva/{reservaId}")
    @GET
    public Double sumarPagosPorReserva(@PathParam("reservaId") Integer reservaId) {
        return this.pagoService.sumarPagosPorReserva(reservaId);
    }

    public static class PagoRequest {
        private Pago pago;
        private Integer reservaId;

        public Pago getPago() { 
            return pago; 
        }
        
        public void setPago(Pago pago) { 
            this.pago = pago; 
        }
        
        public Integer getReservaId() { 
            return reservaId; 
        }
        
        public void setReservaId(Integer reservaId) { 
            this.reservaId = reservaId; 
        }
    }
}