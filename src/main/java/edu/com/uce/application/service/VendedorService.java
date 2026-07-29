package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Sucursal;
import edu.com.uce.domain.model.Vendedor;
import edu.com.uce.infreastructure.repository.VendedorRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class VendedorService {

    @Inject
    private VendedorRepositoryImpl vendedorRepository;

    @Inject
    private SucursalService sucursalService;

    @Auditar
    @MedirTiempo
    public void registrar(Vendedor vendedor, Integer sucursalId) {
        if (vendedor.getCedula() == null || vendedor.getCedula().isEmpty()) {
            throw new RuntimeException("La Cedula del vendedor es obligatorio.");
        }
        if (vendedor.getNombre() == null || vendedor.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del vendedor es obligatorio.");
        }
        if (this.vendedorRepository.buscarPorCedula(vendedor.getCedula()) != null) {
            throw new RuntimeException("Ya existe un vendedor con el Cedula: " + vendedor.getCedula());
        }

        Sucursal sucursal = this.sucursalService.buscarPorId(sucursalId);
        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
        }

        vendedor.setSucursal(sucursal);
        vendedor.setActivo(true);
        this.vendedorRepository.persist(vendedor);
    }

    @Auditar
    @MedirTiempo
    public Vendedor buscarPorId(Integer id) {
        return this.vendedorRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public Vendedor buscarPorCedula(String cedula) {
        return this.vendedorRepository.buscarPorCedula(cedula);
    }

    @Auditar
    @MedirTiempo
    public List<Vendedor> listarTodos() {
        return this.vendedorRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Vendedor> listarPorSucursal(Integer sucursalId) {
        return this.vendedorRepository.buscarPorSucursal(sucursalId);
    }

    @Auditar
    @MedirTiempo
    public List<Vendedor> listarActivos() {
        return this.vendedorRepository.buscarActivos();
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Vendedor vendedorActualizado, Integer sucursalId) {
        Vendedor vendedor = this.vendedorRepository.findById(id);
        if (vendedor == null) {
            throw new RuntimeException("Vendedor no encontrado con ID: " + id);
        }

        if (sucursalId != null) {
            Sucursal sucursal = this.sucursalService.buscarPorId(sucursalId);
            if (sucursal == null) {
                throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
            }
            vendedor.setSucursal(sucursal);
        }

        if (vendedorActualizado.getNombre() != null) {
            vendedor.setNombre(vendedorActualizado.getNombre());
        }
        if (vendedorActualizado.getApellido() != null) {
            vendedor.setApellido(vendedorActualizado.getApellido());
        }
        if (vendedorActualizado.getEmail() != null) {
            Vendedor existente = this.vendedorRepository.buscarPorEmail(vendedorActualizado.getEmail());
            if (existente != null && !existente.getId().equals(id)) {
                throw new RuntimeException("El email ya está registrado por otro vendedor.");
            }
            vendedor.setEmail(vendedorActualizado.getEmail());
        }
        if (vendedorActualizado.getTelefono() != null) {
            vendedor.setTelefono(vendedorActualizado.getTelefono());
        }
        if (vendedorActualizado.getComision() != null) {
            vendedor.setComision(vendedorActualizado.getComision());
        }
        if (vendedorActualizado.getActivo() != null) {
            vendedor.setActivo(vendedorActualizado.getActivo());
        }
        this.vendedorRepository.persist(vendedor);
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        this.vendedorRepository.deleteById(id);
    }

}