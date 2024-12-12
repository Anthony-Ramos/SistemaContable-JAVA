package Controlador;

import Clases.Movimientos;
import Dao.LibroMayorDAO;
import Pantallas.LibroMayor;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControladorLibroMayor implements ActionListener {

    private LibroMayor frmLibroMayor;
    private LibroMayorDAO dao;
    private DefaultTableModel modelo;

    public ControladorLibroMayor() {
        this.frmLibroMayor = new LibroMayor();
        this.dao = new LibroMayorDAO();

        this.frmLibroMayor.setVisible(true);
        this.frmLibroMayor.btnBuscar.addActionListener(this);

        // Configurar el modelo de la tabla con las columnas adecuadas
        this.modelo = new DefaultTableModel(
                new Object[]{"Fecha", "Descripción", "REF", "Debe", "Haber", "Saldo", "Tipo de Saldo"}, 0
        );
        this.frmLibroMayor.jTable1.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmLibroMayor.btnBuscar) {
            mostrarLibroMayor();
        }
    }

    // Método para mostrar los datos del Libro Mayor
    private void mostrarLibroMayor() {
        try {
            int codigoCuenta = Integer.parseInt(frmLibroMayor.txtBuscar.getText());
            cargarMovimientos(codigoCuenta);
        } catch (NumberFormatException ex) {
            frmLibroMayor.txtCuenta.setText("Código inválido");
            limpiarTabla();
        }
    }

    private void cargarMovimientos(int codigoCuenta) {
    ArrayList<Movimientos> listaMovimientos = dao.obtenerMovimientosPorCodigoCuenta(codigoCuenta);
    modelo.setRowCount(0); // Limpiar tabla
    
    // Obtener el nombre de la cuenta
    String nombreCuenta = dao.obtenerNombreCuentaPorCodigo(codigoCuenta);
    frmLibroMayor.txtCuenta.setText("Cuenta: " + nombreCuenta); // Mostrar nombre de cuenta

    double saldo = 0.0;

    for (Movimientos mov : listaMovimientos) {
        saldo += mov.getCargo() - mov.getAbono();
        Object[] fila = {
            mov.getIdpartida().getFecha(),
            mov.getIdpartida().getDescripcion(),
            mov.getIdmovimiento(),  // REF
            mov.getCargo(),
            mov.getAbono(),
            saldo,
            saldo >= 0 ? "Deudor" : "Acreedor"
        };
        modelo.addRow(fila);
    }

    if (listaMovimientos.isEmpty()) {
        frmLibroMayor.txtCuenta.setText("No se encontraron datos para el código.");
    }
}


    private void limpiarTabla() {
        modelo.setRowCount(0);
    }
}
