/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Cuentas;
import Clases.Movimientos;
import Clases.Partidas;
import Dao.MovimientosDao;
import Pantallas.LibroDiario;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorLibroDiario implements ActionListener {

    private LibroDiario frmvista;
    private ArrayList<Movimientos> listaMovimientos;
    private MovimientosDao dao;
    private DefaultTableModel modelo;
    private boolean partidaTerminada; // Nuevo campo para controlar si la partida ha terminado
    private int numeroPartidaActual; // Variable para almacenar el número de partida actual

    public ControladorLibroDiario() {
        this.frmvista = new LibroDiario();
        this.frmvista.setVisible(true);
        this.listaMovimientos = new ArrayList<>();
        this.dao = new MovimientosDao();
        this.modelo = new DefaultTableModel();
        this.partidaTerminada = false; // Inicializamos como partida no terminada

        // Agregar columnas a la tabla
        this.modelo.addColumn("PARTIDA");
        this.modelo.addColumn("FECHA");
        this.modelo.addColumn("CUENTA");
        this.modelo.addColumn("DEBE");
        this.modelo.addColumn("HABER");

        // Asociar el modelo a la tabla
        this.frmvista.tbLibroDiario.setModel(modelo);

        // Eventos de los botones
        this.frmvista.btnAgregar.addActionListener(this);
        this.frmvista.btnBuscar.addActionListener(this);
        this.frmvista.btnTerminarPartida.addActionListener(this);  // Nuevo botón para terminar partida

        mostrarDatos();  // Mostrar los datos al iniciar
        mostrarUltimosNumerosPartida(); // Mostrar los números de las partidas
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmvista.btnBuscar) {
            buscarCuenta();
        } else if (e.getSource() == frmvista.btnAgregar) {
            insertarMovimiento();
        } else if (e.getSource() == frmvista.btnTerminarPartida) {
            terminarPartida(); // Acción para terminar la partida
        }
    }

    // Método para mostrar los movimientos en la tabla
    public void mostrarDatos() {
        // Limpiar el modelo antes de mostrar los nuevos datos
        this.modelo.setRowCount(0);

        // Obtener los movimientos desde el DAO
        this.listaMovimientos = this.dao.mostrar();

        // Llenar la tabla con los datos de los movimientos
        for (Movimientos movimiento : listaMovimientos) {
            Object[] datos = {
                movimiento.getIdpartida().getIdpartida(), // Partida
                movimiento.getIdpartida().getFecha(), // Fecha
                movimiento.getIdcuenta().getNombre(), // Cuenta
                "$ " + movimiento.getCargo(), // Cargo (Debe)
                "$ " + movimiento.getAbono() // Abono (Haber)
            };
            this.modelo.addRow(datos);
        }

        // Actualizar la tabla
        this.frmvista.tbLibroDiario.setModel(modelo);
    }

    // Método para mostrar los últimos números de partida en los campos de texto
    public void mostrarUltimosNumerosPartida() {
        int ultimoNumeroPartida = dao.obtenerUltimoNumeroPartida();
        if (ultimoNumeroPartida == 1) {
            ultimoNumeroPartida = 0;
        }

        // Si no hay ninguna partida registrada, iniciar en 1
        // Solo incrementa el contador si la partida ha sido terminada
        numeroPartidaActual = (ultimoNumeroPartida == 0 || partidaTerminada) ? 1 : ultimoNumeroPartida;

        // Mostrar el número de la partida actual y anterior en los campos de texto
        frmvista.txtNumeroActual.setText(String.valueOf(numeroPartidaActual));
        frmvista.txtNumeroAnterior.setText(String.valueOf(ultimoNumeroPartida));
    }

    // Método para buscar cuenta por código y mostrar la descripción
    private void buscarCuenta() {
        try {
            int codigoCuenta = Integer.parseInt(frmvista.txtCodigoCuenta.getText());
            Cuentas cuenta = dao.buscarCuentaPorCodigo(codigoCuenta);

            if (cuenta != null) {
                frmvista.txtDescripcionCuenta.setText(cuenta.getNombre());
            } else {
                JOptionPane.showMessageDialog(frmvista, "Cuenta no encontrada");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frmvista, "Ingrese un código de cuenta válido");
        }
    }

    private void insertarMovimiento() {
        try {
            // Obtener datos de la vista
            int codigoCuenta = Integer.parseInt(frmvista.txtCodigoCuenta.getText());
            double monto = Double.parseDouble(frmvista.txtMonto.getText());
            String comentario = frmvista.txtComentarioPartida.getText();
            int numeroPartida = Integer.parseInt(frmvista.txtNumeroActual.getText());
            System.out.println("NUMERO DE LA PARTIDA: " + numeroPartida);

            // Buscar cuenta
            Cuentas cuenta = dao.buscarCuentaPorCodigo(codigoCuenta);
            if (cuenta == null) {
                JOptionPane.showMessageDialog(frmvista, "Cuenta no encontrada");
                return;
            }

            // Buscar la partida existente
            Partidas partida = dao.buscarPartidaPorNumeroYFecha(numeroPartida, Date.valueOf(LocalDate.now()));

            if (partida == null) {
                JOptionPane.showMessageDialog(frmvista, "Partida no encontrada");
                return; // Si no existe la partida, no se crea un movimiento
            }

            // Crear movimiento
            Movimientos movimiento = new Movimientos();
            movimiento.setIdcuenta(cuenta); // Establecer la cuenta
            movimiento.setIdpartida(partida); // Establecer la partida

            // Verificar si es cargo o abono
            if (frmvista.jRadioButton1.isSelected()) { // Cargo
                movimiento.setCargo(monto);
                movimiento.setAbono(0.0);
            } else if (frmvista.jRadioButton2.isSelected()) { // Abono
                movimiento.setCargo(0.0);
                movimiento.setAbono(monto);
            } else {
                JOptionPane.showMessageDialog(frmvista, "Seleccione Cargo o Abono");
                return;
            }

            // Insertar movimiento
            boolean resultado = dao.insertar(movimiento);
            if (resultado) {
                mostrarDatos();
                mostrarUltimosNumerosPartida(); // No incrementará el contador aquí
                limpiarCampos();
                JOptionPane.showMessageDialog(frmvista, "Movimiento insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(frmvista, "Error al insertar movimiento");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frmvista, "Verifique los datos ingresados");
        }
    }

    // Método para terminar la partida y permitir crear una nueva
    private void terminarPartida() {
        partidaTerminada = true; // Marcar que la partida ha terminado
        JOptionPane.showMessageDialog(frmvista, "La partida ha terminado. Puede crear una nueva partida.");
        mostrarUltimosNumerosPartida(); // Ahora se incrementa el número de la partida
    }

    // Método para limpiar los campos de la vista
    private void limpiarCampos() {
        frmvista.txtCodigoCuenta.setText("");
        frmvista.txtDescripcionCuenta.setText("");
        frmvista.txtMonto.setText("");
        frmvista.txtComentarioPartida.setText("");
    }
}
