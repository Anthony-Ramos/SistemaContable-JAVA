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
public class EstadoResultados {
    private int id_estado;
    private double total_ingresos;
    private double total_gastos;
    private double utilidad_neta;
    private Date generado_en;
    private PeriodosContables peridocontable;
    private Usuarios usuario;

    public EstadoResultados() {
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public double getTotal_ingresos() {
        return total_ingresos;
    }

    public void setTotal_ingresos(double total_ingresos) {
        this.total_ingresos = total_ingresos;
    }

    public double getTotal_gastos() {
        return total_gastos;
    }

    public void setTotal_gastos(double total_gastos) {
        this.total_gastos = total_gastos;
    }

    public double getUtilidad_neta() {
        return utilidad_neta;
    }

    public void setUtilidad_neta(double utilidad_neta) {
        this.utilidad_neta = utilidad_neta;
    }

    public Date getGenerado_en() {
        return generado_en;
    }

    public void setGenerado_en(Date generado_en) {
        this.generado_en = generado_en;
    }

    

    public PeriodosContables getPeridocontable() {
        return peridocontable;
    }

    public void setPeridocontable(PeriodosContables peridocontable) {
        this.peridocontable = peridocontable;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
}
