package edu.com.uce.web.resource;

import edu.com.uce.application.service.VendedorService;
import edu.com.uce.domain.model.Vendedor;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/vendedor")
public class VendedorResource {

    @Inject
    private VendedorService vendedorService;

    @Path("/guardar")
    @POST
    public void guardar(VendedorRequest request) {
        this.vendedorService.registrar(request.getVendedor(), request.getSucursalId());
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Vendedor buscarPorId(@PathParam("id") Integer id) {
        return this.vendedorService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Vendedor> buscarTodos() {
        return this.vendedorService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(VendedorRequest request, @PathParam("id") Integer id) {
        this.vendedorService.actualizar(id, request.getVendedor(), request.getSucursalId());
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.vendedorService.eliminar(id);
    }

    @Path("/buscarPorCedula/{cedula}")
    @GET
    public Vendedor buscarPorCedula(@PathParam("cedula") String cedula) {
        return this.vendedorService.buscarPorCedula(cedula);
    }

    @Path("/buscarPorSucursal/{sucursalId}")
    @GET
    public List<Vendedor> buscarPorSucursal(@PathParam("sucursalId") Integer sucursalId) {
        return this.vendedorService.listarPorSucursal(sucursalId);
    }

    @Path("/buscarActivos")
    @GET
    public List<Vendedor> buscarActivos() {
        return this.vendedorService.listarActivos();
    }

    public static class VendedorRequest {
        private Vendedor vendedor;
        private Integer sucursalId;

        public Vendedor getVendedor() { 
            return vendedor; 
        }
        
        public void setVendedor(Vendedor vendedor) { 
            this.vendedor = vendedor; 
        }
        
        public Integer getSucursalId() { 
            return sucursalId; 
        }
        
        public void setSucursalId(Integer sucursalId) { 
            this.sucursalId = sucursalId; 
        }
    }
}