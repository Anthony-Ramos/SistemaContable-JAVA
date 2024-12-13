/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Marlo
 */
public class BalanceComprobacion {

    private String nombreCuenta;
    private Double debe;
    private Double haber;
    private Double saldoDeudor;
    private Double saldoAcreedor;

    public BalanceComprobacion() {
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }

    public Double getSaldoDeudor() {
        return saldoDeudor;
    }

    public void setSaldoDeudor(Double saldoDeudor) {
        this.saldoDeudor = saldoDeudor;
    }

    public Double getSaldoAcreedor() {
        return saldoAcreedor;
    }

    public void setSaldoAcreedor(Double saldoAcreedor) {
        this.saldoAcreedor = saldoAcreedor;
    }
    
}
