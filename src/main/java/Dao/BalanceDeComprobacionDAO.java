/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Clases.BalanceComprobacion;
import Clases.BalanceTotales;
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
public class BalanceDeComprobacionDAO {
    private Conexion conexion;
    private ArrayList<BalanceComprobacion> listaBalance;

    public BalanceDeComprobacionDAO() {
        this.conexion = new Conexion();
    }

    private static final String SQL_MOSTRAR = "SELECT \n"
            + "    c.nombre AS nombre_cuenta,\n"
            + "    COALESCE(SUM(m.cargo), 0) AS debe,\n"
            + "    COALESCE(SUM(m.abono), 0) AS haber,\n"
            + "    CASE \n"
            + "        WHEN COALESCE(SUM(m.cargo), 0) - COALESCE(SUM(m.abono), 0) > 0 THEN COALESCE(SUM(m.cargo), 0) - COALESCE(SUM(m.abono), 0)\n"
            + "        ELSE 0\n"
            + "    END AS saldo_deudor,\n"
            + "    CASE \n"
            + "        WHEN COALESCE(SUM(m.cargo), 0) - COALESCE(SUM(m.abono), 0) < 0 THEN ABS(COALESCE(SUM(m.cargo), 0) - COALESCE(SUM(m.abono), 0))\n"
            + "        ELSE 0\n"
            + "    END AS saldo_acreedor\n"
            + "FROM \n"
            + "    public.cuentas c\n"
            + "LEFT JOIN \n"
            + "    public.movimientos m ON c.idcuenta = m.idcuenta\n"
            + "GROUP BY \n"
            + "    c.nombre\n"
            + "ORDER BY \n"
            + "    c.nombre;";
    
    private static final String SQL_TOTALES = "SELECT \n" +
"    COALESCE(SUM(m.cargo), 0) AS total_debe,\n" +
"    COALESCE(SUM(m.abono), 0) AS total_haber,\n" +
"    COALESCE(SUM(CASE \n" +
"        WHEN m.cargo - m.abono > 0 THEN m.cargo - m.abono\n" +
"        ELSE 0\n" +
"    END), 0) AS total_saldo_deudor,\n" +
"    COALESCE(SUM(CASE \n" +
"        WHEN m.cargo - m.abono < 0 THEN ABS(m.cargo - m.abono)\n" +
"        ELSE 0\n" +
"    END), 0) AS total_saldo_acreedor\n" +
"FROM \n" +
"    public.movimientos m;";

    public ArrayList<BalanceComprobacion> mostrar() {
        this.listaBalance = new ArrayList<>();
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_MOSTRAR)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                BalanceComprobacion balance = new BalanceComprobacion();
                balance.setNombreCuenta(rs.getString("nombre_cuenta"));
                balance.setDebe(rs.getDouble("debe"));
                balance.setHaber(rs.getDouble("haber"));
                balance.setSaldoDeudor(rs.getDouble("saldo_deudor"));
                balance.setSaldoAcreedor(rs.getDouble("saldo_acreedor"));

                listaBalance.add(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaBalance;
    }

    public BalanceTotales mostrarTotales() {
        BalanceTotales totales = new BalanceTotales();
        try (Connection connection = this.conexion.getConexion(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_TOTALES)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                totales.setTotalDebe(rs.getDouble("total_debe"));
                totales.setTotalHaber(rs.getDouble("total_haber"));
                totales.setTotalSaldoDeudor(rs.getDouble("total_saldo_deudor"));
                totales.setTotalSaldoAcreedor(rs.getDouble("total_saldo_acreedor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totales;
    }
}
