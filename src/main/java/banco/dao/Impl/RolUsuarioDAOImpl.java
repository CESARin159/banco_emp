package banco.dao.Impl;

import banco.dao.RolUsuarioDAO;
import banco.models.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class RolUsuarioDAOImpl implements RolUsuarioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RolUsuario> findAll() {
        String sql = "SELECT * FROM t_rol_usuario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RolUsuario.class));
    }

    @Override
    public Optional<RolUsuario> findById(Long id) {
        String sql = "SELECT * FROM t_rol_usuario WHERE id_rol_usuario = ?";
        try {
            RolUsuario r = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(RolUsuario.class), id);
            return Optional.ofNullable(r);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public RolUsuario save(RolUsuario rol) {
        String sql = "INSERT INTO t_rol_usuario (nombre_rol, descripcion) VALUES (?, ?)";
        jdbcTemplate.update(sql, rol.getNombre_rol(), rol.getDescripcion());
        return rol;
    }

    @Override
    public RolUsuario update(RolUsuario rol) {
        String sql = "UPDATE t_rol_usuario SET nombre_rol=?, descripcion=? WHERE id_rol_usuario=?";
        jdbcTemplate.update(sql, rol.getNombre_rol(), rol.getDescripcion(), rol.getId_rol_usuario());
        return rol;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM t_rol_usuario WHERE id_rol_usuario = ?";
        jdbcTemplate.update(sql, id);
    }
}