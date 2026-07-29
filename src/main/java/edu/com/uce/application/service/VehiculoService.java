package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Sucursal;
import edu.com.uce.domain.model.Vehiculo;
import edu.com.uce.infreastructure.repository.VehiculoRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class VehiculoService {

    @Inject
    private VehiculoRepositoryImpl vehiculoRepository;

    @Inject
    private SucursalService sucursalService;

    @Auditar
    @MedirTiempo
    public void registrar(Vehiculo vehiculo, Integer sucursalId) {
        if (vehiculo.getMatricula() == null || vehiculo.getMatricula().isEmpty()) {
            throw new RuntimeException("La matrícula del vehículo es obligatoria.");
        }
        if (vehiculo.getMarca() == null || vehiculo.getMarca().isEmpty()) {
            throw new RuntimeException("La marca es obligatoria.");
        }
        if (vehiculo.getPrecioDia() == null || vehiculo.getPrecioDia() <= 0) {
            throw new RuntimeException("El precio por día debe ser mayor a cero.");
        }
        if (this.vehiculoRepository.buscarPorMatricula(vehiculo.getMatricula()) != null) {
            throw new RuntimeException("Ya existe un vehículo con la matrícula: " + vehiculo.getMatricula());
        }

        Sucursal sucursal = this.sucursalService.buscarPorId(sucursalId);
        if (sucursal == null) {
            throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
        }

        vehiculo.setSucursal(sucursal);
        vehiculo.setDisponible(true);
        this.vehiculoRepository.persist(vehiculo);
    }

    @Auditar
    @MedirTiempo
    public Vehiculo buscarPorId(Integer id) {
        return this.vehiculoRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public Vehiculo buscarPorMatricula(String matricula) {
        return this.vehiculoRepository.buscarPorMatricula(matricula);
    }

    @Auditar
    @MedirTiempo
    public List<Vehiculo> listarTodos() {
        return this.vehiculoRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Vehiculo> listarPorSucursal(Integer sucursalId) {
        return this.vehiculoRepository.buscarPorSucursal(sucursalId);
    }

    @Auditar
    @MedirTiempo
    public List<Vehiculo> listarDisponibles() {
        return this.vehiculoRepository.buscarDisponibles();
    }

    @Auditar
    @MedirTiempo
    public List<Vehiculo> listarPorMarca(String marca) {
        return this.vehiculoRepository.buscarPorMarca(marca);
    }

    @Auditar
    @MedirTiempo
    public List<Vehiculo> listarPorPrecioMenor(Double precioMaximo) {
        return this.vehiculoRepository.buscarPorPrecioMenor(precioMaximo);
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Vehiculo vehiculoActualizado, Integer sucursalId) {
        Vehiculo vehiculo = this.vehiculoRepository.findById(id);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado con ID: " + id);
        }

        if (sucursalId != null) {
            Sucursal sucursal = this.sucursalService.buscarPorId(sucursalId);
            if (sucursal == null) {
                throw new RuntimeException("Sucursal no encontrada con ID: " + sucursalId);
            }
            vehiculo.setSucursal(sucursal);
        }

        if (vehiculoActualizado.getMatricula() != null) {
            Vehiculo existente = this.vehiculoRepository.buscarPorMatricula(vehiculoActualizado.getMatricula());
            if (existente != null && !existente.getId().equals(id)) {
                throw new RuntimeException("La matrícula ya está registrada.");
            }
            vehiculo.setMatricula(vehiculoActualizado.getMatricula());
        }
        if (vehiculoActualizado.getMarca() != null) {
            vehiculo.setMarca(vehiculoActualizado.getMarca());
        }
        if (vehiculoActualizado.getModelo() != null) {
            vehiculo.setModelo(vehiculoActualizado.getModelo());
        }
        if (vehiculoActualizado.getAño() != null) {
            vehiculo.setAño(vehiculoActualizado.getAño());
        }
        if (vehiculoActualizado.getTipo() != null) {
            vehiculo.setTipo(vehiculoActualizado.getTipo());
        }
        if (vehiculoActualizado.getCombustible() != null) {
            vehiculo.setCombustible(vehiculoActualizado.getCombustible());
        }
        if (vehiculoActualizado.getTransmision() != null) {
            vehiculo.setTransmision(vehiculoActualizado.getTransmision());
        }
        if (vehiculoActualizado.getPrecioDia() != null) {
            if (vehiculoActualizado.getPrecioDia() <= 0) {
                throw new RuntimeException("El precio por día debe ser mayor a cero.");
            }
            vehiculo.setPrecioDia(vehiculoActualizado.getPrecioDia());
        }
        if (vehiculoActualizado.getKilometraje() != null) {
            vehiculo.setKilometraje(vehiculoActualizado.getKilometraje());
        }
        if (vehiculoActualizado.getDisponible() != null) {
            vehiculo.setDisponible(vehiculoActualizado.getDisponible());
        }
        this.vehiculoRepository.persist(vehiculo);
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        this.vehiculoRepository.deleteById(id);
    }

    
}