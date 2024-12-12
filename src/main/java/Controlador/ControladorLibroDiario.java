package Controlador;

import Clases.Cuentas;
import Clases.Movimientos;
import Clases.Partidas;
import Dao.MovimientosDao;
import Pantallas.LibroDiario;

import javax.swing.JOptionPane;

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
    private int numeroPartidaActual;
    private boolean partidaTerminada;

    public ControladorLibroDiario() {
        this.frmvista = new LibroDiario();
        this.frmvista.setVisible(true);
        this.listaMovimientos = new ArrayList<>();
        this.dao = new MovimientosDao();
        this.modelo = new DefaultTableModel();
        this.partidaTerminada = false;

        // Inicializar columnas de la tabla
        this.modelo.addColumn("PARTIDA");
        this.modelo.addColumn("FECHA");
        this.modelo.addColumn("CUENTA");
        this.modelo.addColumn("DEBE");
        this.modelo.addColumn("HABER");

        this.frmvista.tbLibroDiario.setModel(modelo);

        // Eventos de los botones
        this.frmvista.btnAgregar.addActionListener(this);
        this.frmvista.btnBuscar.addActionListener(this);
        this.frmvista.btnTerminarPartida.addActionListener(this);
        this.frmvista.btnapertura.addActionListener(this);

        mostrarDatos();
        verificarPartidaExistente();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmvista.btnBuscar) {
            buscarCuenta();
        } else if (e.getSource() == frmvista.btnAgregar) {
            insertarMovimiento();
        } else if (e.getSource() == frmvista.btnTerminarPartida) {
            terminarPartida();
        } else if (e.getSource() == this.frmvista.btnapertura) {
            inicializarPartida();
        }
    }

    private void inicializarPartida() {
        try {
            // Verificamos si ya hay una partida inicial para la fecha actual
            int ultimaPartida = dao.obtenerUltimaPartidaFechaActual();

            if (ultimaPartida == 0) {
                // No existe ninguna partida con la fecha actual, insertar partida 1
                Partidas nuevaPartida = new Partidas();
                nuevaPartida.setFecha(Date.valueOf(LocalDate.now()));
                nuevaPartida.setDescripcion("");  // La descripción se puede agregar después
                nuevaPartida.setNumeroPartida(1);

                boolean resultado = dao.insertarPartida(nuevaPartida);
                if (resultado) {
                    numeroPartidaActual = 1;
                    frmvista.txtNumeroActual.setText(String.valueOf(numeroPartidaActual));
                    partidaTerminada = false;
                    JOptionPane.showMessageDialog(frmvista, "Partida 1 iniciada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(frmvista, "Error al crear la partida 1.");
                }
            } else {
                // Si ya existe la partida 1, mostrar un mensaje
                JOptionPane.showMessageDialog(frmvista, "Ya existe la partida 1 para la fecha actual.");
                frmvista.txtNumeroActual.setText(String.valueOf(ultimaPartida));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmvista, "Error al inicializar la partida.");
            ex.printStackTrace();
        }
    }

    public void verificarPartidaExistente() {
        int ultimaPartida = dao.obtenerUltimaPartidaFechaActual();
        if (ultimaPartida != 0) {
            numeroPartidaActual = ultimaPartida;
            frmvista.txtNumeroActual.setText(String.valueOf(numeroPartidaActual));
        } else {
            numeroPartidaActual = 1;
            frmvista.txtNumeroActual.setText(String.valueOf(numeroPartidaActual));
        }
    }

    private void mostrarDatos() {
        this.modelo.setRowCount(0);  // Limpiar la tabla antes de mostrar los nuevos datos
        this.listaMovimientos = this.dao.mostrar();  // Obtener todos los movimientos desde la base de datos

        for (Movimientos movimiento : listaMovimientos) {
            // Cambiar el idpartida por el numeroPartida
            Object[] datos = {
                movimiento.getIdpartida().getNumeroPartida(),
                movimiento.getIdpartida().getFecha(),
                movimiento.getIdpartida().getDescripcion(),
                "$ " + movimiento.getCargo(),
                "$ " + movimiento.getAbono()
            };
            this.modelo.addRow(datos);  // Agregar los datos a la tabla
        }

        this.frmvista.tbLibroDiario.setModel(modelo);  // Actualizar la tabla con los nuevos datos

        // Llamar a actualizarTotales después de mostrar los movimientos
        actualizarTotales();
    }

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
            int codigoCuenta = Integer.parseInt(frmvista.txtCodigoCuenta.getText());
            double monto = Double.parseDouble(frmvista.txtMonto.getText());
            int numeroPartida = Integer.parseInt(frmvista.txtNumeroActual.getText());
            Cuentas cuenta = dao.buscarCuentaPorCodigo(codigoCuenta);
            if (cuenta == null) {
                JOptionPane.showMessageDialog(frmvista, "Cuenta no encontrada");
                return;
            }
            Partidas partida = dao.buscarPartidaPorNumero(numeroPartida);
            if (partida == null) {
                JOptionPane.showMessageDialog(frmvista, "Partida no encontrada");
                return;
            }

            Movimientos movimiento = new Movimientos();
            movimiento.setIdcuenta(cuenta);
            movimiento.setIdpartida(partida);

            if (frmvista.jRadioButton1.isSelected()) {
                movimiento.setCargo(monto);
                movimiento.setAbono(0.0);
            } else if (frmvista.jRadioButton2.isSelected()) {
                movimiento.setCargo(0.0);
                movimiento.setAbono(monto);
            } else {
                JOptionPane.showMessageDialog(frmvista, "Seleccione Cargo o Abono");
                return;
            }

            boolean resultado = dao.insertar(movimiento);
            if (resultado) {
                mostrarDatos();
                JOptionPane.showMessageDialog(frmvista, "Movimiento insertado exitosamente");
            } else {
                JOptionPane.showMessageDialog(frmvista, "Error al insertar movimiento");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frmvista, "Verifique los datos ingresados");
        }
    }

    private void terminarPartida() {
        try {
            int numeroPartida = Integer.parseInt(frmvista.txtNumeroActual.getText());
            String descripcion = JOptionPane.showInputDialog(frmvista, "Ingrese una descripción para la partida:");

            if (descripcion != null && !descripcion.trim().isEmpty()) {
                // Actualizar la partida con la descripción
                Partidas partida = dao.buscarPartidaPorNumero(numeroPartida);
                partida.setDescripcion(descripcion);
                boolean resultado = dao.actualizarPartida(partida);
                if (resultado) {
                    JOptionPane.showMessageDialog(frmvista, "Partida " + numeroPartida + " actualizada correctamente.");
                    // Insertar nueva partida sin descripción
                    Partidas nuevaPartida = new Partidas();
                    nuevaPartida.setFecha(Date.valueOf(LocalDate.now()));
                    nuevaPartida.setDescripcion("");  // Sin descripción
                    nuevaPartida.setNumeroPartida(numeroPartida + 1);

                    boolean resultadoNuevaPartida = dao.insertarPartida(nuevaPartida);
                    if (resultadoNuevaPartida) {
                        numeroPartidaActual = numeroPartida + 1;
                        frmvista.txtNumeroActual.setText(String.valueOf(numeroPartidaActual));
                        JOptionPane.showMessageDialog(frmvista, "Partida " + (numeroPartida + 1) + " iniciada.");
                    } else {
                        JOptionPane.showMessageDialog(frmvista, "Error al crear la nueva partida.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frmvista, "Error al actualizar la partida.");
                }
            } else {
                JOptionPane.showMessageDialog(frmvista, "Debe ingresar una descripción para la partida.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmvista, "Ocurrió un error al terminar la partida.");
            ex.printStackTrace();
        }
    }

    private void actualizarTotales() {
        double totalCargo = dao.obtenerTotalCargoPorAnoActual();
        double totalAbono = dao.obtenerTotalAbonoPorAnoActual();

        // Mostrar los totales en los JTextField correspondientes
        frmvista.txtTotalCargo.setText("$ " + String.format("%.2f", totalCargo));
        frmvista.txtTotalAbono.setText("$ " + String.format("%.2f", totalAbono));
    }

}
