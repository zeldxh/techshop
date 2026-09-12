package com.tienda.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="ruta")
public class Ruta implements Serializable {
    
    // Se establece un inicio de ID para serializar...
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRuta;
    
    @Column(unique = true, nullable = false, length = 25)
    @NotNull
    @Size(max=25)
    private String ruta;
    private boolean requiereRol;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_rol")
    private Rol rol;
}
