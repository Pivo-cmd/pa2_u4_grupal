package edu.com.uce.application.service;


import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Auditoria;
import edu.com.uce.infreastructure.repository.AuditoriaRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class AuditoriaService {

    @Inject
    private AuditoriaRepositoryImpl auditoriaRepository;

    @MedirTiempo
    public void guardar(Auditoria auditoria) {
        this.auditoriaRepository.persist(auditoria);
    }

    @MedirTiempo
    public List<Auditoria> listarTodos() {
        return this.auditoriaRepository.findAll().list();
    }

    @MedirTiempo
    public List<Auditoria> listarPorEntidad(String entidad) {
        return this.auditoriaRepository.buscarPorEntidad(entidad);
    }

    @MedirTiempo
    public List<Auditoria> listarPorOperacion(String operacion) {
        return this.auditoriaRepository.buscarPorOperacion(operacion);
    }

}