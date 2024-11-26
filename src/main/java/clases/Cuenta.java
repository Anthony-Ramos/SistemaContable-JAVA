package clases;

/**
 *
 * @author Anthony
 */
public class Cuenta {
    private String nombre;
    private String codigo;
    private String tipoCuenta; // Ejemplo: Activo, Pasivo, Capital, Ingreso, Gasto
    private int nivel; // Nivel de jerarquía de la cuenta (por ejemplo, 1 para activos, 2 para subcuentas)
    private String saldoContrario; // "R" si la cuenta es de saldo contrario (como una cuenta de pasivo o contraactivo)

    public Cuenta(String nombre, String codigo, String tipoCuenta, int nivel, String saldoContrario) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.tipoCuenta = tipoCuenta;
        this.nivel = nivel;
        this.saldoContrario = saldoContrario;
    }

    public Cuenta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getSaldoContrario() {
        return saldoContrario;
    }

    public void setSaldoContrario(String saldoContrario) {
        this.saldoContrario = saldoContrario;
    } 
}

