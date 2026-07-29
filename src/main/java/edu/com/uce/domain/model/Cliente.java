package edu.com.uce.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
public class Cliente{

    @Id
    @SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    @Column(name = "clie_id")
    private Integer id;

    @Column(name = "clie_nombre")
    private String nombre;

    @Column(name = "clie_apellido")
    private String apellido;

    @Column(name = "clie_email")
    private String email;

    @Column(name = "clie_telefono")
    private String telefono;

    @Column(name = "clie_cedula")
    private String cedula;

    @Column(name = "clie_licencia")
    private String licencia;

    @Column(name = "clie_fecha_nacimiento")
    private LocalDate fechaNacimiento;

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

    public String getLicencia() { 
        return licencia; 
    }

    public void setLicencia(String licencia) { 
        this.licencia = licencia; 
    }

    public LocalDate getFechaNacimiento() { 
        return fechaNacimiento; 
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
}