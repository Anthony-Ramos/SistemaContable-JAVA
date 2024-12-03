/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Marlo
 */
public class Movimientos {
    private int idmovimiento;
    private double cargo;
    private double abono;
    private Cuentas idcuenta;
    private Partidas idpartida;

    public Movimientos() {
    }

    public int getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(int idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public Cuentas getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Cuentas idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Partidas getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(Partidas idpartida) {
        this.idpartida = idpartida;
    }
    
}
