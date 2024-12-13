/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Partidas;
import Dao.PartidasDAO;
import Pantallas.VistaPartidas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marlo
 */
public class ControladorPartidas implements ActionListener, KeyListener {

    private VistaPartidas frmVista;
    private ArrayList<Partidas> listaPartidas;
    private PartidasDAO dao;
    private DefaultTableModel modelo;
    private boolean funciono = false;

    public ControladorPartidas() {
        this.frmVista = new VistaPartidas();
        this.frmVista.setVisible(true);

        this.listaPartidas = new ArrayList<>();
        this.dao = new PartidasDAO();

        this.modelo = new DefaultTableModel();
        this.modelo.addColumn("PARTIDA");
        this.modelo.addColumn("FECHA");
        this.modelo.addColumn("DESCRIPCIÓN");
        this.frmVista.tbldatos.setModel(modelo);

        this.frmVista.btnregistrar.addActionListener(this);
        mostrarDatos();
        agregarValidacionNumerica();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.frmVista.btnregistrar) {
            if (!this.frmVista.txtnumeropartida.getText().isEmpty() && !this.frmVista.txtdescripcion.getText().isEmpty()) {
                if (validarEntrada()) {
                    Registrar();
                }
            } else {
                JOptionPane.showMessageDialog(frmVista, "Datos vacios");
            }
        }
    }

    //METODO PARA MOSTRAR LOS DATOS EN LA TABLA
    public void mostrarDatos() {
        this.listaPartidas = this.dao.Mostrar();
        for (Partidas partida : listaPartidas) {
            Object datos[] = {
                partida.getNumeroPartida(),
                partida.getFecha(),
                partida.getDescripcion()
            };
            this.modelo.addRow(datos);
        }
        this.frmVista.tbldatos.setModel(modelo);
    }

    public void Registrar() {
        Partidas partidas = new Partidas();

        // Convertir java.util.Date a java.sql.Date
        java.util.Date utilDate;
        utilDate = this.frmVista.calendario.getDatoFecha();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        partidas.setFecha(sqlDate);
        partidas.setDescripcion(this.frmVista.txtdescripcion.getText());
        partidas.setNumeroPartida(Integer.parseInt(this.frmVista.txtnumeropartida.getText()));
        this.funciono = this.dao.insertar(partidas);
        if (funciono) {
            this.modelo.setRowCount(0);
            mostrarDatos();
            limpiar();
        } else {
        }
    }

    private void limpiar() {
        this.frmVista.calendario.setDatoFecha(null);
        this.frmVista.txtdescripcion.setText("");
        this.frmVista.txtnumeropartida.setText("");
    }

    private void agregarValidacionNumerica() {
        this.frmVista.txtnumeropartida.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

    }

    private boolean validarEntrada() {
        int prueba = Integer.parseInt(this.frmVista.txtnumeropartida.getText());

        if (prueba < 1) {
            JOptionPane.showMessageDialog(frmVista, "El valor debe ser mayor a 0");
            this.frmVista.txtnumeropartida.setText("");
            return false;
        }

        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}