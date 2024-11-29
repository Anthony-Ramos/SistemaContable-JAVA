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
    private static final String SQL_INSERTAR = "INSERT INTO cuentas(codigo, nombre, tipo, saldo_inicial, saldo_actual, creado_en, saldo_contrario)\n"
            + "VALUES (?,?,?,?,?,?,?);";
    private static final String SQL_ACTUALIZAR = "";
    private static final String SQL_ELIMINAR = "DELETE FROM cuentas WHERE id_cuenta =?";
    

    //METODO PARA MOSTRAR
    public ArrayList<Cuentas> Mostrar() throws SQLException{
        this.listaCuentas = new ArrayList<>();
        try( Connection connection = this.conexion.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR);){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
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
        }catch(SQLException e){
//            DesktopNotify.setDefaultTheme(NotifyTheme.Red);
//            DesktopNotify.showDesktopMessage("Error", "Error en sql", DesktopNotify.ERROR, 3000);
            e.printStackTrace();
        }
        return this.listaCuentas;
    }
    
//    //METODO PARA INSERTAR
//    public boolean insertar(Cuentas cuenta) throws SQLException{
//        boolean resultado = false;
//        try (Connection connection = this.conexion.getConexion(); 
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
//            preparedStatement.setString(1, bartender.getNombre());
//            preparedStatement.setInt(2, bartender.getAniosExperiencia());
//            resultado = preparedStatement.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            java.util.logging.Logger.getLogger(BartenderDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } finally {
//            this.conexion.cerrarConexiones();
//        }
//        return resultado;
//    }
}
