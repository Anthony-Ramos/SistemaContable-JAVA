package Dao;

import Clases.Movimientos;
import Clases.Partidas;
import Conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class LibroMayorDAO {

    private Conexion conexion;

    public LibroMayorDAO() {
        this.conexion = new Conexion();
    }

    // Consulta SQL para obtener los movimientos de una cuenta específica
    private static final String SQL_OBTENER_MOVIMIENTOS = "SELECT p.fecha, p.descripcion, p.numeropartida, m.cargo, m.abono " +
            "FROM movimientos m " +
            "INNER JOIN cuentas c ON m.idcuenta = c.idcuenta " +
            "INNER JOIN partida p ON m.idpartida = p.idpartida " +
            "WHERE c.codigocuenta = ? " +
            "ORDER BY p.fecha";

    public ArrayList<Movimientos> obtenerMovimientosPorCodigoCuenta(int codigoCuenta) {
        ArrayList<Movimientos> listaMovimientos = new ArrayList<>();
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_OBTENER_MOVIMIENTOS)) {

            ps.setInt(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movimientos movimiento = new Movimientos();

                // Crear y setear la partida
                Partidas partida = new Partidas();
                partida.setFecha(rs.getDate("fecha"));
                partida.setDescripcion(rs.getString("descripcion"));
                partida.setNumeropartida(rs.getInt("numeropartida"));

                movimiento.setIdpartida(partida);
                movimiento.setCargo(rs.getDouble("cargo"));
                movimiento.setAbono(rs.getDouble("abono"));

                listaMovimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaMovimientos;
    }
}
