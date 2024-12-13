/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Marlo
 */
public class BalanceTotales {
    private double totalDebe;
    private double totalHaber;
    private double totalSaldoDeudor;
    private double totalSaldoAcreedor;

    // Getters and Setters
    public double getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(double totalDebe) {
        this.totalDebe = totalDebe;
    }

    public double getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(double totalHaber) {
        this.totalHaber = totalHaber;
    }

    public double getTotalSaldoDeudor() {
        return totalSaldoDeudor;
    }

    public void setTotalSaldoDeudor(double totalSaldoDeudor) {
        this.totalSaldoDeudor = totalSaldoDeudor;
    }

    public double getTotalSaldoAcreedor() {
        return totalSaldoAcreedor;
    }

    public void setTotalSaldoAcreedor(double totalSaldoAcreedor) {
        this.totalSaldoAcreedor = totalSaldoAcreedor;
    }

    @Override
    public String toString() {
        return "BalanceTotales{" +
                "totalDebe=" + totalDebe +
                ", totalHaber=" + totalHaber +
                ", totalSaldoDeudor=" + totalSaldoDeudor +
                ", totalSaldoAcreedor=" + totalSaldoAcreedor +
                '}';
    }
}
