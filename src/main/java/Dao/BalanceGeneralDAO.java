/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Clases.ModeloBalanceG;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BalanceGeneralDAO {

    private Conexion conexion;

    public BalanceGeneralDAO() {
        this.conexion = new Conexion();
    }

    public ArrayList<ModeloBalanceG> cargarBalancePorTipo(String tipo) {
        ArrayList<ModeloBalanceG> listaBalance = new ArrayList<>();
        String sql = "SELECT c.nombre AS nombrecuenta, " +
                     "SUM(CASE WHEN m.cargo IS NULL THEN 0 ELSE m.cargo END) - " +
                     "SUM(CASE WHEN m.abono IS NULL THEN 0 ELSE m.abono END) AS total " +
                     "FROM cuentas c " +
                     "JOIN movimientos m ON c.idcuenta = m.idcuenta " +
                     "WHERE c.tipo = ? " +
                     "GROUP BY c.nombre";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo); // Cambiado a String
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ModeloBalanceG balance = new ModeloBalanceG();
                balance.setNombreCuenta(rs.getString("nombrecuenta"));
                balance.setMonto(rs.getDouble("total"));
                listaBalance.add(balance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaBalance;
    }
}
