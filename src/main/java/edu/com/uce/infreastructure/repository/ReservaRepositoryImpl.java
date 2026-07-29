package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Reserva;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ReservaRepositoryImpl implements PanacheRepositoryBase<Reserva, Integer> {

    @Inject
    EntityManager em;

    public Reserva buscarPorCodigo(String codigo) {
        try {
            return em.createQuery("SELECT r FROM Reserva r WHERE r.codigo = :codigo", Reserva.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Reserva> buscarPorCliente(Integer clienteId) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.cliente.id = :clienteId", Reserva.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }

    public List<Reserva> buscarPorVehiculo(Integer vehiculoId) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.vehiculo.id = :vehiculoId", Reserva.class)
                .setParameter("vehiculoId", vehiculoId)
                .getResultList();
    }

    public List<Reserva> buscarPorVendedor(Integer vendedorId) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.vendedor.id = :vendedorId", Reserva.class)
                .setParameter("vendedorId", vendedorId)
                .getResultList();
    }

    public List<Reserva> buscarPorSucursalRecogida(Integer sucursalId) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.sucursalRecogida.id = :sucursalId", Reserva.class)
                .setParameter("sucursalId", sucursalId)
                .getResultList();
    }

    public List<Reserva> buscarPorSucursalDevolucion(Integer sucursalId) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.sucursalDevolucion.id = :sucursalId", Reserva.class)
                .setParameter("sucursalId", sucursalId)
                .getResultList();
    }

    public List<Reserva> buscarPorEstado(String estado) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.estado = :estado", Reserva.class)
                .setParameter("estado", estado)
                .getResultList();
    }

    public List<Reserva> buscarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.fechaRecogida BETWEEN :inicio AND :fin", Reserva.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }

    public List<Reserva> buscarPorFechaRecogida(LocalDate fecha) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.fechaRecogida = :fecha", Reserva.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    public List<Reserva> buscarPorFechaDevolucion(LocalDate fecha) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.fechaDevolucion = :fecha", Reserva.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    public List<Reserva> buscarPorFechaReserva(LocalDateTime fecha) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.fechaReserva = :fecha", Reserva.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    public List<Reserva> buscarPorTotalMayor(Double totalMinimo) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.total >= :totalMin", Reserva.class)
                .setParameter("totalMin", totalMinimo)
                .getResultList();
    }

    public List<Reserva> buscarPorTotalEntre(Double totalMin, Double totalMax) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.total BETWEEN :min AND :max", Reserva.class)
                .setParameter("min", totalMin)
                .setParameter("max", totalMax)
                .getResultList();
    }

    public List<Reserva> buscarPorPlacaVehiculo(String placa) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.vehiculo.matricula = :placa", Reserva.class)
                .setParameter("placa", placa)
                .getResultList();
    }

    public List<Reserva> buscarPorDniCliente(String cedula) {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.cliente.cedula = :cedula", Reserva.class)
                .setParameter("cedula", cedula)
                .getResultList();
    }

    public List<Reserva> buscarReservasActivas() {
        return em.createQuery("SELECT r FROM Reserva r WHERE r.estado IN ('PENDIENTE', 'CONFIRMADA')", Reserva.class)
                .getResultList();
    }

    public Long contarReservasPorCliente(Integer clienteId) {
        Long count = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.cliente.id = :clienteId", Long.class)
                .setParameter("clienteId", clienteId)
                .getSingleResult();
        return count != null ? count : 0L;
    }

    public Long contarReservasPorVehiculo(Integer vehiculoId) {
        Long count = em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.vehiculo.id = :vehiculoId", Long.class)
                .setParameter("vehiculoId", vehiculoId)
                .getSingleResult();
        return count != null ? count : 0L;
    }
}