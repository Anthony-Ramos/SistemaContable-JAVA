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
    private static final String SQL_MOSTRAR = "SELECT * FROM cuentas";
    private static final String SQL_INSERTAR = "INSERT INTO cuentas (codigocuenta, nombre, descripcion, tipo) VALUES (?, ?, ?, ?);";
    private static final String SQL_ACTUALIZAR = "UPDATE cuentas SET codigocuenta = ?, nombre = ?, descripcion = ?, tipo = ? WHERE idcuenta = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM cuentas WHERE idcuenta =?";
    

    //METODO PARA MOSTRAR
    public ArrayList<Cuentas> Mostrar(){
        this.listaCuentas = new ArrayList<>();
        try( Connection connection = this.conexion.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR);){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Cuentas cuenta = new Cuentas();
                cuenta.setIdcuenta(rs.getInt("idcuenta"));
                cuenta.setCodigocuenta(Integer.parseInt(rs.getString("codigocuenta")));
                cuenta.setNombre(rs.getString("nombre"));
                cuenta.setDescripcion(rs.getString("descripcion"));
                cuenta.setTipo(rs.getString("tipo"));
           
                this.listaCuentas.add(cuenta);
            }
        }catch(SQLException e){
//            DesktopNotify.setDefaultTheme(NotifyTheme.Red);
//            DesktopNotify.showDesktopMessage("Error", "Error en sql", DesktopNotify.ERROR, 3000);
            e.printStackTrace();
        }
        return this.listaCuentas;
    }
    
//    //METODO PARA INSERTAR
    public boolean insertar(Cuentas cuenta){
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); 
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
            preparedStatement.setInt(1, cuenta.getCodigocuenta());
            preparedStatement.setString(2, cuenta.getNombre());
            preparedStatement.setString(3, cuenta.getDescripcion());
            preparedStatement.setString(4, cuenta.getTipo());
            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CuentasDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            this.conexion.cerrarConexiones();
        }
        return resultado;
    }
    
    
    // MÉTODO PARA ACTUALIZAR UNA CUENTA
    public boolean actualizar(Cuentas cuenta) throws SQLException {
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ACTUALIZAR)) {
            // Asignar parámetros a la consulta
            preparedStatement.setInt(1, cuenta.getCodigocuenta());   // Código de la cuenta
            preparedStatement.setString(2, cuenta.getNombre());      // Nombre
            preparedStatement.setString(3, cuenta.getDescripcion()); // Descripción
            preparedStatement.setString(4, cuenta.getTipo());        // Tipo
            preparedStatement.setInt(5, cuenta.getIdcuenta());       // ID de la cuenta (WHERE)
            // Ejecutar la consulta
            int filasAfectadas = preparedStatement.executeUpdate();
            // Verificar si la actualización fue exitosa
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar cuenta: " + ex.getMessage());
            return false;
        }
    }
    
    //METODO PARA OBTENER CUENTA POR ID
    public Cuentas obtenerCuentaPorId(int idCuenta) throws SQLException {
        Cuentas cuenta = null;
        String query = "SELECT * FROM cuentas WHERE idcuenta = ?";
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(query)) {

            // Asignar el parámetro al PreparedStatement
            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                // Si se encuentra un resultado, asignarlo a un objeto Cuentas
                if (rs.next()) {
                    cuenta = new Cuentas();
                    cuenta.setIdcuenta(rs.getInt("idcuenta"));          // ID de la cuenta
                    cuenta.setCodigocuenta(rs.getInt("codigocuenta"));  // Código de la cuenta
                    cuenta.setNombre(rs.getString("nombre"));          // Nombre
                    cuenta.setDescripcion(rs.getString("descripcion"));// Descripción
                    cuenta.setTipo(rs.getString("tipo"));              // Tipo
                }
            }
        }
        return cuenta; // Retorna la cuenta o null si no se encuentra
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
