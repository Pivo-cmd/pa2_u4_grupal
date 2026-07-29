package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Cliente;
import edu.com.uce.infreastructure.repository.ClienteRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ClienteService {

    @Inject
    private ClienteRepositoryImpl clienteRepository;

    @Auditar
    @MedirTiempo
    public void registrar(Cliente cliente) {
        if (cliente.getCedula() == null || cliente.getCedula().isEmpty()) {
            throw new RuntimeException("El Cedula del cliente es obligatorio.");
        }
        if (cliente.getLicencia() == null || cliente.getLicencia().isEmpty()) {
            throw new RuntimeException("El número de licencia es obligatorio.");
        }
        if (this.clienteRepository.buscarPorCedula(cliente.getCedula()) != null) {
            throw new RuntimeException("Ya existe un cliente con el Cedula: " + cliente.getCedula());
        }
        if (this.clienteRepository.buscarPorLicencia(cliente.getLicencia()) != null) {
            throw new RuntimeException("Ya existe un cliente con la licencia: " + cliente.getLicencia());
        }
        if (cliente.getEmail() != null && this.clienteRepository.buscarPorEmail(cliente.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado.");
        }
        this.clienteRepository.persist(cliente);
    }

    @Auditar
    @MedirTiempo
    public Cliente buscarPorId(Integer id) {
        return this.clienteRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public Cliente buscarPorCedula(String Cedula) {
        return this.clienteRepository.buscarPorCedula(Cedula);
    }

    @Auditar
    @MedirTiempo
    public Cliente buscarPorLicencia(String licencia) {
        return this.clienteRepository.buscarPorLicencia(licencia);
    }

    @Auditar
    @MedirTiempo
    public List<Cliente> listarTodos() {
        return this.clienteRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Cliente> listarPorNombre(String nombre) {
        return this.clienteRepository.buscarPorNombre(nombre);
    }

    @Auditar
    @MedirTiempo
    public List<Cliente> listarMayoresDeEdad() {
        return this.clienteRepository.buscarMayoresDeEdad();
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Cliente clienteActualizado) {
        Cliente cliente = this.clienteRepository.findById(id);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }

        if (clienteActualizado.getNombre() != null) {
            cliente.setNombre(clienteActualizado.getNombre());
        }
        if (clienteActualizado.getApellido() != null) {
            cliente.setApellido(clienteActualizado.getApellido());
        }
        if (clienteActualizado.getEmail() != null) {
            Cliente existente = this.clienteRepository.buscarPorEmail(clienteActualizado.getEmail());
            if (existente != null && !existente.getId().equals(id)) {
                throw new RuntimeException("El email ya está registrado.");
            }
            cliente.setEmail(clienteActualizado.getEmail());
        }
        if (clienteActualizado.getTelefono() != null) {
            cliente.setTelefono(clienteActualizado.getTelefono());
        }
        if (clienteActualizado.getFechaNacimiento() != null) {
            cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
        }
        this.clienteRepository.persist(cliente);
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        this.clienteRepository.deleteById(id);
    }

}