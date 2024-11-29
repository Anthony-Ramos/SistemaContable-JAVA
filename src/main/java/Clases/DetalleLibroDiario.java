/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Marlo
 */
public class DetalleLibroDiario {
    private int id_detalle;
    private String tipo;
    private double monto;
    private LibroDiario librodiario;
    private Cuentas cuenta;

    public DetalleLibroDiario() {
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LibroDiario getLibrodiario() {
        return librodiario;
    }

    public void setLibrodiario(LibroDiario librodiario) {
        this.librodiario = librodiario;
    }

    public Cuentas getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }
    
}
