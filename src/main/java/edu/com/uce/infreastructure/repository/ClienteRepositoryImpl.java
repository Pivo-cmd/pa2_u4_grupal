package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ClienteRepositoryImpl implements PanacheRepositoryBase<Cliente, Integer> {

    @Inject
    EntityManager em;

    public Cliente buscarPorCedula(String cedula) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.cedula = :cedula", Cliente.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Cliente buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Cliente buscarPorLicencia(String licencia) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.licencia = :licencia", Cliente.class)
                    .setParameter("licencia", licencia)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return em.createQuery("SELECT c FROM Cliente c WHERE c.nombre = :nombre", Cliente.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public List<Cliente> buscarPorApellido(String apellido) {
        return em.createQuery("SELECT c FROM Cliente c WHERE c.apellido = :apellido", Cliente.class)
                .setParameter("apellido", apellido)
                .getResultList();
    }

    public List<Cliente> buscarPorFechaNacimiento(LocalDate fecha) {
        return em.createQuery("SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fecha", Cliente.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    public List<Cliente> buscarMayoresDeEdad() {
        LocalDate fechaLimite = LocalDate.now().minusYears(18);
        return em.createQuery("SELECT c FROM Cliente c WHERE c.fechaNacimiento <= :limite", Cliente.class)
                .setParameter("limite", fechaLimite)
                .getResultList();
    }
}