package banco.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cuenta_bancaria;

    private String numero_cuenta;
    private String banco;
    private String tipo_cuenta;
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Getters y Setters

    public Long getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(Long id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}