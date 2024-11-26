package Vistas;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class VistaCatalogoCuentas extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public VistaCatalogoCuentas() {
        // Establecer el layout general
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(245, 245, 245)); // Fondo suave y claro

        // Título
        JLabel titleLabel = new JLabel("Catálogo de Cuentas", JLabel.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 32));  // Aumentar tamaño de fuente
        titleLabel.setForeground(new Color(50, 50, 50));
        this.add(titleLabel, BorderLayout.NORTH);

        // Panel para el formulario de entrada de cuenta
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout
        formPanel.setBackground(new Color(245, 245, 245)); // Fondo igual al del panel principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaciado entre los componentes

        // Nombre de la cuenta
        JLabel accountNameLabel = new JLabel("Nombre de la cuenta:");
        gbc.gridx = 0; // Columna
        gbc.gridy = 0; // Fila
        formPanel.add(accountNameLabel, gbc);
        
        JTextField accountNameField = new JTextField();
        styleTextField(accountNameField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(accountNameField, gbc);

        // Código de la cuenta
        JLabel accountCodeLabel = new JLabel("Código de la cuenta:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(accountCodeLabel, gbc);
        
        JTextField accountCodeField = new JTextField();
        styleTextField(accountCodeField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(accountCodeField, gbc);

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

        // Nivel (ComboBox)
        JLabel accountLevelLabel = new JLabel("Nivel de cuenta:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(accountLevelLabel, gbc);
        
        String[] levels = {"Principal", "Detalle"};
        JComboBox<String> accountLevelCombo = new JComboBox<>(levels);
        styleComboBox(accountLevelCombo, 250);  // Aumentar el tamaño
        gbc.gridx = 1;
        formPanel.add(accountLevelCombo, gbc);

        // Código para saldos contrarios (campo de texto)
        JLabel contraCodeLabel = new JLabel("Código saldo contrario (R):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(contraCodeLabel, gbc);
        
        JTextField contraCodeField = new JTextField();
        styleTextField(contraCodeField, 250);  // Aumentar el tamaño del campo
        gbc.gridx = 1;
        formPanel.add(contraCodeField, gbc);

        this.add(formPanel, BorderLayout.WEST);

        // Tabla de cuentas (solo ejemplo)
        String[] columnas = {"Código", "Nombre", "Tipo", "Nivel", "Saldo Contrario"};
        tableModel = new DefaultTableModel(columnas, 0);
        table = new JTable(tableModel);
        styleTable(table);  // Aumentar tamaño de celdas de la tabla

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones para agregar y modificar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));  // Aumentar espaciado
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Botón para agregar cuenta
        JButton addButton = new JButton("Agregar Cuenta");
        styleButton(addButton, 200, 50);  // Aumentar tamaño del botón
        addButton.addActionListener(e -> {
            String accountName = accountNameField.getText().trim();
            String accountCode = accountCodeField.getText().trim();
            String accountType = (String) accountTypeCombo.getSelectedItem();
            String accountLevel = (String) accountLevelCombo.getSelectedItem();
            String contraCode = contraCodeField.getText().trim();

            // Validar que no haya campos vacíos
            if (accountName.isEmpty() || accountCode.isEmpty() || contraCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Agregar nueva cuenta a la tabla
                tableModel.addRow(new Object[]{accountCode, accountName, accountType, accountLevel, contraCode});

                // Limpiar los campos después de agregar
                accountNameField.setText("");
                accountCodeField.setText("");
                contraCodeField.setText("");
            }
        });
        buttonPanel.add(addButton);

        // Botón para modificar cuenta
        JButton modifyButton = new JButton("Modificar Cuenta");
        styleButton(modifyButton, 200, 50);  // Aumentar tamaño del botón
        modifyButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Obtener los valores de los campos
                String accountName = accountNameField.getText().trim();
                String accountCode = accountCodeField.getText().trim();
                String accountType = (String) accountTypeCombo.getSelectedItem();
                String accountLevel = (String) accountLevelCombo.getSelectedItem();
                String contraCode = contraCodeField.getText().trim();

                // Validar que no haya campos vacíos
                if (accountName.isEmpty() || accountCode.isEmpty() || contraCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Modificar la cuenta seleccionada
                    table.setValueAt(accountCode, selectedRow, 0);
                    table.setValueAt(accountName, selectedRow, 1);
                    table.setValueAt(accountType, selectedRow, 2);
                    table.setValueAt(accountLevel, selectedRow, 3);
                    table.setValueAt(contraCode, selectedRow, 4);

                    // Limpiar los campos después de modificar
                    accountNameField.setText("");
                    accountCodeField.setText("");
                    contraCodeField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una cuenta para modificar.");
            }
        });
        buttonPanel.add(modifyButton);

        // Botón para eliminar cuenta
        JButton deleteButton = new JButton("Eliminar Cuenta");
        styleButton(deleteButton, 200, 50);  // Aumentar tamaño del botón
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Eliminar la cuenta seleccionada
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una cuenta para eliminar.");
            }
        });
        buttonPanel.add(deleteButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Habilitar el drag-and-drop de celdas
        enableDragAndDrop();
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

    private void enableDragAndDrop() {
        table.addMouseListener(new MouseAdapter() {
            private Point initialClick;

            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int selectedRow = table.rowAtPoint(initialClick);
                if (selectedRow >= 0) {
                    // Implementar lógica de arrastre
                    // Permitir arrastrar la celda
                }
            }
        });
    }
}