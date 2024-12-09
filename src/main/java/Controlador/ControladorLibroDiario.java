/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.Movimientos;
import Dao.MovimientosDao;
import Pantallas.LibroDiario;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ControladorLibroDiario implements ActionListener {

    private LibroDiario frmvista;
    private ArrayList<Movimientos> listaMovimientos;
    private MovimientosDao dao;
    private DefaultTableModel modelo;

    public ControladorLibroDiario() {
        this.frmvista = new LibroDiario();
        this.frmvista.setVisible(true);

        this.listaMovimientos = new ArrayList<>();
        this.dao = new MovimientosDao();

        this.modelo = new DefaultTableModel();

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

        mostrarDatos();  // Mostrar los datos al iniciar
        mostrarUltimosNumerosPartida(); // Mostrar los números de las partidas
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
                movimiento.getCargo(), // Cargo (Debe)
                movimiento.getAbono() // Abono (Haber)
            };
            this.modelo.addRow(datos);
        }

        // Actualizar la tabla
        this.frmvista.tbLibroDiario.setModel(modelo);
    }

    // Método para mostrar los últimos números de partida en los campos de texto
    public void mostrarUltimosNumerosPartida() {
        int ultimoNumeroPartida = dao.obtenerUltimoNumeroPartida();
        // Si no hay ninguna partida registrada, iniciar en 1
        int numeroActual = (ultimoNumeroPartida == 0) ? 1 : ultimoNumeroPartida + 1;
        int numeroAnterior = ultimoNumeroPartida; // El anterior será el último número registrado

        // Mostrar el número de la partida actual y anterior en los campos de texto
        frmvista.txtNumeroActual.setText(String.valueOf(numeroActual));
        frmvista.txtNumeroAnterior.setText(String.valueOf(numeroAnterior));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Aquí irían las acciones de los botones, si fuera necesario
    }
}
