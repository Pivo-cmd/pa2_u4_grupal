package edu.com.uce.web.resource;

import edu.com.uce.application.service.VehiculoService;
import edu.com.uce.domain.model.Vehiculo;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/vehiculo")
public class VehiculoResource {

    @Inject
    private VehiculoService vehiculoService;

    @Path("/guardar")
    @POST
    public void guardar(VehiculoRequest request) {
        this.vehiculoService.registrar(request.getVehiculo(), request.getSucursalId());
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Vehiculo buscarPorId(@PathParam("id") Integer id) {
        return this.vehiculoService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Vehiculo> buscarTodos() {
        return this.vehiculoService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(VehiculoRequest request, @PathParam("id") Integer id) {
        this.vehiculoService.actualizar(id, request.getVehiculo(), request.getSucursalId());
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.vehiculoService.eliminar(id);
    }

    @Path("/buscarPorMatricula/{matricula}")
    @GET
    public Vehiculo buscarPorMatricula(@PathParam("matricula") String matricula) {
        return this.vehiculoService.buscarPorMatricula(matricula);
    }

    @Path("/buscarPorSucursal/{sucursalId}")
    @GET
    public List<Vehiculo> buscarPorSucursal(@PathParam("sucursalId") Integer sucursalId) {
        return this.vehiculoService.listarPorSucursal(sucursalId);
    }

    @Path("/buscarDisponibles")
    @GET
    public List<Vehiculo> buscarDisponibles() {
        return this.vehiculoService.listarDisponibles();
    }

    @Path("/buscarPorMarca/{marca}")
    @GET
    public List<Vehiculo> buscarPorMarca(@PathParam("marca") String marca) {
        return this.vehiculoService.listarPorMarca(marca);
    }

    @Path("/buscarPorPrecioMenor/{precioMaximo}")
    @GET
    public List<Vehiculo> buscarPorPrecioMenor(@PathParam("precioMaximo") Double precioMaximo) {
        return this.vehiculoService.listarPorPrecioMenor(precioMaximo);
    }

    public static class VehiculoRequest {
        private Vehiculo vehiculo;
        private Integer sucursalId;

        public Vehiculo getVehiculo() { 
            return vehiculo; 
        }
        
        public void setVehiculo(Vehiculo vehiculo) { 
            this.vehiculo = vehiculo; 
        }
        
        public Integer getSucursalId() { 
            return sucursalId; 
        }
        
        public void setSucursalId(Integer sucursalId) {
            this.sucursalId = sucursalId; 
        }
    }
}