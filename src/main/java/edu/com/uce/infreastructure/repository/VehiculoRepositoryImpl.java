package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Vehiculo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class VehiculoRepositoryImpl implements PanacheRepositoryBase<Vehiculo, Integer> {

    @Inject
    EntityManager em;

    public Vehiculo buscarPorMatricula(String matricula) {
        try {
            return em.createQuery("SELECT v FROM Vehiculo v WHERE v.matricula = :matricula", Vehiculo.class)
                    .setParameter("matricula", matricula)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Vehiculo> buscarPorMarca(String marca) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.marca = :marca", Vehiculo.class)
                .setParameter("marca", marca)
                .getResultList();
    }

    public List<Vehiculo> buscarPorModelo(String modelo) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.modelo = :modelo", Vehiculo.class)
                .setParameter("modelo", modelo)
                .getResultList();
    }

    public List<Vehiculo> buscarPorTipo(String tipo) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.tipo = :tipo", Vehiculo.class)
                .setParameter("tipo", tipo)
                .getResultList();
    }

    public List<Vehiculo> buscarPorCombustible(String combustible) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.combustible = :combustible", Vehiculo.class)
                .setParameter("combustible", combustible)
                .getResultList();
    }

    public List<Vehiculo> buscarPorTransmision(String transmision) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.transmision = :transmision", Vehiculo.class)
                .setParameter("transmision", transmision)
                .getResultList();
    }

    public List<Vehiculo> buscarPorSucursal(Integer sucursalId) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.sucursal.id = :sucursalId", Vehiculo.class)
                .setParameter("sucursalId", sucursalId)
                .getResultList();
    }

    public List<Vehiculo> buscarDisponibles() {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.disponible = true", Vehiculo.class)
                .getResultList();
    }

    public List<Vehiculo> buscarPorPrecioMenor(Double precioMaximo) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.precioDia <= :precioMax", Vehiculo.class)
                .setParameter("precioMax", precioMaximo)
                .getResultList();
    }

    public List<Vehiculo> buscarPorPrecioEntre(Double precioMin, Double precioMax) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.precioDia BETWEEN :min AND :max", Vehiculo.class)
                .setParameter("min", precioMin)
                .setParameter("max", precioMax)
                .getResultList();
    }

    public List<Vehiculo> buscarPorAnio(Integer anio) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.año = :anio", Vehiculo.class)
                .setParameter("anio", anio)
                .getResultList();
    }

    public List<Vehiculo> buscarPorAnioMayor(Integer anioMinimo) {
        return em.createQuery("SELECT v FROM Vehiculo v WHERE v.año >= :anioMin", Vehiculo.class)
                .setParameter("anioMin", anioMinimo)
                .getResultList();
    }
}