
package com.tienda.service;

import com.tienda.domain.Usuario;
import com.tienda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {
    
    
    private final UsuarioRepository usuarioRepository;
    private final HttpSession session;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, HttpSession session) {
        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }
         
    // Este método busca en la tabla de usuarios el usuario con el username que pasaron desde el "login".
    // Si lo encuentra guarda la foto y crea el usuario con rol de seguridad...
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Primero se busca el usuario a ver si está...
        Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado "+ username));
        
        // Si estoy acá es que si encontró el usuario...
        // Se crea un atributo de session para guardar la imagen del usuario.
        session.removeAttribute("imagenUsuario");
        session.setAttribute("imagenUsuario", usuario.getRutaImagen());
        
        // Se cargan los roles del usuario... como Roles de Seguridad...
        var roles = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority("ROLE_"+rol.getRol()))
                .collect(Collectors.toSet());
        
        // Se retorna un usuario del sistema con username, clave y roles.
        return new User(usuario.getUsername(), usuario.getPassword(),roles);
    }
    
}
