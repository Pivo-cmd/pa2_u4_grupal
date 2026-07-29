package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Sucursal;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SucursalRepositoryImpl implements PanacheRepositoryBase<Sucursal, Integer> {

    @Inject
    EntityManager em;

    public Sucursal buscarPorNombre(String nombre) {
        try {
            return em.createQuery("SELECT s FROM Sucursal s WHERE s.nombre = :nombre", Sucursal.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Sucursal> buscarPorCiudad(String ciudad) {
        return em.createQuery("SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad", Sucursal.class)
                .setParameter("ciudad", ciudad)
                .getResultList();
    }

    public List<Sucursal> buscarPorPais(String pais) {
        return em.createQuery("SELECT s FROM Sucursal s WHERE s.pais = :pais", Sucursal.class)
                .setParameter("pais", pais)
                .getResultList();
    }

    public List<Sucursal> buscarActivas() {
        return em.createQuery("SELECT s FROM Sucursal s WHERE s.activa = true", Sucursal.class)
                .getResultList();
    }

    public List<Sucursal> buscarPorRangoIds(Integer idInicio, Integer idFin) {
        return em.createQuery("SELECT s FROM Sucursal s WHERE s.id BETWEEN :inicio AND :fin", Sucursal.class)
                .setParameter("inicio", idInicio)
                .setParameter("fin", idFin)
                .getResultList();
    }
}