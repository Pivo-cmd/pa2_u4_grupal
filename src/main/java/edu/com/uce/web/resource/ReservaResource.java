package edu.com.uce.web.resource;

import edu.com.uce.application.service.ReservaService;
import edu.com.uce.domain.model.Reserva;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.time.LocalDate;
import java.util.List;

@Path("/reserva")
public class ReservaResource {

    @Inject
    private ReservaService reservaService;

    @Path("/guardar")
    @POST
    public void guardar(ReservaRequest request) {
        this.reservaService.registrar(
            request.getReserva(),
            request.getClienteId(),
            request.getVendedorId(),
            request.getVehiculoId(),
            request.getSucursalRecogidaId(),
            request.getSucursalDevolucionId()
        );
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Reserva buscarPorId(@PathParam("id") Integer id) {
        return this.reservaService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Reserva> buscarTodos() {
        return this.reservaService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(ReservaRequest request, @PathParam("id") Integer id) {
        this.reservaService.actualizar(
            id,
            request.getReserva(),
            request.getClienteId(),
            request.getVendedorId(),
            request.getVehiculoId(),
            request.getSucursalRecogidaId(),
            request.getSucursalDevolucionId()
        );
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.reservaService.eliminar(id);
    }

    @Path("/buscarPorCodigo/{codigo}")
    @GET
    public Reserva buscarPorCodigo(@PathParam("codigo") String codigo) {
        return this.reservaService.buscarPorCodigo(codigo);
    }

    @Path("/buscarPorCliente/{clienteId}")
    @GET
    public List<Reserva> buscarPorCliente(@PathParam("clienteId") Integer clienteId) {
        return this.reservaService.listarPorCliente(clienteId);
    }

    @Path("/buscarPorEstado/{estado}")
    @GET
    public List<Reserva> buscarPorEstado(@PathParam("estado") String estado) {
        return this.reservaService.listarPorEstado(estado);
    }

    @Path("/buscarPorPlaca/{placa}")
    @GET
    public List<Reserva> buscarPorPlaca(@PathParam("placa") String placa) {
        return this.reservaService.listarPorPlacaVehiculo(placa);
    }

    @Path("/buscarPorCedulaCliente/{cedula}")
    @GET
    public List<Reserva> buscarPorDniCliente(@PathParam("cedula") String cedula) {
        return this.reservaService.listarPorCedulaCliente(cedula);
    }

    @Path("/buscarPorRangoFechas/{inicio}/{fin}")
    @GET
    public List<Reserva> buscarPorRangoFechas(@PathParam("inicio") String inicio, 
                                              @PathParam("fin") String fin) {
        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);
        return this.reservaService.listarPorRangoFechas(fechaInicio, fechaFin);
    }

    @Path("/cancelar/{id}")
    @PUT
    public void cancelar(@PathParam("id") Integer id) {
        this.reservaService.cancelar(id);
    }

    @Path("/completar/{id}")
    @PUT
    public void completar(@PathParam("id") Integer id) {
        this.reservaService.completar(id);
    }

    public static class ReservaRequest {
        private Reserva reserva;
        private Integer clienteId;
        private Integer vendedorId;
        private Integer vehiculoId;
        private Integer sucursalRecogidaId;
        private Integer sucursalDevolucionId;

        public Reserva getReserva() { 
            return reserva; 
        }
        
        public void setReserva(Reserva reserva) { 
            this.reserva = reserva; 
        }
        
        public Integer getClienteId() { 
            return clienteId; 
        }
        
        public void setClienteId(Integer clienteId) { 
            this.clienteId = clienteId; 
        }
        
        public Integer getVendedorId() {
            return vendedorId; 
        }
        
        public void setVendedorId(Integer vendedorId) { 
            this.vendedorId = vendedorId; 
        }

        public Integer getVehiculoId() { 
            return vehiculoId; 
        }
        
        public void setVehiculoId(Integer vehiculoId) { 
            this.vehiculoId = vehiculoId; 
        }
        
        public Integer getSucursalRecogidaId() { 
            return sucursalRecogidaId; 
        }
        
        public void setSucursalRecogidaId(Integer sucursalRecogidaId) { 
            this.sucursalRecogidaId = sucursalRecogidaId; 
        }
        
        public Integer getSucursalDevolucionId() { 
            return sucursalDevolucionId; 
        }
        
        public void setSucursalDevolucionId(Integer sucursalDevolucionId) { 
            this.sucursalDevolucionId = sucursalDevolucionId; 
        }
    }
}