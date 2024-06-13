package com.example.instrumentos_api.Repositories;

import com.example.instrumentos_api.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuarioAndClave(String nombreUsuario, String clave);
}