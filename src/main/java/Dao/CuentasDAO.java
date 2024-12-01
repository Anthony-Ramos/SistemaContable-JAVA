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
    public ArrayList<Cuentas> Mostrar(){
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
    public boolean insertar(Cuentas cuenta){
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); 
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
            preparedStatement.setString(1, cuenta.getCodigo());
            preparedStatement.setString(2, cuenta.getNombre());
            preparedStatement.setString(3, cuenta.getTipo());
            preparedStatement.setDouble(4, cuenta.getSaldo_inicial());
            preparedStatement.setDouble(5, cuenta.getSaldo_actual());
            preparedStatement.setDate(6,new java.sql.Date(cuenta.getCreado_en().getTime()));
            preparedStatement.setBoolean(7, cuenta.isSaldo_contrario());
            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CuentasDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            this.conexion.cerrarConexiones();
        }
        return resultado;
    }
}
