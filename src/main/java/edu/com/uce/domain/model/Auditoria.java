package edu.com.uce.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria{

    @Id
    @SequenceGenerator(name = "seq_auditoria", sequenceName = "seq_auditoria", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auditoria")
    @Column(name = "audi_id")
    private Integer id;

    @Column(name = "audi_entidad")
    private String entidad;

    @Column(name = "audi_operacion")
    private String operacion;

    @Column(name = "audi_registro_id")
    private String registroId;

    @Column(name = "audi_detalle")
    private String detalle;

    @Column(name = "audi_fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "audi_tiempo_ejecucion_ms")
    private Long tiempoEjecucionMs;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getEntidad() { 
        return entidad; 
    }

    public void setEntidad(String entidad) { 
        this.entidad = entidad; 
    }

    public String getOperacion() { 
        return operacion; 
    }
    
    public void setOperacion(String operacion) { 
        this.operacion = operacion; 
    }

    public String getRegistroId() { 
        return registroId; 
    }
    
    public void setRegistroId(String registroId) { 
        this.registroId = registroId; 
    }

    public String getDetalle() { 
        return detalle; 
    }
    
    public void setDetalle(String detalle) { 
        this.detalle = detalle; 
    }

    public LocalDateTime getFechaHora() { 
        return fechaHora; 
    }
    
    public void setFechaHora(LocalDateTime fechaHora) { 
        this.fechaHora = fechaHora; 
    }

    public Long getTiempoEjecucionMs() { 
        return tiempoEjecucionMs; 
    }
    
    public void setTiempoEjecucionMs(Long tiempoEjecucionMs) { 
        this.tiempoEjecucionMs = tiempoEjecucionMs; 
    }

    @Override
    public String toString() {
        return "Auditoria [entidad=" + entidad + ", operacion=" + operacion + ", registroId=" + registroId + "]";
    }
}