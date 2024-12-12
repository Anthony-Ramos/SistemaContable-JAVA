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
import java.time.LocalDate;

import java.util.ArrayList;

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
    private static final String SQL_OBTENER_ULTIMO_NUMERO_PARTIDA = "SELECT MAX(numeropartida) AS maxpartida FROM partida";

    // Método para obtener el último número de partida
    public int obtenerUltimoNumeroPartida() {
        int ultimoNumero = 0;
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(SQL_OBTENER_ULTIMO_NUMERO_PARTIDA)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ultimoNumero = rs.getInt("maxpartida");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimoNumero;
    }

    // Método para insertar movimiento
    public boolean insertar(Movimientos movimiento) {
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(SQL_INSERTAR)) {
            ps.setInt(1, movimiento.getIdcuenta().getIdcuenta());
            ps.setInt(2, movimiento.getIdpartida().getIdpartida());
            ps.setDouble(3, movimiento.getCargo());
            ps.setDouble(4, movimiento.getAbono());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para mostrar movimientos
    public ArrayList<Movimientos> mostrar() {
        ArrayList<Movimientos> listaMovimientos = new ArrayList<>();
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(SQL_MOSTRAR); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movimientos movimiento = new Movimientos();
                Partidas partida = new Partidas();
                Cuentas cuenta = new Cuentas();

                partida.setIdpartida(rs.getInt("idpartida"));
                partida.setFecha(rs.getDate("fecha"));
                cuenta.setNombre(rs.getString("nombre"));

                movimiento.setIdpartida(partida);
                movimiento.setIdcuenta(cuenta);
                movimiento.setCargo(rs.getDouble("cargo"));
                movimiento.setAbono(rs.getDouble("abono"));

                listaMovimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMovimientos;
    }

    // Método para buscar cuenta por código
    public Cuentas buscarCuentaPorCodigo(int codigo) {
        Cuentas cuenta = null;
        String query = "SELECT * FROM cuentas WHERE codigocuenta = ?";
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cuenta = new Cuentas();
                    cuenta.setIdcuenta(rs.getInt("idcuenta"));
                    cuenta.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuenta;
    }

    // Método para buscar una partida por número y fecha
    public Partidas buscarPartidaPorNumeroYFecha(int numeroPartida, Date fecha) {
        Partidas partida = null;
        String query = "SELECT * FROM partida WHERE numeropartida = ? AND fecha = ?";
        try (Connection connection = conexion.getConexion(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, numeroPartida);
            ps.setDate(2, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    partida = new Partidas();
                    partida.setIdpartida(rs.getInt("idpartida"));
                    partida.setFecha(rs.getDate("fecha"));
                    partida.setDescripcion(rs.getString("descripcion"));
                    partida.setNumeroPartida(rs.getInt("numeropartida"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partida;
    }
}
