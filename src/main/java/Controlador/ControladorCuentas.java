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
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
    private boolean funciono = false;
    
    

    public ControladorCuentas(){
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
        
        this.frmvista.txtid.setVisible(false);
        
        this.frmvista.btnregistrarcuenta.addActionListener(this);
        this.frmvista.btneliminarcuenta.addActionListener(this);
        this.frmvista.btnmodificarcuenta.addActionListener(this);
        mostrarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.frmvista.btnregistrarcuenta) {
            Registrar();
        }
    }

    //METODO PARA MOSTRAR LOS DATOS EN LA TABLA
    public void mostrarDatos(){
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

    //METODO PARA INSERTAR
    public void Registrar(){
        Cuentas cuenta = new Cuentas();
        cuenta.setCodigo(this.frmvista.txtcodigocuenta.getText());
        cuenta.setNombre(this.frmvista.txtnombrecuenta.getText());
        cuenta.setTipo(this.frmvista.combotipocuenta.getSelectedItem().toString());
        cuenta.setSaldo_inicial(Double.parseDouble(this.frmvista.txtsaldoinicial.getText()));
        cuenta.setSaldo_actual(Double.parseDouble(this.frmvista.txtsaldoactual.getText()));
        
        java.util.Date utilDate = this.frmvista.calendario.getDatoFecha();
        if(utilDate != null){
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            cuenta.setCreado_en(sqlDate);
        }else{
            JOptionPane.showMessageDialog(frmvista, utilDate);
        }
        
        if (this.frmvista.radiosi.isSelected()) {
            cuenta.setSaldo_contrario(true);
        } else if (this.frmvista.radiono.isSelected()) {
            cuenta.setSaldo_contrario(false);
        }
        this.funciono = this.dao.insertar(cuenta);
        if (funciono) {
            //DesktopNotify.showDesktopMessage("Éxito", "Bartender registrado con éxito", DesktopNotify.SUCCESS, 3000);
            this.modelo.setRowCount(0);
            mostrarDatos();
            limpiar();
        } else {
            //DesktopNotify.showDesktopMessage("Error", "No se pudo registrar el Bartender", DesktopNotify.ERROR, 3000);
        }
    }
    
    public void limpiar(){
        this.frmvista.txtcodigocuenta.setText("");
        this.frmvista.txtnombrecuenta.setText("");
        this.frmvista.txtsaldoinicial.setText("");
        this.frmvista.txtsaldoactual.setText("");
    }

    
}
