package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Auditoria;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AuditoriaRepositoryImpl implements PanacheRepositoryBase<Auditoria, Integer> {

    @Inject
    EntityManager em;

    public List<Auditoria> buscarPorEntidad(String entidad) {
        return em.createQuery("SELECT a FROM Auditoria a WHERE a.entidad = :entidad", Auditoria.class)
                .setParameter("entidad", entidad)
                .getResultList();
    }

    public List<Auditoria> buscarPorOperacion(String operacion) {
        return em.createQuery("SELECT a FROM Auditoria a WHERE a.operacion = :operacion", Auditoria.class)
                .setParameter("operacion", operacion)
                .getResultList();
    }
}