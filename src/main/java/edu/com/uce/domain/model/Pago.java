package edu.com.uce.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago{

    @Id
    @SequenceGenerator(name = "seq_pago", sequenceName = "seq_pago", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pago")
    @Column(name = "pago_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pago_reserva_id")
    private Reserva reserva;

    @Column(name = "pago_monto")
    private Double monto;

    @Column(name = "pago_metodo")
    private String metodoPago; 

    @Column(name = "pago_estado")
    private String estadoPago; 

    @Column(name = "pago_fecha")
    private LocalDateTime fechaPago;

    @Column(name = "pago_referencia")
    private String referencia;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }

    public Reserva getReserva() { 
        return reserva; 
    }
    
    public void setReserva(Reserva reserva) { 
        this.reserva = reserva; 
    }

    public Double getMonto() { 
        return monto; 
    }
    
    public void setMonto(Double monto) { 
        this.monto = monto; 
    }

    public String getMetodoPago() { 
        return metodoPago; 
    }

    public void setMetodoPago(String metodoPago) { 
        this.metodoPago = metodoPago; 
    }

    public String getEstadoPago() { 
        return estadoPago; 
    }
    
    public void setEstadoPago(String estadoPago) { 
        this.estadoPago = estadoPago; 
    }

    public LocalDateTime getFechaPago() { 
        return fechaPago; 
    }
    
    public void setFechaPago(LocalDateTime fechaPago) { 
        this.fechaPago = fechaPago; 
    }

    public String getReferencia() { 
        return referencia; 
    }
    
    public void setReferencia(String referencia) { 
        this.referencia = referencia; 
    }
}