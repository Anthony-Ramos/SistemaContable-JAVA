package Vistas;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class VistaLibroDiario extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public VistaLibroDiario() {
        // Establecer el layout general
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245)); // Fondo suave y claro

        // Título
        JLabel titleLabel = new JLabel("Libro Diario", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 32));  // Aumentar tamaño de fuente
        titleLabel.setForeground(new Color(50, 50, 50));
        this.add(titleLabel, BorderLayout.NORTH);

        // Panel para el formulario de entrada
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout
        formPanel.setBackground(new Color(245, 245, 245)); // Fondo igual al del panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaciado entre los componentes

        // Fecha de la transacción
        JLabel dateLabel = new JLabel("Fecha de la transacción:");
        gbc.gridx = 0; // Columna
        gbc.gridy = 0; // Fila
        formPanel.add(dateLabel, gbc);
        
        JTextField dateField = new JTextField();
        styleTextField(dateField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        // Descripción de la transacción
        JLabel descriptionLabel = new JLabel("Descripción:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(descriptionLabel, gbc);
        
        JTextField descriptionField = new JTextField();
        styleTextField(descriptionField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);

        // Tipo de cuenta (ComboBox)
        JLabel accountTypeLabel = new JLabel("Tipo de cuenta:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(accountTypeLabel, gbc);
        
        String[] types = {"Activo", "Pasivo", "Capital", "Ingreso", "Gasto"};
        JComboBox<String> accountTypeCombo = new JComboBox<>(types);
        styleComboBox(accountTypeCombo, 250);  // Aumentar el tamaño
        gbc.gridx = 1;
        formPanel.add(accountTypeCombo, gbc);

        // Monto (campo numérico)
        JLabel amountLabel = new JLabel("Monto:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(amountLabel, gbc);
        
        JTextField amountField = new JTextField();
        styleTextField(amountField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);

        this.add(formPanel, BorderLayout.WEST);

        // Tabla de libro diario (solo ejemplo)
        String[] columnas = {"Fecha", "Descripción", "Tipo de Cuenta", "Monto"};
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel);
        styleTable(table);  // Aumentar tamaño de celdas de la tabla

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones para agregar y modificar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));  // Aumentar espaciado
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Botón para agregar registro
        JButton addButton = new JButton("Agregar Registro");
        styleButton(addButton, 200, 50);  // Aumentar tamaño del botón
        addButton.addActionListener(e -> {
            String date = dateField.getText().trim();
            String description = descriptionField.getText().trim();
            String accountType = (String) accountTypeCombo.getSelectedItem();
            String amount = amountField.getText().trim();

            // Validar que no haya campos vacíos
            if (date.isEmpty() || description.isEmpty() || amount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Agregar nuevo registro a la tabla
                tableModel.addRow(new Object[]{date, description, accountType, amount});

                // Limpiar los campos después de agregar
                dateField.setText("");
                descriptionField.setText("");
                amountField.setText("");
            }
        });
        buttonPanel.add(addButton);

        // Botón para modificar registro
        JButton modifyButton = new JButton("Modificar Registro");
        styleButton(modifyButton, 200, 50);  // Aumentar tamaño del botón
        modifyButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Obtener los valores de los campos
                String date = dateField.getText().trim();
                String description = descriptionField.getText().trim();
                String accountType = (String) accountTypeCombo.getSelectedItem();
                String amount = amountField.getText().trim();

                // Validar que no haya campos vacíos
                if (date.isEmpty() || description.isEmpty() || amount.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Modificar el registro seleccionado
                    table.setValueAt(date, selectedRow, 0);
                    table.setValueAt(description, selectedRow, 1);
                    table.setValueAt(accountType, selectedRow, 2);
                    table.setValueAt(amount, selectedRow, 3);

                    // Limpiar los campos después de modificar
                    dateField.setText("");
                    descriptionField.setText("");
                    amountField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro para modificar.");
            }
        });
        buttonPanel.add(modifyButton);

        // Botón para eliminar registro
        JButton deleteButton = new JButton("Eliminar Registro");
        styleButton(deleteButton, 200, 50);  // Aumentar tamaño del botón
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Eliminar el registro seleccionado
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
            }
        });
        buttonPanel.add(deleteButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos auxiliares para aplicar estilos
    private void styleTextField(JTextField field, int width) {
        field.setFont(new Font("Roboto", Font.PLAIN, 18));  // Aumentar tamaño de fuente
        field.setBackground(new Color(255, 255, 255));
        field.setForeground(new Color(50, 50, 50));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));  // Borde más grueso
        field.setPreferredSize(new Dimension(width, 50));  // Aumentar tamaño del campo
        field.setMargin(new Insets(0, 10, 0, 10));
    }

    private void styleComboBox(JComboBox<String> comboBox, int width) {
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 18));  // Aumentar tamaño de fuente
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setForeground(new Color(50, 50, 50));
        comboBox.setPreferredSize(new Dimension(width, 50));  // Aumentar tamaño del combo
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Roboto", Font.PLAIN, 18));  // Aumentar tamaño de fuente
        table.setRowHeight(40);  // Aumentar altura de las filas
        table.setSelectionBackground(new Color(100, 150, 255));  // Color de fondo para la fila seleccionada
        table.setSelectionForeground(Color.WHITE);  // Color del texto en la fila seleccionada
        table.setDefaultEditor(Object.class, null);  // Deshabilitar edición de celdas directamente
    }

    private void styleButton(JButton button, int width, int height) {
        button.setFont(new Font("Roboto", Font.PLAIN, 18));  // Aumentar tamaño de fuente
        button.setBackground(new Color(34, 167, 240));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));  // Aumentar tamaño del botón
    }
}