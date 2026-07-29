package edu.com.uce.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @SequenceGenerator(name = "seq_vehiculo", sequenceName = "seq_vehiculo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vehiculo")
    @Column(name = "vehi_id")
    private Integer id;

    @Column(name = "vehi_matricula")
    private String matricula;

    @Column(name = "vehi_marca")
    private String marca;

    @Column(name = "vehi_modelo")
    private String modelo;

    @Column(name = "vehi_anio")
    private Integer año;

    @Column(name = "vehi_tipo")
    private String tipo; 

    @Column(name = "vehi_combustible")
    private String combustible;

    @Column(name = "vehi_transmision")
    private String transmision;

    @Column(name = "vehi_precio_dia")
    private Double precioDia;

    @Column(name = "vehi_kilometraje")
    private Double kilometraje;

    @Column(name = "vehi_disponible")
    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "vehi_sucursal_id")
    private Sucursal sucursal;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getMatricula() { 
        return matricula; 
    }
    
    public void setMatricula(String matricula) { 
        this.matricula = matricula; 
    }

    public String getMarca() { 
        return marca; 
    }
    
    public void setMarca(String marca) { 
        this.marca = marca; 
    }

    public String getModelo() { 
        return modelo; 
    }
    
    public void setModelo(String modelo) { 
        this.modelo = modelo; 
    }

    public Integer getAño() { 
        return año; 
    }
    
    public void setAño(Integer año) { 
        this.año = año; 
    }

    public String getTipo() { 
        return tipo; 
    }

    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }

    public String getCombustible() { 
        return combustible; 
    }

    public void setCombustible(String combustible) { 
        this.combustible = combustible; 
    }

    public String getTransmision() { 
        return transmision; 
    }
    
    public void setTransmision(String transmision) { 
        this.transmision = transmision; 
    }

    public Double getPrecioDia() { 
        return precioDia; 
    }
    
    public void setPrecioDia(Double precioDia) { 
        this.precioDia = precioDia; 
    }

    public Double getKilometraje() {
        return kilometraje; 
    }
    
    public void setKilometraje(Double kilometraje) { 
        this.kilometraje = kilometraje; 
    }

    public Boolean getDisponible() { 
        return disponible; 
    }
    
    public void setDisponible(Boolean disponible) { 
        this.disponible = disponible; 
    }

    public Sucursal getSucursal() { 
        return sucursal; 
    }
    
    public void setSucursal(Sucursal sucursal) { 
        this.sucursal = sucursal; 
    }
}