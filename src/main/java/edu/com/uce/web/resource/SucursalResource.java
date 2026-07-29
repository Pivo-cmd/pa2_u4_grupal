package edu.com.uce.web.resource;

import edu.com.uce.application.service.SucursalService;
import edu.com.uce.domain.model.Sucursal;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/sucursal")
public class SucursalResource {

    @Inject
    private SucursalService sucursalService;

    @Path("/guardar")
    @POST
    public void guardar(Sucursal sucursal) {
        this.sucursalService.registrar(sucursal);
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Sucursal buscarPorId(@PathParam("id") Integer id) {
        return this.sucursalService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Sucursal> buscarTodos() {
        return this.sucursalService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(Sucursal sucursal, @PathParam("id") Integer id) {
        this.sucursalService.actualizar(id, sucursal);
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.sucursalService.eliminar(id);
    }

    @Path("/buscarPorNombre/{nombre}")
    @GET
    public Sucursal buscarPorNombre(@PathParam("nombre") String nombre) {
        return this.sucursalService.buscarPorNombre(nombre);
    }

    @Path("/buscarPorCiudad/{ciudad}")
    @GET
    public List<Sucursal> buscarPorCiudad(@PathParam("ciudad") String ciudad) {
        return this.sucursalService.listarPorCiudad(ciudad);
    }

    @Path("/buscarActivas")
    @GET
    public List<Sucursal> buscarActivas() {
        return this.sucursalService.listarActivas();
    }
}