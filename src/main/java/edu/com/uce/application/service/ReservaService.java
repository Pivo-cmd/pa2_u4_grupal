package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.*;
import edu.com.uce.infreastructure.repository.ReservaRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class ReservaService {

    @Inject
    private ReservaRepositoryImpl reservaRepository;

    @Inject
    private ClienteService clienteService;

    @Inject
    private VendedorService vendedorService;

    @Inject
    private VehiculoService vehiculoService;

    @Inject
    private SucursalService sucursalService;

    @Inject PagoService pagoService;

    @Auditar
    @MedirTiempo
    public Reserva registrar(Reserva reserva, Integer clienteId, Integer vendedorId,
            Integer vehiculoId, Integer sucursalRecogidaId,
            Integer sucursalDevolucionId) {

        if (reserva.getFechaDevolucion().isBefore(reserva.getFechaRecogida()) ||
                reserva.getFechaDevolucion().isEqual(reserva.getFechaRecogida())) {
            throw new RuntimeException("La fecha de devolución debe ser posterior a la fecha de recogida.");
        }

        Cliente cliente = this.clienteService.buscarPorId(clienteId);
        if(cliente == null){
            throw new RuntimeException("Cliente no encontrado con ID: " + clienteId);
        }
        Vendedor vendedor = this.vendedorService.buscarPorId(vendedorId);
        if(vendedor == null){
            throw new RuntimeException("Vendedor no encontrado con ID: " +vendedorId);
        }

        if (!vendedor.getActivo()) {
            throw new RuntimeException("El vendedor "+ vendedor.getActivo() + " no está activo. No se puede gestionar la reserva");
        }

        Vehiculo vehiculo = this.vehiculoService.buscarPorId(vehiculoId);
        if (vehiculo == null) {
            throw new RuntimeException("Vehiculo no encontrado con ID: " + vehiculoId);
        }

         if (Boolean.FALSE.equals(vehiculo.getDisponible())) {
            throw new RuntimeException("El vehículo con placa " + vehiculo.getMatricula() + " está fuera de servicio.");
        }

        Sucursal sucursalRecogida = this.sucursalService.buscarPorId(sucursalRecogidaId);
        if (sucursalRecogida == null) {
            throw new RuntimeException("Sucursal de recogida no encontrada con ID: " + sucursalRecogidaId);
        }

        if(!sucursalRecogida.getActiva()){
            throw new RuntimeException("La sucursal de recogida " + sucursalRecogida.getNombre() + " no está activa.");
        }

        if (!sucursalRecogida.getId().equals(vehiculo.getSucursal().getId())) {
            throw new RuntimeException(
                    "El vehículo con placa " + vehiculo.getMatricula() +
                            " se encuentra en la sucursal '" + vehiculo.getSucursal().getNombre() +
                            "'. No puede recogerse en '" + sucursalRecogida.getNombre() + "'.");
        }
        Sucursal sucursalDevolucion = this.sucursalService.buscarPorId(sucursalDevolucionId);
        if(sucursalDevolucion == null){
            throw new RuntimeException("Sucursal de devolución no encontrada con ID: " + sucursalDevolucionId);
        }
        if (!sucursalDevolucion.getActiva()) {
            throw new RuntimeException("La sucursal de devolución " + sucursalDevolucion.getNombre() + " no está activa.");
        }

        boolean existeSolapamiento = this.existeReservaSolapada(vehiculoId,
                reserva.getFechaRecogida(), reserva.getFechaDevolucion(), null);
        if (existeSolapamiento) {
            throw new RuntimeException("El vehículo ya tiene una reserva en el rango de fechas seleccionado.");
        }

        reserva.setCliente(cliente);
        reserva.setVendedor(vendedor);
        reserva.setVehiculo(vehiculo);
        reserva.setSucursalRecogida(sucursalRecogida);
        reserva.setSucursalDevolucion(sucursalDevolucion);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado("PENDIENTE");
        reserva.setCodigo("RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        long dias = ChronoUnit.DAYS.between(reserva.getFechaRecogida(), reserva.getFechaDevolucion());
        reserva.setTotal(vehiculo.getPrecioDia() * dias);

        this.reservaRepository.persist(reserva);
        vehiculo.setDisponible(false);
        vehiculoService.actualizar(vehiculo.getId(), vehiculo, vehiculo.getSucursal().getId());
        
        return reserva;
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Reserva reservaActualizada,
            Integer clienteId, Integer vendedorId,
            Integer vehiculoId, Integer sucursalRecogidaId,
            Integer sucursalDevolucionId) {

        Reserva reserva = this.reservaRepository.findById(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }

        if (reservaActualizada.getFechaRecogida() != null && reservaActualizada.getFechaDevolucion() != null) {
            if (reservaActualizada.getFechaDevolucion().isBefore(reservaActualizada.getFechaRecogida()) ||
                    reservaActualizada.getFechaDevolucion().isEqual(reservaActualizada.getFechaRecogida())) {
                throw new RuntimeException("La fecha de devolución debe ser posterior a la fecha de recogida.");
            }
        }

        LocalDate fechaRecogidaFinal = reservaActualizada.getFechaRecogida() != null
                ? reservaActualizada.getFechaRecogida()
                : reserva.getFechaRecogida();
        LocalDate fechaDevolucionFinal = reservaActualizada.getFechaDevolucion() != null
                ? reservaActualizada.getFechaDevolucion()
                : reserva.getFechaDevolucion();

        Vehiculo vehiculoNuevo = null;

        if (vehiculoId != null && !vehiculoId.equals(reserva.getVehiculo().getId())) {
            boolean existeSolapamiento = this.existeReservaSolapada(vehiculoId,
                    fechaRecogidaFinal, fechaDevolucionFinal, id);
            if (existeSolapamiento) {
                throw new RuntimeException("El nuevo vehículo ya tiene una reserva en esas fechas.");
            }

            vehiculoNuevo = this.vehiculoService.buscarPorId(vehiculoId);
            if (Boolean.FALSE.equals(vehiculoNuevo.getDisponible())) {
                throw new RuntimeException("El nuevo vehículo está fuera de servicio.");
            }
            reserva.setVehiculo(vehiculoNuevo);
        } else {
            boolean existeSolapamiento = this.existeReservaSolapada(reserva.getVehiculo().getId(),
                    fechaRecogidaFinal, fechaDevolucionFinal, id);
            if (existeSolapamiento) {
                throw new RuntimeException("El vehículo ya tiene otra reserva en el nuevo rango de fechas.");
            }
            vehiculoNuevo = reserva.getVehiculo();
        }

        if (clienteId != null)
            reserva.setCliente(this.clienteService.buscarPorId(clienteId));
        if (vendedorId != null)
            reserva.setVendedor(this.vendedorService.buscarPorId(vendedorId));
        if (sucursalRecogidaId != null)
            reserva.setSucursalRecogida(this.sucursalService.buscarPorId(sucursalRecogidaId));
        if (sucursalDevolucionId != null)
            reserva.setSucursalDevolucion(this.sucursalService.buscarPorId(sucursalDevolucionId));

        if (reservaActualizada.getFechaRecogida() != null)
            reserva.setFechaRecogida(reservaActualizada.getFechaRecogida());
        if (reservaActualizada.getFechaDevolucion() != null)
            reserva.setFechaDevolucion(reservaActualizada.getFechaDevolucion());
        if (reservaActualizada.getEstado() != null)
            reserva.setEstado(reservaActualizada.getEstado());

        long dias = ChronoUnit.DAYS.between(reserva.getFechaRecogida(), reserva.getFechaDevolucion());
        reserva.setTotal(vehiculoNuevo.getPrecioDia() * dias);

        this.reservaRepository.persist(reserva);
    }

    @Auditar
    @MedirTiempo
    public void cancelar(Integer id) {
        Reserva reserva = this.reservaRepository.findById(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }
        reserva.setEstado("CANCELADA");
        Vehiculo vehiculo = reserva.getVehiculo();
        vehiculo.setDisponible(true);
        vehiculoService.actualizar(vehiculo.getId(), vehiculo, vehiculo.getSucursal().getId());
        this.reservaRepository.persist(reserva);
        System.out.println("Reserva cancelada: " + reserva.getCodigo());
    }

    @Auditar
    @MedirTiempo
    public void completar(Integer id) {
        Reserva reserva = this.reservaRepository.findById(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }
        reserva.setEstado("COMPLETADA");
        this.reservaRepository.persist(reserva);

        Vehiculo vehiculo = reserva.getVehiculo();
        vehiculo.setDisponible(true);
        vehiculo.setSucursal(reserva.getSucursalDevolucion());
        this.vehiculoService.actualizar(vehiculo.getId(), vehiculo, reserva.getSucursalDevolucion().getId());
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        Reserva reserva = this.reservaRepository.findById(id);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }

        List<Pago> pagos = this.pagoService.listarPorReserva(id);
        if (!pagos.isEmpty()) {
            throw new RuntimeException(
                "No se puede eliminar la reserva porque tiene " + 
                pagos.size() + " pagos asociados. "
        );
        }

        Vehiculo vehiculo = reserva.getVehiculo();
        if (vehiculo != null) {
            vehiculo.setDisponible(true);
            this.vehiculoService.actualizar(
                vehiculo.getId(), 
                vehiculo, 
                vehiculo.getSucursal().getId()
            );
            System.out.println("Vehículo " + vehiculo.getMatricula() + " liberado.");
        }
        
        this.reservaRepository.delete(reserva);
    }

    @Auditar
    @MedirTiempo
    public Reserva buscarPorId(Integer id) {
        return this.reservaRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public Reserva buscarPorCodigo(String codigo) {
        return this.reservaRepository.buscarPorCodigo(codigo);
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarTodos() {
        return this.reservaRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarPorCliente(Integer clienteId) {
        return this.reservaRepository.buscarPorCliente(clienteId);
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarPorEstado(String estado) {
        return this.reservaRepository.buscarPorEstado(estado);
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarPorPlacaVehiculo(String placa) {
        return this.reservaRepository.buscarPorPlacaVehiculo(placa);
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarPorCedulaCliente(String cedula) {
        return this.reservaRepository.buscarPorDniCliente(cedula);
    }

    @Auditar
    @MedirTiempo
    public List<Reserva> listarPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return this.reservaRepository.buscarPorRangoFechas(inicio, fin);
    }

    private boolean existeReservaSolapada(Integer vehiculoId, LocalDate inicio, LocalDate fin,
            Integer excluirReservaId) {
        List<String> estadosActivos = List.of("PENDIENTE", "CONFIRMADA", "EN_CURSO");

        List<Reserva> reservasExistentes = this.reservaRepository.buscarPorVehiculo(vehiculoId);

        for (Reserva r : reservasExistentes) {
            if (excluirReservaId != null && excluirReservaId.equals(r.getId())) {
                continue;
            }
            if (!estadosActivos.contains(r.getEstado())) {
                continue;
            }
            boolean seSolapan = !inicio.isAfter(r.getFechaDevolucion()) &&
                    !fin.isBefore(r.getFechaRecogida());
            if (seSolapan) {
                return true;
            }
        }
        return false;
    }

    public void confirmar(Integer id) {
        Reserva reserva = this.reservaRepository.findById(id);
        if (reserva != null && "PENDIENTE".equals(reserva.getEstado())) {
            reserva.setEstado("CONFIRMADA");
            this.reservaRepository.persist(reserva);
        }
    }

}