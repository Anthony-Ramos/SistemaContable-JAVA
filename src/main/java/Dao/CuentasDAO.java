/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Clases.Cuentas;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Marlo
 */
public class CuentasDAO {

    private Conexion conexion;
    private ArrayList<Cuentas> listaCuentas;

    public CuentasDAO() {
        this.conexion = new Conexion();
    }

    //Consultas SQL
    private static final String SQL_MOSTRAR = "SELECT * FROM cuentas ORDER BY codigo DESC";
    private static final String SQL_INSERTAR = "INSERT INTO cuentas(codigo, nombre, tipo, saldo_inicial, saldo_actual, creado_en, saldo_contrario)\n"
            + "VALUES (?,?,?,?,?,?,?);";
    private static final String SQL_ACTUALIZAR = "UPDATE cuentasSET codigo=?, nombre=?, tipo=?, saldo_inicial=?, saldo_actual=?, creado_en=?, saldo_contrario=?	WHERE id_cuenta=?;";
    private static final String SQL_ELIMINAR = "DELETE FROM cuentas WHERE id_cuenta =?";

    //METODO PARA MOSTRAR
    public ArrayList<Cuentas> Mostrar() {
        this.listaCuentas = new ArrayList<>();
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cuentas cuenta = new Cuentas();
                cuenta.setId_cuenta(rs.getInt("id_cuenta"));
                cuenta.setCodigo(rs.getString("codigo"));
                cuenta.setNombre(rs.getString("nombre"));
                cuenta.setTipo(rs.getString("tipo"));
                cuenta.setSaldo_inicial(rs.getDouble("saldo_inicial"));
                cuenta.setSaldo_actual(rs.getDouble("saldo_actual"));
                cuenta.setCreado_en(rs.getDate("creado_en"));
                cuenta.setSaldo_contrario(rs.getBoolean("saldo_contrario"));
                this.listaCuentas.add(cuenta);
            }
        } catch (SQLException e) {
//            DesktopNotify.setDefaultTheme(NotifyTheme.Red);
//            DesktopNotify.showDesktopMessage("Error", "Error en sql", DesktopNotify.ERROR, 3000);
            e.printStackTrace();
        }
        return this.listaCuentas;
    }

//    //METODO PARA INSERTAR
    public boolean insertar(Cuentas cuenta) {
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
            preparedStatement.setString(1, cuenta.getCodigo());
            preparedStatement.setString(2, cuenta.getNombre());
            preparedStatement.setString(3, cuenta.getTipo());
            preparedStatement.setDouble(4, cuenta.getSaldo_inicial());
            preparedStatement.setDouble(5, cuenta.getSaldo_actual());
            preparedStatement.setDate(6, new java.sql.Date(cuenta.getCreado_en().getTime()));
            preparedStatement.setBoolean(7, cuenta.isSaldo_contrario());
            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CuentasDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            this.conexion.cerrarConexiones();
        }
        return resultado;
    }

    // METODO PARA ACTUALIZAR UNA CUENTA
    public boolean actualizar(Cuentas cuenta) throws SQLException {
        String SQL_ACTUALIZAR = "UPDATE cuentas SET codigo = ?, nombre = ?, tipo = ?, saldo_inicial = ?, saldo_actual = ?, creado_en = ?, saldo_contrario = ? WHERE id_cuenta = ?";

        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ACTUALIZAR)) {

            // Asignar parámetros a la consulta
            preparedStatement.setString(1, cuenta.getCodigo()); // Código
            preparedStatement.setString(2, cuenta.getNombre()); // Nombre
            preparedStatement.setString(3, cuenta.getTipo());   // Tipo
            preparedStatement.setDouble(4, cuenta.getSaldo_inicial()); // Saldo inicial
            preparedStatement.setDouble(5, cuenta.getSaldo_actual());  // Saldo actual

            // Convertir java.util.Date a java.sql.Date (si es necesario)
            if (cuenta.getCreado_en() != null) {
                preparedStatement.setDate(6, new java.sql.Date(cuenta.getCreado_en().getTime())); // Fecha de creación
            } else {
                preparedStatement.setDate(6, null); // Manejo de fechas nulas
            }

            preparedStatement.setBoolean(7, cuenta.isSaldo_contrario()); // Saldo contrario
            preparedStatement.setInt(8, cuenta.getId_cuenta());         // ID de la cuenta (WHERE)

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar cuenta: " + ex.getMessage());
            return false;
        }
    }

    public Cuentas obtenerCuentaPorId(int idCuenta) throws SQLException {
        Cuentas cuenta = null;
        String query = "SELECT * FROM cuentas WHERE id_cuenta = ?";
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cuenta = new Cuentas();
                    cuenta.setId_cuenta(rs.getInt("id_cuenta"));
                    cuenta.setCodigo(rs.getString("codigo"));
                    cuenta.setNombre(rs.getString("nombre"));
                    cuenta.setTipo(rs.getString("tipo"));
                    cuenta.setSaldo_inicial(rs.getDouble("saldo_inicial"));
                    cuenta.setSaldo_actual(rs.getDouble("saldo_actual"));
                    cuenta.setCreado_en(rs.getDate("creado_en"));
                    cuenta.setSaldo_contrario(rs.getBoolean("saldo_contrario"));
                }
            }
        }
        return cuenta;
    }   
    public boolean eliminar(int idCuenta) {
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ELIMINAR)) {
            preparedStatement.setInt(1, idCuenta);
            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cuenta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }

}
