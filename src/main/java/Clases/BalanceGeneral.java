/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Date;

/**
 *
 * @author Marlo
 */
public class BalanceGeneral {
    private int id_balance;
    private double total_activos;
    private double total_pasivos;
    private double total_patrimonio;
    private Date generado_en;
    private PeriodosContables periodocontable;
    private Usuarios usuario;

    public BalanceGeneral() {
    }

    public int getId_balance() {
        return id_balance;
    }

    public void setId_balance(int id_balance) {
        this.id_balance = id_balance;
    }

    public double getTotal_activos() {
        return total_activos;
    }

    public void setTotal_activos(double total_activos) {
        this.total_activos = total_activos;
    }

    public double getTotal_pasivos() {
        return total_pasivos;
    }

    public void setTotal_pasivos(double total_pasivos) {
        this.total_pasivos = total_pasivos;
    }

    public double getTotal_patrimonio() {
        return total_patrimonio;
    }

    public void setTotal_patrimonio(double total_patrimonio) {
        this.total_patrimonio = total_patrimonio;
    }

    public Date getGenerado_en() {
        return generado_en;
    }

    public void setGenerado_en(Date generado_en) {
        this.generado_en = generado_en;
    }

    

    public PeriodosContables getPeriodocontable() {
        return periodocontable;
    }

    public void setPeriodocontable(PeriodosContables periodocontable) {
        this.periodocontable = periodocontable;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
}
