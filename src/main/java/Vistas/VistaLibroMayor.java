/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaLibroMayor extends JPanel {

    public JTextField txtCodigoCuenta;
    public JTextField txtNombreCuenta;
    public JTable tableLibroMayor;
    public JButton btnBuscar;
    private DefaultTableModel tableModel;

    public VistaLibroMayor() {
        // Layout principal
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245));

        // Título de la vista
        JLabel titleLabel = new JLabel("Libro Mayor", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 50));
        this.add(titleLabel, BorderLayout.NORTH);

        // Panel de búsqueda (Formulario)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Campo "Código de cuenta"
        JLabel lblCodigoCuenta = new JLabel("Código de cuenta:");
        lblCodigoCuenta.setFont(new Font("Roboto", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(lblCodigoCuenta, gbc);

        txtCodigoCuenta = new JTextField();
        styleTextField(txtCodigoCuenta, 200);
        gbc.gridx = 1;
        searchPanel.add(txtCodigoCuenta, gbc);

        // Campo "Nombre de cuenta"
        JLabel lblNombreCuenta = new JLabel("Nombre de cuenta:");
        lblNombreCuenta.setFont(new Font("Roboto", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(lblNombreCuenta, gbc);

        txtNombreCuenta = new JTextField();
        txtNombreCuenta.setEditable(false); // Solo lectura
        styleTextField(txtNombreCuenta, 200);
        gbc.gridx = 1;
        searchPanel.add(txtNombreCuenta, gbc);

        // Botón "Buscar"
        btnBuscar = new JButton("Buscar");
        styleButton(btnBuscar, 150, 40);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2; // Ocupa dos filas
        searchPanel.add(btnBuscar, gbc);

        this.add(searchPanel, BorderLayout.WEST);

        // Tabla de datos
        String[] columnNames = {"Fecha", "Descripción", "REF", "Debe", "Haber", "Saldo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableLibroMayor = new JTable(tableModel);
        styleTable(tableLibroMayor);

        JScrollPane scrollPane = new JScrollPane(tableLibroMayor);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Método auxiliar para estilizar JTextField
    private void styleTextField(JTextField field, int width) {
        field.setFont(new Font("Roboto", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(width, 40));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
    }

    // Método auxiliar para estilizar JButton
    private void styleButton(JButton button, int width, int height) {
        button.setFont(new Font("Roboto", Font.PLAIN, 18));
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
    }

    // Método auxiliar para estilizar JTable
    private void styleTable(JTable table) {
        table.setFont(new Font("Roboto", Font.PLAIN, 18));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(100, 150, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(100, 150, 255));
        table.setSelectionForeground(Color.WHITE);
        table.setDefaultEditor(Object.class, null); // Deshabilitar edición directa
    }

    public static void main(String[] args) {
        // Probar la vista
        JFrame frame = new JFrame("Libro Mayor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(new VistaLibroMayor());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
