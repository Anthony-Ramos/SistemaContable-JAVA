/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Harvin Ramos
 */
public class ModeloBalanceG {

    private String codigo;
    private String nombreCuenta;
    private double monto;
    private int tipo;

    public ModeloBalanceG() {
    }

    public ModeloBalanceG(String codigo, String nombreCuenta, double monto, int tipo) {
        this.codigo = codigo;
        this.nombreCuenta = nombreCuenta;
        this.monto = monto;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}
