package edu.com.uce.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedor")
public class Vendedor{

    @Id
    @SequenceGenerator(name = "seq_vendedor", sequenceName = "seq_vendedor", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vendedor")
    @Column(name = "vend_id")
    private Integer id;

    @Column(name = "vend_nombre")
    private String nombre;

    @Column(name = "vend_apellido")
    private String apellido;

    @Column(name = "vend_email")
    private String email;

    @Column(name = "vend_telefono")
    private String telefono;

    @Column(name = "vend_cedula")
    private String cedula;

    @Column(name = "vend_comision")
    private Double comision;

    @Column(name = "vend_activo")
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "vend_sucursal_id", nullable = false)
    private Sucursal sucursal;

    public Integer getId() { 
        return id; 
    }
    
    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getApellido() { 
        return apellido; 
    }
    
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getCedula() { 
        return cedula; 
    }
    
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }

    public Double getComision() { 
        return comision; 
    }

    public void setComision(Double comision) { 
        this.comision = comision; 
    }

    public Boolean getActivo() { 
        return activo; 
    }

    public void setActivo(Boolean activo) { 
        this.activo = activo; 
    }

    public Sucursal getSucursal() { 
        return sucursal; 
    }

    public void setSucursal(Sucursal sucursal) { 
        this.sucursal = sucursal; 
    }
}