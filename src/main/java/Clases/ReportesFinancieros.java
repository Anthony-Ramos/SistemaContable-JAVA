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
public class ReportesFinancieros {
    private int id_reporte;
    private String tipo;
    private Date generado_en;
    private AjustesContables ajustecontable;
    private Usuarios usuario;

    public ReportesFinancieros() {
    }

    public int getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(int id_reporte) {
        this.id_reporte = id_reporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getGenerado_en() {
        return generado_en;
    }

    public void setGenerado_en(Date generado_en) {
        this.generado_en = generado_en;
    }

    

    public AjustesContables getAjustecontable() {
        return ajustecontable;
    }

    public void setAjustecontable(AjustesContables ajustecontable) {
        this.ajustecontable = ajustecontable;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
}
