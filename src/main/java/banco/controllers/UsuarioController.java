package banco.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import banco.dao.UsuarioDAO;
import banco.dto.UsuarioDTO;
import banco.models.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Controller REST para manejar operaciones de Usuario
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * GET /api/usuarios - Obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioDAO.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/usuarios/{id} - Obtener un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioDAO.findById(id);
            return usuarioOpt
                    .map(usuario -> ResponseEntity.ok(convertToDTO(usuario)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/usuarios - Crear un nuevo usuario
     */
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Verificar duplicados
            if (usuarioDAO.existsByDniRuc(usuarioDTO.getDni_ruc())) {
                return ResponseEntity.badRequest().body("El DNI/RUC ya está registrado.");
            }
            if (usuarioDAO.existsByCorreo(usuarioDTO.getCorreo())) {
                return ResponseEntity.badRequest().body("El correo ya está registrado.");
            }

            Usuario usuario = convertToEntity(usuarioDTO);
            Usuario savedUsuario = usuarioDAO.save(usuario);
            return ResponseEntity.ok(convertToDTO(savedUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear usuario.");
        }
    }

    /**
     * PUT /api/usuarios/{id} - Actualizar un usuario existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Optional<Usuario> existingUsuarioOpt = usuarioDAO.findById(id);
            if (existingUsuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = convertToEntity(usuarioDTO);
            usuario.setId_usuario(id);

            Usuario updatedUsuario = usuarioDAO.update(usuario);
            return ResponseEntity.ok(convertToDTO(updatedUsuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar usuario.");
        }
    }

    /**
     * DELETE /api/usuarios/{id} - Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar usuario.");
        }
    }

    /**
     * GET /api/usuarios/dni/{dni} - Buscar usuario por DNI
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<UsuarioDTO> getUsuarioByDni(@PathVariable String dni) {
        try {
            Optional<Usuario> usuarioOpt = usuarioDAO.findByDniRuc(dni);
            return usuarioOpt
                    .map(usuario -> ResponseEntity.ok(convertToDTO(usuario)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/usuarios/email/{email} - Buscar usuario por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(@PathVariable String email) {
        try {
            Optional<Usuario> usuarioOpt = usuarioDAO.findByCorreo(email);
            return usuarioOpt
                    .map(usuario -> ResponseEntity.ok(convertToDTO(usuario)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    /**
     * GET /api/usuarios/tipo/{tipo} - Obtener usuarios por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByTipo(@PathVariable String tipo) {
        try {
            List<UsuarioDTO> usuarios = usuarioDAO.findByRol(tipo.toUpperCase())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/usuarios/login - Iniciar sesión
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
        try {
            Optional<Usuario> usuarioOpt = usuarioDAO.findByCorreo(loginRequest.getCorreo());

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(401).body("Usuario no encontrado");
            }

            Usuario usuario = usuarioOpt.get();
            if (!usuario.getContrasena().equals(loginRequest.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            return ResponseEntity.ok(convertToDTO(usuario));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // 🔄 Conversión de modelo a DTO
    private UsuarioDTO convertToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId_usuario(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getDni_ruc(),
                usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getNombre_usuario(),
                usuario.getRol_usuario()
        );
    }

    // 🔄 Conversión de DTO a modelo
    private Usuario convertToEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setDni_ruc(dto.getDni_ruc());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setNombre_usuario(dto.getNombre_usuario());
        usuario.setRol_usuario(dto.getRol_usuario());
        return usuario;
    }
}
