package Controlador;

import Clases.Cuentas;
import Dao.CuentasDAO;
import Pantallas.CatalogoDeCuentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorCuentas implements ActionListener {

    private CatalogoDeCuentas frmvista;
    private ArrayList<Cuentas> listaCuentas;
    private CuentasDAO dao;
    private DefaultTableModel modelo;
    private boolean funciono = false;

    public ControladorCuentas() {
        this.frmvista = new CatalogoDeCuentas();
        this.frmvista.setVisible(true);

        this.listaCuentas = new ArrayList<>();
        this.dao = new CuentasDAO();

        this.modelo = new DefaultTableModel();
        this.modelo.addColumn("ID");
        this.modelo.addColumn("CODIGO");
        this.modelo.addColumn("NOMBRE");
        this.modelo.addColumn("TIPO");
        this.modelo.addColumn("SALDO INICIAL");
        this.modelo.addColumn("SALDO ACTUAL");
        this.modelo.addColumn("CREADO EN");
        this.modelo.addColumn("SALDO CONTRARIO");
        this.frmvista.tabla.setModel(modelo);

        this.frmvista.txtid.setVisible(false);
        
        // Ocultar la columna "ID"
this.frmvista.tabla.getColumnModel().getColumn(0).setMaxWidth(0);  // Establecer el ancho de la columna a 0
this.frmvista.tabla.getColumnModel().getColumn(0).setMinWidth(0);  // Establecer el ancho mínimo de la columna a 0
this.frmvista.tabla.getColumnModel().getColumn(0).setPreferredWidth(0);  // Establecer el ancho preferido a 0

        // Agregar los ActionListeners para los botones
        this.frmvista.btnregistrarcuenta.addActionListener(this);
        this.frmvista.btneliminarcuenta.addActionListener(this);
        this.frmvista.btnmodificarcuenta.addActionListener(this);

        // Mostrar los datos en la tabla al iniciar
        mostrarDatos();

        // Configurar el listener para la selección de una fila en la tabla
        frmvista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = frmvista.tabla.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el ID de la cuenta de la fila seleccionada
                    int idCuenta = (int) frmvista.tabla.getValueAt(filaSeleccionada, 0);
                    cargarCuentaEnFormulario(idCuenta);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.frmvista.btnregistrarcuenta) {
            Registrar();
        }
        if (e.getSource() == this.frmvista.btnmodificarcuenta) {
            Actualizar();
        }
        if (e.getSource() == this.frmvista.btneliminarcuenta) {
            Eliminar();
        }
    }

    // Método para mostrar los datos en la tabla
    public void mostrarDatos() {
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

    // Método para insertar una nueva cuenta
    public void Registrar() {
        Cuentas cuenta = new Cuentas();
        cuenta.setCodigo(this.frmvista.txtcodigocuenta.getText());
        cuenta.setNombre(this.frmvista.txtnombrecuenta.getText());
        cuenta.setTipo(this.frmvista.combotipocuenta.getSelectedItem().toString());
        cuenta.setSaldo_inicial(Double.parseDouble(this.frmvista.txtsaldoinicial.getText()));
        cuenta.setSaldo_actual(Double.parseDouble(this.frmvista.txtsaldoactual.getText()));

        java.util.Date utilDate = this.frmvista.calendario.getDatoFecha();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            cuenta.setCreado_en(sqlDate);
        } else {
            JOptionPane.showMessageDialog(frmvista, "Por favor, selecciona una fecha válida.");
            return;
        }

        if (this.frmvista.radiosi.isSelected()) {
            cuenta.setSaldo_contrario(true);
        } else if (this.frmvista.radiono.isSelected()) {
            cuenta.setSaldo_contrario(false);
        }
 
        this.funciono = this.dao.insertar(cuenta);
        if (funciono) {
            this.modelo.setRowCount(0);
            mostrarDatos();
            limpiar();
            JOptionPane.showMessageDialog(frmvista, "Cuenta registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frmvista, "No se pudo registrar la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar los campos del formulario
    public void limpiar() {
        this.frmvista.txtcodigocuenta.setText("");
        this.frmvista.txtnombrecuenta.setText("");
        this.frmvista.txtsaldoinicial.setText("");
        this.frmvista.txtsaldoactual.setText("");
        this.frmvista.txtid.setText("");
        this.frmvista.calendario.setDatoFecha(null);
        this.frmvista.radiosi.setSelected(false);
        this.frmvista.radiono.setSelected(false);
    }

    // Método para obtener los datos de una cuenta por su ID y cargarla en el formulario
    public void cargarCuentaEnFormulario(int idCuenta) {
        try {
            Cuentas cuentaSeleccionada = dao.obtenerCuentaPorId(idCuenta);
            if (cuentaSeleccionada != null) {
                // Cargar los datos de la cuenta seleccionada en los campos del formulario
                frmvista.txtid.setText(String.valueOf(cuentaSeleccionada.getId_cuenta()));
                frmvista.txtcodigocuenta.setText(cuentaSeleccionada.getCodigo());
                frmvista.txtnombrecuenta.setText(cuentaSeleccionada.getNombre());
                frmvista.combotipocuenta.setSelectedItem(cuentaSeleccionada.getTipo());
                frmvista.txtsaldoinicial.setText(String.valueOf(cuentaSeleccionada.getSaldo_inicial()));
                frmvista.txtsaldoactual.setText(String.valueOf(cuentaSeleccionada.getSaldo_actual()));
                frmvista.calendario.setDatoFecha(cuentaSeleccionada.getCreado_en());

                // Ajustar el estado del radio button para "Saldo contrario"
                if (cuentaSeleccionada.isSaldo_contrario()) {
                    frmvista.radiosi.setSelected(true);
                } else {
                    frmvista.radiono.setSelected(true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para actualizar una cuenta
    public void Actualizar() {
        Cuentas cuenta = new Cuentas();

        cuenta.setCodigo(this.frmvista.txtcodigocuenta.getText());
        cuenta.setNombre(this.frmvista.txtnombrecuenta.getText());
        cuenta.setTipo(this.frmvista.combotipocuenta.getSelectedItem().toString());
        cuenta.setSaldo_inicial(Double.parseDouble(this.frmvista.txtsaldoinicial.getText()));
        cuenta.setSaldo_actual(Double.parseDouble(this.frmvista.txtsaldoactual.getText()));

        java.util.Date utilDate = this.frmvista.calendario.getDatoFecha();
        if (utilDate != null) {
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            cuenta.setCreado_en(sqlDate);
        } else {
            JOptionPane.showMessageDialog(frmvista, "Por favor, selecciona una fecha válida.");
            return;
        }

        if (this.frmvista.radiosi.isSelected()) {
            cuenta.setSaldo_contrario(true);
        } else if (this.frmvista.radiono.isSelected()) {
            cuenta.setSaldo_contrario(false);
        }

        cuenta.setId_cuenta(Integer.parseInt(this.frmvista.txtid.getText()));

        try {
            this.funciono = this.dao.actualizar(cuenta);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (funciono) {
            JOptionPane.showMessageDialog(frmvista, "Cuenta actualizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.modelo.setRowCount(0);
            mostrarDatos();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(frmvista, "No se pudo actualizar la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Eliminar() {
        // Obtener el ID de la cuenta desde el campo oculto (txtid)
        int idCuenta = Integer.parseInt(this.frmvista.txtid.getText());

        // Llamar al método eliminar del DAO
        boolean resultado = this.dao.eliminar(idCuenta);

        if (resultado) {
            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(
                    null,
                    "Cuenta eliminada con éxito.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Limpiar la tabla y volver a cargar los datos
            this.modelo.setRowCount(0); // Limpiar tabla
            mostrarDatos(); // Recargar datos
            limpiar(); // Limpiar campos del formulario
        } else {
            // Mostrar mensaje de error
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo eliminar la cuenta.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
