package edu.com.uce.infreastructure.repository;

import edu.com.uce.domain.model.Pago;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PagoRepositoryImpl implements PanacheRepositoryBase<Pago, Integer> {

    @Inject
    EntityManager em;

    public List<Pago> buscarPorReserva(Integer reservaId) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.reserva.id = :reservaId", Pago.class)
                .setParameter("reservaId", reservaId)
                .getResultList();
    }

    public List<Pago> buscarPorMetodoPago(String metodoPago) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.metodoPago = :metodoPago", Pago.class)
                .setParameter("metodoPago", metodoPago)
                .getResultList();
    }

    public List<Pago> buscarPorEstadoPago(String estadoPago) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.estadoPago = :estadoPago", Pago.class)
                .setParameter("estadoPago", estadoPago)
                .getResultList();
    }

    public List<Pago> buscarPorFechaPago(LocalDateTime fecha) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.fechaPago = :fecha", Pago.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }

    public List<Pago> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :inicio AND :fin", Pago.class)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }

    public List<Pago> buscarPorMontoMayor(Double montoMinimo) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.monto >= :monto", Pago.class)
                .setParameter("monto", montoMinimo)
                .getResultList();
    }

    public List<Pago> buscarPorMontoEntre(Double montoMin, Double montoMax) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.monto BETWEEN :min AND :max", Pago.class)
                .setParameter("min", montoMin)
                .setParameter("max", montoMax)
                .getResultList();
    }

    public List<Pago> buscarPorReferencia(String referencia) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.referencia = :referencia", Pago.class)
                .setParameter("referencia", referencia)
                .getResultList();
    }

    public Double sumarPagosPorReserva(Integer reservaId) {
        Double result = em.createQuery("SELECT SUM(p.monto) FROM Pago p WHERE p.reserva.id = :reservaId", Double.class)
                .setParameter("reservaId", reservaId)
                .getSingleResult();
        return result != null ? result : 0.0;
    }

    public Long contarPagosPorReserva(Integer reservaId) {
        return em.createQuery("SELECT COUNT(p) FROM Pago p WHERE p.reserva.id = :reservaId", Long.class)
                .setParameter("reservaId", reservaId)
                .getSingleResult();
    }

    public List<Pago> buscarPagosCompletadosPorReserva(Integer reservaId) {
        return em.createQuery("SELECT p FROM Pago p WHERE p.reserva.id = :reservaId AND p.estadoPago = 'COMPLETADO'", Pago.class)
                .setParameter("reservaId", reservaId)
                .getResultList();
    }
}