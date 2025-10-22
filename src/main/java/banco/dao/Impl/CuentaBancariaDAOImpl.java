package banco.dao.Impl;


import banco.dao.CuentaBancariaDAO;
import banco.models.CuentaBancaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CuentaBancariaDAOImpl implements CuentaBancariaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CuentaBancaria> findAll() {
        String sql = "SELECT * FROM t_cuenta_bancaria";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CuentaBancaria.class));
    }

    @Override
    public Optional<CuentaBancaria> findById(Long id) {
        String sql = "SELECT * FROM t_cuenta_bancaria WHERE id_cuenta_bancaria = ?";
        try {
            CuentaBancaria c = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CuentaBancaria.class), id);
            return Optional.ofNullable(c);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public CuentaBancaria save(CuentaBancaria cuenta) {
        String sql = "INSERT INTO t_cuenta_bancaria (numero_cuenta, banco, tipo_cuenta, saldo, id_usuario) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cuenta.getNumero_cuenta(), cuenta.getBanco(), cuenta.getTipo_cuenta(), cuenta.getSaldo(), cuenta.getUsuario().getId_usuario());
        return cuenta;
    }

    @Override
    public CuentaBancaria update(CuentaBancaria cuenta) {
        String sql = "UPDATE t_cuenta_bancaria SET numero_cuenta=?, banco=?, tipo_cuenta=?, saldo=?, id_usuario=? WHERE id_cuenta_bancaria=?";
        jdbcTemplate.update(sql, cuenta.getNumero_cuenta(), cuenta.getBanco(), cuenta.getTipo_cuenta(), cuenta.getSaldo(), cuenta.getUsuario().getId_usuario(), cuenta.getId_cuenta_bancaria());
        return cuenta;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM t_cuenta_bancaria WHERE id_cuenta_bancaria = ?";
        jdbcTemplate.update(sql, id);
    }
}