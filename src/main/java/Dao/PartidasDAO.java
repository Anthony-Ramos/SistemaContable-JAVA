/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Clases.Partidas;
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
public class PartidasDAO {
    private Conexion conexion;
    private ArrayList<Partidas> listaPartidas;

    public PartidasDAO() {
        this.conexion = new Conexion();
    }
    
    //Consultas SQL
    private static final String SQL_MOSTRAR = "SELECT * FROM partida";
    private static final String SQL_INSERTAR = "INSERT INTO partida (fecha, descripcion, numeropartida) VALUES (?, ?, ?);";
    
    
    //METODO PARA MOSTRAR
    public ArrayList<Partidas> Mostrar(){
        this.listaPartidas = new ArrayList<>();
        try( Connection connection = this.conexion.getConexion();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR);){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Partidas partida = new Partidas();
                partida.setFecha(rs.getDate("fecha"));
                partida.setDescripcion(rs.getString("descripcion"));
                partida.setNumeropartida(rs.getInt("numeropartida"));
                this.listaPartidas.add(partida);
            }
        }catch(SQLException e){
//            DesktopNotify.setDefaultTheme(NotifyTheme.Red);
//            DesktopNotify.showDesktopMessage("Error", "Error en sql", DesktopNotify.ERROR, 3000);
            e.printStackTrace();
        }
        return this.listaPartidas;
    }
    
//    //METODO PARA INSERTAR
    public boolean insertar(Partidas partida){
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); 
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
            preparedStatement.setDate(1, new java.sql.Date(partida.getFecha().getTime()));
            preparedStatement.setString(2, partida.getDescripcion());
            preparedStatement.setInt(3, partida.getNumeropartida());
            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CuentasDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            this.conexion.cerrarConexiones();
        }
        return resultado;
    }
}
