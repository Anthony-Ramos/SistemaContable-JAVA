package Clases;

import java.sql.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Marlo
 */
public class Partidas {
    private int idpartida;
    private Date fecha;
    private String descripcion;
    private int numeropartida;

    public Partidas() {
    }

    public int getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(int idpartida) {
        this.idpartida = idpartida;
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

    public int getNumeropartida() {
        return numeropartida;
    }

    public void setNumeropartida(int numeropartida) {
        this.numeropartida = numeropartida;
    }
    
}
