/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author impor
 */
import Clases.Cuentas;
import Clases.Movimientos;
import Clases.Partidas;
import Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;

import java.sql.*;
import java.util.ArrayList;

public class MovimientosDao {

    private Conexion conexion;

    public MovimientosDao() {
        this.conexion = new Conexion();
    }

    // Consultas SQL
    private static final String SQL_MOSTRAR = "SELECT p.idpartida, p.fecha, c.nombre, m.cargo, m.abono "
            + "FROM movimientos m "
            + "INNER JOIN partida p ON p.idpartida = m.idpartida "
            + "INNER JOIN cuentas c ON c.idcuenta = m.idcuenta";

    private static final String SQL_INSERTAR = "INSERT INTO movimientos (idcuenta, idpartida, cargo, abono) VALUES (?, ?, ?, ?)";

    // Método para mostrar todos los movimientos
    public ArrayList<Movimientos> mostrar() {
        ArrayList<Movimientos> listaMovimientos = new ArrayList<>();
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Movimientos movimiento = new Movimientos();

                // Setear el ID de la partida
                Partidas partida = new Partidas();
                partida.setIdpartida(rs.getInt("idpartida"));
                partida.setFecha(rs.getDate("fecha"));

                // Setear la cuenta
                Cuentas cuenta = new Cuentas();
                cuenta.setNombre(rs.getString("nombre"));

                // Setear cargo y abono
                movimiento.setCargo(rs.getDouble("cargo"));
                movimiento.setAbono(rs.getDouble("abono"));

                // Asociar la partida y la cuenta al movimiento
                movimiento.setIdpartida(partida);
                movimiento.setIdcuenta(cuenta);

                listaMovimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMovimientos;
    }

    // Método para insertar un nuevo movimiento
    public boolean insertar(Movimientos movimiento) {
        boolean resultado = false;
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERTAR)) {
            preparedStatement.setInt(1, movimiento.getIdcuenta().getIdcuenta()); // ID cuenta
            preparedStatement.setInt(2, movimiento.getIdpartida().getIdpartida()); // ID partida
            preparedStatement.setDouble(3, movimiento.getCargo()); // Cargo
            preparedStatement.setDouble(4, movimiento.getAbono()); // Abono

            resultado = preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
}
