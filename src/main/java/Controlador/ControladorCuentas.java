/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Cuentas;
import Dao.CuentasDAO;
import Pantallas.CatalogoDeCuentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marlo
 */
public class ControladorCuentas implements ActionListener{
    private CatalogoDeCuentas frmvista;
    private ArrayList<Cuentas> listaCuentas;
    private CuentasDAO dao;
    private DefaultTableModel modelo;

    public ControladorCuentas() throws SQLException{
        this.frmvista = new CatalogoDeCuentas();
        this.frmvista.setVisible(true);
        
        this.listaCuentas = new ArrayList<>();
        this.dao = new CuentasDAO();
        
        this.modelo = new DefaultTableModel();
        this.modelo.addColumn("Id");
        this.modelo.addColumn("CODIGO");
        this.modelo.addColumn("NOMBRE");
        this.modelo.addColumn("TIPO");
        this.modelo.addColumn("SALDO INICIAL");
        this.modelo.addColumn("SALDO ACTUAL");
        this.modelo.addColumn("CREADO EN");
        this.modelo.addColumn("SALDO CONTRARIO");
        this.frmvista.tabla.setModel(modelo);
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //METODO PARA MOSTRAR LOS DATOS EN LA TABLA
    public void mostrarDatos() throws SQLException{
        this.listaCuentas = this.dao.Mostrar();
        for (Cuentas cuenta : listaCuentas) {
            Object datos[] = {
                cuenta.getId_cuenta(),
                cuenta.getCodigo(),
                cuenta.getNombre(),
                cuenta.getTipo(),
                cuenta.getSaldo_inicial(),
                cuenta.getSaldo_actual(),
                cuenta.getCreado_en(),
                cuenta.isSaldo_contrario()
            };
            this.modelo.addRow(datos);
        }
        this.frmvista.tabla.setModel(modelo);
    }
    
}
