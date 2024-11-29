/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Marlo
 */
public class BalanzaComprobacion {
    private int id_balanza;
    private double saldo_debito;
    private double saldo_credito;
    private PeriodosContables periodocontabel;
    private Cuentas cuenta;

    public BalanzaComprobacion() {
    }

    public int getId_balanza() {
        return id_balanza;
    }

    public void setId_balanza(int id_balanza) {
        this.id_balanza = id_balanza;
    }

    public double getSaldo_debito() {
        return saldo_debito;
    }

    public void setSaldo_debito(double saldo_debito) {
        this.saldo_debito = saldo_debito;
    }

    public double getSaldo_credito() {
        return saldo_credito;
    }

    public void setSaldo_credito(double saldo_credito) {
        this.saldo_credito = saldo_credito;
    }

    public PeriodosContables getPeriodocontabel() {
        return periodocontabel;
    }

    public void setPeriodocontabel(PeriodosContables periodocontabel) {
        this.periodocontabel = periodocontabel;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }
    
}
