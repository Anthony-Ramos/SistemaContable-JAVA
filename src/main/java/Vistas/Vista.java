package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vista extends javax.swing.JFrame {

    private JButton selectedButton = null; // Para mantener un botón seleccionado
    private JPanel cards;
    private CardLayout cardLayout;

    public Vista() {
        initComponents();
        ajustarVentana();
    }

    private void ajustarVentana() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension pantalla = toolkit.getScreenSize();
        this.setSize(pantalla.width, pantalla.height);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        fondo_menu = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setLayout(new java.awt.BorderLayout());

        fondo_menu.setBackground(new java.awt.Color(51, 153, 255));
        fondo_menu.setLayout(new BoxLayout(fondo_menu, BoxLayout.Y_AXIS));

        // Crear botones de menú
        JButton btn1 = createMenuButton("Catálogo de Cuentas");
        JButton btn2 = createMenuButton("Libro Diario");
        JButton btn3 = createMenuButton("Estado de Resultados");

        btn1.addActionListener(e -> showCard("Catálogo de Cuentas", btn1));
        btn2.addActionListener(e -> showCard("Libro Diario", btn2));
        btn3.addActionListener(e -> showCard("Estado de Resultados", btn3));

        fondo_menu.add(btn1);
        fondo_menu.add(btn2);
        fondo_menu.add(btn3);

        // Crear el panel de contenido principal con CardLayout
        cards = new JPanel();
        cardLayout = new CardLayout();
        cards.setLayout(cardLayout);

        // Agregar paneles
        JPanel panelCatalogo = new VistaCatalogoCuentas();
        JPanel panelLibroDiario = new JPanel();
        panelLibroDiario.setBackground(Color.WHITE);
        panelLibroDiario.add(new JLabel("Contenido de Libro Diario"));

        JPanel panelEstadoResultados = new JPanel();
        panelEstadoResultados.setBackground(Color.WHITE);
        panelEstadoResultados.add(new JLabel("Contenido de Estado de Resultados"));

        // Añadir paneles al CardLayout
        cards.add(panelCatalogo, "Catálogo de Cuentas");
        cards.add(panelLibroDiario, "Libro Diario");
        cards.add(panelEstadoResultados, "Estado de Resultados");

        JPanel contenido = new JPanel();
        contenido.setBackground(new java.awt.Color(255, 255, 255));
        contenido.add(cards);

        Fondo.add(fondo_menu, java.awt.BorderLayout.WEST);
        Fondo.add(contenido, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    // Método para crear botones de menú con tamaño, texto justificado y selección
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new java.awt.Color(51, 153, 255));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Aseguramos que el botón ocupe todo el espacio disponible en su contenedor
        button.setPreferredSize(new Dimension(250, 50)); // Ajusta el tamaño del botón
        button.setMaximumSize(new Dimension(250, 50)); // No permite que el botón crezca más
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinea el texto al centro

        // Alineación del texto al centro
        button.setHorizontalAlignment(SwingConstants.CENTER); // Justifica el texto horizontalmente
        button.setVerticalAlignment(SwingConstants.CENTER); // Justifica el texto verticalmente

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Cambiar el cursor a mano al pasar el mouse sobre el botón
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto de cambio de color al pasar el mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new java.awt.Color(0, 102, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Cambiar al color de fondo predeterminado si no está seleccionado
                if (button != selectedButton) {
                    button.setBackground(new java.awt.Color(51, 153, 255));
                }
            }
        });

        return button;
    }

    // Método para cambiar de tarjeta y manejar la selección de los botones
    private void showCard(String cardName, JButton selectedBtn) {
        // Cambiar el color de fondo del botón seleccionado
        if (selectedButton != null) {
            selectedButton.setBackground(new java.awt.Color(51, 153, 255)); // Color predeterminado
        }
        
        // Establecer el nuevo botón seleccionado
        selectedButton = selectedBtn;
        selectedButton.setBackground(new java.awt.Color(0, 102, 204)); // Color de selección

        // Mostrar la tarjeta correspondiente
        cardLayout.show(cards, cardName);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Vista().setVisible(true));
    }

    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel fondo_menu;
}