package edu.com.uce.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @SequenceGenerator(name = "seq_sucursal", sequenceName = "seq_sucursal", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sucursal")
    @Column(name = "sucu_id")
    private Integer id;

    @Column(name = "sucu_nombre")
    private String nombre;

    @Column(name = "sucu_direccion")
    private String direccion;

    @Column(name = "sucu_ciudad")
    private String ciudad;

    @Column(name = "sucu_pais")
    private String pais;

    @Column(name = "sucu_telefono")
    private String telefono;

    @Column(name = "sucu_email")
    private String email;

    @Column(name = "sucu_activa")
    private Boolean activa = true;

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

    public String getDireccion() { 
        return direccion; 
    }
    
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }

    public String getCiudad() { 
        return ciudad; 
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad; 
    }

    public String getPais() {
        return pais; 
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public Boolean getActiva() { 
        return activa; 
    }
    
    public void setActiva(Boolean activa) { 
        this.activa = activa; 
    }
}