/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.BalanceComprobacion;
import Clases.BalanceTotales;
import Dao.BalanceDeComprobacionDAO;
import Pantallas.BalanceDeComprobacion;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marlo
 */
public class ControladorBalanceDeComprobacion {
    BalanceDeComprobacion frmVista;
    private ArrayList<BalanceComprobacion> listaBalanza;
    private BalanceDeComprobacionDAO dao;
     private DefaultTableModel modelo;

    public ControladorBalanceDeComprobacion() {
        this.frmVista = new BalanceDeComprobacion();
        this.frmVista.setVisible(true);
        
        this.listaBalanza = new ArrayList<>();
        this.dao = new BalanceDeComprobacionDAO();
        
        this.modelo = new DefaultTableModel();
        this.modelo.addColumn("CUENTAS");
        this.modelo.addColumn("DEBE");
        this.modelo.addColumn("HABER");
        this.modelo.addColumn("SALDO DEUDOR");
        this.modelo.addColumn("SALDO ACREEDOR");
        this.frmVista.tbldatos.setModel(modelo);
        mostrarDatos();
        mostrarTotales();
    }
    
    //METODO PARA MOSTRAR LOS DATOS EN LA TABLA
    public void mostrarDatos() {
        this.listaBalanza = this.dao.mostrar();
        for (BalanceComprobacion balance : this.listaBalanza) {
            Object datos[] = {
                balance.getNombreCuenta(),
                balance.getDebe(),
                balance.getHaber(),
                balance.getSaldoDeudor(),
                balance.getSaldoAcreedor()
            };
            this.modelo.addRow(datos);
        }
        this.frmVista.tbldatos.setModel(modelo);
    }

    // MÉTODO PARA MOSTRAR LOS TOTALES 
    public void mostrarTotales() {
        BalanceTotales totales = this.dao.mostrarTotales();
        this.frmVista.txtdebe.setText(String.valueOf(totales.getTotalDebe()));
        this.frmVista.txthaber.setText(String.valueOf(totales.getTotalHaber()));
        this.frmVista.txtdeudor.setText(String.valueOf(totales.getTotalSaldoDeudor()));
        this.frmVista.txtacreedor.setText(String.valueOf(totales.getTotalSaldoAcreedor()));
    }
}
