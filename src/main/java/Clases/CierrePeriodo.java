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
public class CierrePeriodo {
    private int id_cierre;
    private double total_debitos;
    private double total_creditos;
    private String estado;
    private Date cerrado_en;
    private Usuarios usuario;
    private PeriodosContables periodocontable;

    public CierrePeriodo() {
    }

    public int getId_cierre() {
        return id_cierre;
    }

    public void setId_cierre(int id_cierre) {
        this.id_cierre = id_cierre;
    }

    public double getTotal_debitos() {
        return total_debitos;
    }

    public void setTotal_debitos(double total_debitos) {
        this.total_debitos = total_debitos;
    }

    public double getTotal_creditos() {
        return total_creditos;
    }

    public void setTotal_creditos(double total_creditos) {
        this.total_creditos = total_creditos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCerrado_en() {
        return cerrado_en;
    }

    public void setCerrado_en(Date cerrado_en) {
        this.cerrado_en = cerrado_en;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public PeriodosContables getPeriodocontable() {
        return periodocontable;
    }

    public void setPeriodocontable(PeriodosContables periodocontable) {
        this.periodocontable = periodocontable;
    }
    
}
