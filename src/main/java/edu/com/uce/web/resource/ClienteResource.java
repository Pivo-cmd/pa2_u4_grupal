package edu.com.uce.web.resource;

import edu.com.uce.application.service.ClienteService;
import edu.com.uce.domain.model.Cliente;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/cliente")
public class ClienteResource {

    @Inject
    private ClienteService clienteService;

    @Path("/guardar")
    @POST
    public void guardar(Cliente cliente) {
        this.clienteService.registrar(cliente);
    }

    @Path("/buscarPorId/{id}")
    @GET
    public Cliente buscarPorId(@PathParam("id") Integer id) {
        return this.clienteService.buscarPorId(id);
    }

    @Path("/buscarTodos")
    @GET
    public List<Cliente> buscarTodos() {
        return this.clienteService.listarTodos();
    }

    @Path("/actualizar/{id}")
    @PUT
    public void actualizar(Cliente cliente, @PathParam("id") Integer id) {
        this.clienteService.actualizar(id, cliente);
    }

    @Path("/eliminar/{id}")
    @DELETE
    public void eliminar(@PathParam("id") Integer id) {
        this.clienteService.eliminar(id);
    }

    @Path("/buscarPorCedula/{cedula}")
    @GET
    public Cliente buscarPorCedula(@PathParam("cedula") String cedula) {
        return this.clienteService.buscarPorCedula(cedula);
    }

    @Path("/buscarPorLicencia/{licencia}")
    @GET
    public Cliente buscarPorLicencia(@PathParam("licencia") String licencia) {
        return this.clienteService.buscarPorLicencia(licencia);
    }

    @Path("/buscarPorNombre/{nombre}")
    @GET
    public List<Cliente> buscarPorNombre(@PathParam("nombre") String nombre) {
        return this.clienteService.listarPorNombre(nombre);
    }

    @Path("/buscarMayoresDeEdad")
    @GET
    public List<Cliente> buscarMayoresDeEdad() {
        return this.clienteService.listarMayoresDeEdad();
    }
}