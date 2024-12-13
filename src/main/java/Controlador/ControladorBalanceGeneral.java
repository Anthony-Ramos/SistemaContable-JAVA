/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Clases.ModeloBalanceG;
import Dao.BalanceGeneralDAO;
import Pantallas.BalanceGeneral;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControladorBalanceGeneral {

    private BalanceGeneral frmBalanceGeneral;
    private BalanceGeneralDAO dao;

    private DefaultTableModel modeloActivos;
    private DefaultTableModel modeloPasivosPatrimonio;

    public ControladorBalanceGeneral() {
        this.frmBalanceGeneral = new BalanceGeneral();
        this.dao = new BalanceGeneralDAO();

        this.modeloActivos = (DefaultTableModel) this.frmBalanceGeneral.jTable1.getModel();
        this.modeloPasivosPatrimonio = (DefaultTableModel) this.frmBalanceGeneral.jTable2.getModel();

        cargarDatos();
        this.frmBalanceGeneral.setVisible(true);
    }

    private void cargarDatos() {
        cargarActivos();
        cargarPasivosYPatrimonio();
    }

    private void cargarActivos() {
        modeloActivos.setRowCount(0); // Limpiar tabla antes de cargar
        agregarDatosATabla(dao.cargarBalancePorTipo("Activo"), "Activo Corriente", modeloActivos);
        agregarDatosATabla(dao.cargarBalancePorTipo("Activo No Corriente"), "Activo No Corriente", modeloActivos);
    }

    private void cargarPasivosYPatrimonio() {
        modeloPasivosPatrimonio.setRowCount(0); // Limpiar tabla antes de cargar
        agregarDatosATabla(dao.cargarBalancePorTipo("Pasivo"), "Pasivo Corriente", modeloPasivosPatrimonio);
        agregarDatosATabla(dao.cargarBalancePorTipo("Pasivo No Corriente"), "Pasivo No Corriente", modeloPasivosPatrimonio);
        agregarDatosATabla(dao.cargarBalancePorTipo("Patrimonio"), "Patrimonio", modeloPasivosPatrimonio);
    }

    private void agregarDatosATabla(ArrayList<ModeloBalanceG> lista, String titulo, DefaultTableModel modelo) {
        modelo.addRow(new Object[]{titulo, "", ""}); // Agrega el encabezado
        double totalPorGrupo = 0;

        for (ModeloBalanceG item : lista) {
            modelo.addRow(new Object[]{item.getNombreCuenta(), item.getMonto(), ""});
            totalPorGrupo += item.getMonto();
        }

        // Agregar total al final del grupo
        modelo.addRow(new Object[]{"TOTAL " + titulo, "", totalPorGrupo});
    }

    public static void main(String[] args) {
        new ControladorBalanceGeneral();
    }
}
