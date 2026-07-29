package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Vendedor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class VendedorRepositoryImpl implements PanacheRepositoryBase<Vendedor, Integer> {

    @Inject
    EntityManager em;

    public Vendedor buscarPorCedula(String cedula) {
        try {
            return em.createQuery("SELECT v FROM Vendedor v WHERE v.cedula = :cedula", Vendedor.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Vendedor buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT v FROM Vendedor v WHERE v.email = :email", Vendedor.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Vendedor> buscarPorNombre(String nombre) {
        return em.createQuery("SELECT v FROM Vendedor v WHERE v.nombre = :nombre", Vendedor.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public List<Vendedor> buscarPorSucursal(Integer sucursalId) {
        return em.createQuery("SELECT v FROM Vendedor v WHERE v.sucursal.id = :sucursalId", Vendedor.class)
                .setParameter("sucursalId", sucursalId)
                .getResultList();
    }

    public List<Vendedor> buscarActivos() {
        return em.createQuery("SELECT v FROM Vendedor v WHERE v.activo = true", Vendedor.class)
                .getResultList();
    }

    public List<Vendedor> buscarPorComisionMayor(Double comisionMinima) {
        return em.createQuery("SELECT v FROM Vendedor v WHERE v.comision >= :comisionMin", Vendedor.class)
                .setParameter("comisionMin", comisionMinima)
                .getResultList();
    }
}