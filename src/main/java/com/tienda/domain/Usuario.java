package com.tienda.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
    
    // Se establece un inicio de ID para serializar...
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    
    @Column(unique = true, nullable = false, length = 30)
    @NotNull
    @Size(max=30)
    private String username;
    
    @Column(unique = true, nullable = false, length = 512)
    @NotNull
    @Size(max=512)
    private String password;
    
    @Column(nullable = false, length = 20)
    @NotNull
    @Size(max=20)
    private String nombre;
    
    @Column(nullable = false, length = 30)
    @NotNull
    @Size(max=30)
    private String apellidos;
    
    @Column(unique = true, nullable = false, length = 75)
    @NotNull
    @Size(max=75)
    private String correo;
    
    @Column(length = 25)
    @Size(max=25)
    private String telefono;
    
    @Column(length = 1024)
    @Size(max=1024)
    private String rutaImagen;
    
    private boolean activo;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="usuario_rol",joinColumns=@JoinColumn(name="id_usuario"),
            inverseJoinColumns=@JoinColumn(name="id_rol")
    )
    private Set<Rol> roles = new HashSet<>();
    
}
