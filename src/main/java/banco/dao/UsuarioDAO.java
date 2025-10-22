package banco.dao;

import banco.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {

    // CRUD básico
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id_usuario);
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void deleteById(Long id_usuario);

    // Búsquedas específicas
    Optional<Usuario> findByDniRuc(String dniRuc);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(String rolUsuario);
    List<Usuario> findBySexo(String sexo);

    // Verificaciones
    boolean existsByDniRuc(String dniRuc);
    boolean existsByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
}
