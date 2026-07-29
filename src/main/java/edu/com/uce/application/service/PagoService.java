package edu.com.uce.application.service;

import edu.com.uce.application.interceptor.Auditar;
import edu.com.uce.application.interceptor.MedirTiempo;
import edu.com.uce.domain.model.Pago;
import edu.com.uce.domain.model.Reserva;
import edu.com.uce.infreastructure.repository.PagoRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
@Transactional
public class PagoService {

    @Inject
    private PagoRepositoryImpl pagoRepository;

    @Inject
    private ReservaService reservaService;

    @Auditar
    @MedirTiempo
    public void registrar(Pago pago, Integer reservaId) {
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser mayor a cero.");
        }

        Reserva reserva = this.reservaService.buscarPorId(reservaId);
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada con ID: " + reservaId);
        }

        Double pagadoHastaAhora = this.pagoRepository.sumarPagosPorReserva(reservaId);
        Double nuevoTotalPagado = pagadoHastaAhora + pago.getMonto();

        if (nuevoTotalPagado > reserva.getTotal()) {
            throw new RuntimeException(
                    "El pago excede el total de la reserva. " +
                            "Total: $" + reserva.getTotal() +
                            ", ya pagado: $" + pagadoHastaAhora +
                            ", intentas pagar: $" + pago.getMonto());
        }

        pago.setReserva(reserva);
        pago.setFechaPago(LocalDateTime.now());
        if (pago.getEstadoPago() == null) {
            pago.setEstadoPago("PENDIENTE");
        }
        this.pagoRepository.persist(pago);

        if ("COMPLETADO".equals(pago.getEstadoPago())) {
            this.reservaService.confirmar(reservaId);
        }

        generarFacturaEnParalelo(pago, reserva);
    }

    @Auditar
    @MedirTiempo
    public Pago buscarPorId(Integer id) {
        return this.pagoRepository.findById(id);
    }

    @Auditar
    @MedirTiempo
    public List<Pago> listarTodos() {
        return this.pagoRepository.findAll().list();
    }

    @Auditar
    @MedirTiempo
    public List<Pago> listarPorReserva(Integer reservaId) {
        return this.pagoRepository.buscarPorReserva(reservaId);
    }

    @Auditar
    @MedirTiempo
    public List<Pago> listarPorEstadoPago(String estado) {
        return this.pagoRepository.buscarPorEstadoPago(estado);
    }

    @Auditar
    @MedirTiempo
    public List<Pago> listarPorMetodoPago(String metodo) {
        return this.pagoRepository.buscarPorMetodoPago(metodo);
    }

    @Auditar
    @MedirTiempo
    public Double sumarPagosPorReserva(Integer reservaId) {
        return this.pagoRepository.sumarPagosPorReserva(reservaId);
    }

    @Auditar
    @MedirTiempo
    public void actualizar(Integer id, Pago pagoActualizado, Integer reservaId) {
        Pago pago = this.pagoRepository.findById(id);
        if (pago == null) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }

        if (reservaId != null) {
            Reserva reserva = this.reservaService.buscarPorId(reservaId);
            if (reserva == null) {
                throw new RuntimeException("Reserva no encontrada con ID: " + reservaId);
            }
            pago.setReserva(reserva);
        }

        if (pagoActualizado.getMonto() != null) {
            if (pagoActualizado.getMonto() <= 0) {
                throw new RuntimeException("El monto debe ser mayor a cero.");
            }
            pago.setMonto(pagoActualizado.getMonto());
        }
        if (pagoActualizado.getMetodoPago() != null) {
            pago.setMetodoPago(pagoActualizado.getMetodoPago());
        }
        if (pagoActualizado.getEstadoPago() != null) {
            pago.setEstadoPago(pagoActualizado.getEstadoPago());
        }
        if (pagoActualizado.getReferencia() != null) {
            pago.setReferencia(pagoActualizado.getReferencia());
        }
        this.pagoRepository.persist(pago);
    }

    @Auditar
    @MedirTiempo
    public void eliminar(Integer id) {
        this.pagoRepository.deleteById(id);
    }

    private void generarFacturaEnParalelo(Pago pago, Reserva reserva) {
        Thread hiloFactura = new Thread(() -> {
            try {
                Thread.sleep(2000);

                String directorio = System.getProperty("user.dir") + File.separator + "facturas";
                File carpeta = new File(directorio);
                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                String nombreArchivo = "FACTURA-" + reserva.getCodigo() + "-"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
                        + ".txt";

                File archivo = new File(carpeta, nombreArchivo);
                FileWriter escritor = new FileWriter(archivo);

                escritor.write("==================================================\n");
                escritor.write("           FACTURA DE PAGO - RENT-A-CAR           \n");
                escritor.write("==================================================\n");
                escritor.write("Fecha Emision: " + LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
                escritor.write("Reserva:       " + reserva.getCodigo() + "\n");
                escritor.write("Cliente:       " + reserva.getCliente().getNombre() + " "
                        + reserva.getCliente().getApellido() + "\n");
                escritor.write("Cedula:        " + reserva.getCliente().getCedula() + "\n");
                escritor.write("Vehiculo:      " + reserva.getVehiculo().getMarca() + " "
                        + reserva.getVehiculo().getModelo() + "\n");
                escritor.write("Matricula:     " + reserva.getVehiculo().getMatricula() + "\n");
                escritor.write("--------------------------------------------------\n");
                escritor.write("Monto Pagado:  $" + pago.getMonto() + "\n");
                escritor.write("Metodo Pago:   " + pago.getMetodoPago() + "\n");
                escritor.write(
                        "Referencia:    " + (pago.getReferencia() != null ? pago.getReferencia() : "N/A") + "\n");
                escritor.write("Estado Pago:   " + pago.getEstadoPago() + "\n");
                escritor.write("--------------------------------------------------\n");
                escritor.write("Total Reserva: $" + reserva.getTotal() + "\n");
                escritor.write("==================================================\n");

                escritor.close();

                System.out.println("[Factura] Archivo generado: " + archivo.getAbsolutePath());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Factura] Proceso interrumpido para reserva " + reserva.getCodigo());
            } catch (Exception e) {
                System.err.println("[Factura] Error al generar archivo: " + e.getMessage());
            }
        });

        hiloFactura.setName("Hilo-Factura-" + reserva.getCodigo());
        hiloFactura.start();
    }

}