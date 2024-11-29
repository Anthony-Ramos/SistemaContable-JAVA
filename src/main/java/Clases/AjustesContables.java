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
public class AjustesContables {
    private int id_ajuste;
    private String descripcion;
    private Date fecha;
    private PeriodosContables periodocontable;
    private Usuarios usuario;

    public AjustesContables() {
    }

    public int getId_ajuste() {
        return id_ajuste;
    }

    public void setId_ajuste(int id_ajuste) {
        this.id_ajuste = id_ajuste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
