/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Pantallas.CatalogoDeCuentas;
import Pantallas.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Marlo
 */
public class ControladorIncio implements  ActionListener{
    Menu frmVista;

    public ControladorIncio() {
        this.frmVista = new Menu();
        this.frmVista.setVisible(true);
        
        this.frmVista.btncatalogo.addActionListener(this);
        this.frmVista.btnbalanzacomprobacion.addActionListener(this);
        this.frmVista.btnbalanzageneral.addActionListener(this);
        this.frmVista.btnlibrodiario.addActionListener(this);
        this.frmVista.btnlibromayor.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.frmVista.btncatalogo){
            ControladorCuentas crt = new ControladorCuentas();
        }else if(e.getSource()==this.frmVista.btnbalanzacomprobacion){
            ControladorBalanceDeComprobacion crt1 = new ControladorBalanceDeComprobacion();
        }else if(e.getSource()==this.frmVista.btnbalanzageneral){
            
        }else if(e.getSource()==this.frmVista.btnlibrodiario){
            ControladorLibroDiario crt2 = new ControladorLibroDiario();
        }else if(e.getSource()==this.frmVista.btnlibromayor){
            
        }
    }
    
}
