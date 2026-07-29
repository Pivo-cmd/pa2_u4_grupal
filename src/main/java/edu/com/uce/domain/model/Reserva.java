package edu.com.uce.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @SequenceGenerator(name = "seq_reserva", sequenceName = "seq_reserva", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reserva")
    @Column(name = "rese_id")
    private Integer id;

    @Column(name = "rese_codigo")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "rese_cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "rese_vendedor_id")
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "rese_vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "rese_sucursal_recogida")
    private Sucursal sucursalRecogida;

    @ManyToOne
    @JoinColumn(name = "rese_sucursal_devolucion")
    private Sucursal sucursalDevolucion;

    @Column(name = "rese_fecha_recogida")
    private LocalDate fechaRecogida;

    @Column(name = "rese_fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "rese_fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "rese_estado", length = 30)
    private String estado; 
    
    @Column(name = "rese_total")
    private Double total;

    public Integer getId() { 
        return id; 
    }

    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getCodigo() { 
        return codigo; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }
    
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente; 
    }

    public Vendedor getVendedor() { 
        return vendedor; 
    }

    public void setVendedor(Vendedor vendedor) { 
        this.vendedor = vendedor; 
    }

    public Vehiculo getVehiculo() { 
        return vehiculo; 
    }
    
    public void setVehiculo(Vehiculo vehiculo) { 
        this.vehiculo = vehiculo; 
    }

    public Sucursal getSucursalRecogida() { 
        return sucursalRecogida; 
    }
    
    public void setSucursalRecogida(Sucursal sucursalRecogida) { 
        this.sucursalRecogida = sucursalRecogida; 
    }

    public Sucursal getSucursalDevolucion() { 
        return sucursalDevolucion; 
    }
    
    public void setSucursalDevolucion(Sucursal sucursalDevolucion) { 
        this.sucursalDevolucion = sucursalDevolucion; 
    }

    public LocalDate getFechaRecogida() { 
        return fechaRecogida; 
    }
    
    public void setFechaRecogida(LocalDate fechaRecogida) { 
        this.fechaRecogida = fechaRecogida; 
    }

    public LocalDate getFechaDevolucion() { 
        return fechaDevolucion; 
    }
    
    public void setFechaDevolucion(LocalDate fechaDevolucion) { 
        this.fechaDevolucion = fechaDevolucion; 
    }

    public LocalDateTime getFechaReserva() { 
        return fechaReserva; 
    }
    
    public void setFechaReserva(LocalDateTime fechaReserva) { 
        this.fechaReserva = fechaReserva; 
    }

    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public Double getTotal() { 
        return total; 
    }
    
    public void setTotal(Double total) { 
        this.total = total; 
    }
}