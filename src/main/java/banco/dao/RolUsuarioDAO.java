package banco.dao;

import banco.models.RolUsuario;
import java.util.List;
import java.util.Optional;

public interface RolUsuarioDAO {
    List<RolUsuario> findAll();
    Optional<RolUsuario> findById(Long id);
    RolUsuario save(RolUsuario rolUsuario);
    RolUsuario update(RolUsuario rolUsuario);
    void deleteById(Long id);
}