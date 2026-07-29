package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Sucursal;
import edu.com.uce.infreastructure.repository.SucursalRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class SucursalService {

    @Inject
    private SucursalRepositoryImpl sucursalRepository;

    @Auditar
    @MedirTiempo
    public void registrar(Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre de la sucursal es obligatorio.");
        }
        sucursal.setActiva(true);
        this.sucursalRepository.persist(sucursal);
    }

    @Auditar
    @MedirTiempo
    public Sucursal buscarPorId(Integer id) {
        return this.sucursalRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public Sucursal buscarPorNombre(String nombre) {
        return this.sucursalRepository.buscarPorNombre(nombre);
    }

    @Auditar
    @MedirTiempo
    public List<Sucursal> listarTodos() {
        return this.sucursalRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Sucursal> listarPorCiudad(String ciudad) {
        return this.sucursalRepository.buscarPorCiudad(ciudad);
    }

    @Auditar
    @MedirTiempo
    public List<Sucursal> listarActivas() {
        return this.sucursalRepository.buscarActivas();
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Sucursal sucursalActualizada) {
        Sucursal sucursal = this.sucursalRepository.findById(id);
        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + id);
        }
        if (sucursalActualizada.getNombre() != null) {
            sucursal.setNombre(sucursalActualizada.getNombre());
        }
        if (sucursalActualizada.getDireccion() != null) {
            sucursal.setDireccion(sucursalActualizada.getDireccion());
        }
        if (sucursalActualizada.getCiudad() != null) {
            sucursal.setCiudad(sucursalActualizada.getCiudad());
        }
        if (sucursalActualizada.getPais() != null) {
            sucursal.setPais(sucursalActualizada.getPais());
        }
        if (sucursalActualizada.getTelefono() != null) {
            sucursal.setTelefono(sucursalActualizada.getTelefono());
        }
        if (sucursalActualizada.getEmail() != null) {
            sucursal.setEmail(sucursalActualizada.getEmail());
        }
        if (sucursalActualizada.getActiva() != null) {
            sucursal.setActiva(sucursalActualizada.getActiva());
        }
        this.sucursalRepository.persist(sucursal);
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        this.sucursalRepository.deleteById(id);
    }

}