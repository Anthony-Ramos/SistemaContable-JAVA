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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marlo
 */
public class ControladorCuentas implements ActionListener , KeyListener{
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
        this.modelo.addColumn("DESCRIPCION");
        this.modelo.addColumn("TIPO");
        this.frmvista.tabla.setModel(modelo);
        // Ocultar la columna "Id" en la tabla
        this.frmvista.tabla.getColumnModel().getColumn(0).setMinWidth(0);
        this.frmvista.tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        this.frmvista.tabla.getColumnModel().getColumn(0).setWidth(0);
        
        this.frmvista.txtid.setVisible(false);
        
        // Agregar MouseListener a la tabla
        this.frmvista.tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarDatosEnCampos();
            }
        });
        this.frmvista.btnregistrarcuenta.addActionListener(this);
        this.frmvista.btneliminarcuenta.addActionListener(this);
        this.frmvista.btnmodificarcuenta.addActionListener(this);
        mostrarDatos();
        
        this.frmvista.btnmodificarcuenta.setVisible(false);
        this.frmvista.btneliminarcuenta.setVisible(false);
        agregarValidacionNumerica();
        agregarValidacionNumerica1();
        agregarValidacionNumerica2();
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

    //METODO PARA MOSTRAR LOS DATOS EN LA TABLA
    public void mostrarDatos(){
        this.listaCuentas = this.dao.Mostrar();
        for (Cuentas cuenta : listaCuentas) {
            Object datos[] = {
                cuenta.getIdcuenta(),
                cuenta.getCodigocuenta(),
                cuenta.getNombre(),
                cuenta.getDescripcion(),
                cuenta.getTipo()
            };
            this.modelo.addRow(datos);
        }
        this.frmvista.tabla.setModel(modelo);
    }

    //METODO PARA INSERTAR
    public void Registrar(){
        Cuentas cuenta = new Cuentas();
        cuenta.setCodigocuenta(Integer.parseInt(this.frmvista.txtcodigocuenta.getText()));
        cuenta.setNombre(this.frmvista.txtnombrecuenta.getText());
        cuenta.setDescripcion(this.frmvista.txtdescripcion.getText());
        cuenta.setTipo(this.frmvista.combotipocuenta.getSelectedItem().toString());
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
        this.frmvista.txtdescripcion.setText("");
    }
    
    // MÉTODO PARA ACTUALIZAR UNA CUENTA
    public void Actualizar() {
        // Crear un objeto de tipo Cuentas
        Cuentas cuenta = new Cuentas();
        // Asignar valores desde la vista al objeto
        cuenta.setCodigocuenta(Integer.parseInt(this.frmvista.txtcodigocuenta.getText())); // Código de cuenta
        cuenta.setNombre(this.frmvista.txtnombrecuenta.getText());                        // Nombre de la cuenta
        cuenta.setDescripcion(this.frmvista.txtdescripcion.getText());                   // Descripción
        cuenta.setTipo(this.frmvista.combotipocuenta.getSelectedItem().toString());      // Tipo
        cuenta.setIdcuenta(Integer.parseInt(this.frmvista.txtid.getText()));             // ID de la cuenta

        try {
            // Llamar al método actualizar del DAO y guardar el resultado
            this.funciono = this.dao.actualizar(cuenta);

            // Validar si la actualización fue exitosa
            if (funciono) {
                JOptionPane.showMessageDialog(frmvista, "Cuenta actualizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.modelo.setRowCount(0); // Limpiar el modelo de la tabla
                mostrarDatos();             // Mostrar los datos actualizados en la tabla
                limpiar();                  // Limpiar los campos de entrada
                ControladorCuentas crt = new ControladorCuentas();
                this.frmvista.dispose();
            } else {
                JOptionPane.showMessageDialog(frmvista, "No se pudo actualizar la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControladorCuentas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmvista, "Error al actualizar la cuenta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            ControladorCuentas crt = new ControladorCuentas();
            this.frmvista.dispose();
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

    //metodo para cargar datos en campos
    private void cargarDatosEnCampos() {
        this.frmvista.btnmodificarcuenta.setVisible(true);
        this.frmvista.btnregistrarcuenta.setVisible(false);
        this.frmvista.btneliminarcuenta.setVisible(true);
        // Obtener la fila seleccionada
        int filaSeleccionada = this.frmvista.tabla.getSelectedRow();

        // Verificar que se haya seleccionado una fila
        if (filaSeleccionada >= 0) {
            // Obtener los datos de la fila seleccionada
            this.frmvista.txtid.setText(this.modelo.getValueAt(filaSeleccionada, 0).toString());         // ID
            this.frmvista.txtcodigocuenta.setText(this.modelo.getValueAt(filaSeleccionada, 1).toString()); // Código
            this.frmvista.txtnombrecuenta.setText(this.modelo.getValueAt(filaSeleccionada, 2).toString()); // Nombre
            this.frmvista.txtdescripcion.setText(this.modelo.getValueAt(filaSeleccionada, 3).toString()); // Descripción
            this.frmvista.combotipocuenta.setSelectedItem(this.modelo.getValueAt(filaSeleccionada, 4).toString()); // Tipo
        } else {
            JOptionPane.showMessageDialog(frmvista, "Por favor, selecciona un registro válido.");
        }
    }

    private void agregarValidacionNumerica() {
        this.frmvista.txtcodigocuenta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

    }
    
    private void agregarValidacionNumerica1() {
        this.frmvista.txtnombrecuenta.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

    }
    
    private void agregarValidacionNumerica2() {
        this.frmvista.txtdescripcion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

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
