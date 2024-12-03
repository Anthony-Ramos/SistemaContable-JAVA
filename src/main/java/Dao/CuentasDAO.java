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
    private static final String SQL_INSERTAR = "INSERT INTO cuentas (codigocuenta, nombre, descripcion, tipo) VALUES (?, ?, ?, ?);";
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
}
