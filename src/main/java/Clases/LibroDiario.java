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
public class LibroDiario {
    private int id_librodiario;
    private Date fecha;
    private String descripcion;
    private Date creado_en;
    private PeriodosContables periodocontable;
    private Usuarios usuario;

    public LibroDiario() {
    }

    public int getId_librodiario() {
        return id_librodiario;
    }

    public void setId_librodiario(int id_librodiario) {
        this.id_librodiario = id_librodiario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreado_en() {
        return creado_en;
    }

    public void setCreado_en(Date creado_en) {
        this.creado_en = creado_en;
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
