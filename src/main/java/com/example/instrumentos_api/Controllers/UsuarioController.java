package com.example.instrumentos_api.Controllers;

import com.example.instrumentos_api.Entities.Usuario;
import com.example.instrumentos_api.Repositories.UsuarioRepository;
import com.example.instrumentos_api.Utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        String encryptedPassword = EncryptionUtils.encryptPassword(usuario.getClave());
        Usuario foundUsuario = usuarioRepository.findByNombreUsuarioAndClave(usuario.getNombreUsuario(), encryptedPassword);
        if (foundUsuario != null) {
            return ResponseEntity.ok(foundUsuario);
        } else {
            return ResponseEntity.status(401).body("Usuario y/o Clave incorrectos, vuelva a intentar");
        }
    }
}
