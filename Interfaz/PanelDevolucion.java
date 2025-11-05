package Interfaz;

import javax.swing.*;
import java.awt.*;

public class PanelDevolucion extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTextField txtTituloDevolucion;
    private JButton btnDevolver;

    // ******* CONSTRUCTOR *******
    public PanelDevolucion() {
        this.setVistaDevolucion();
    }
    
    // ******* SETTERS *******
    private void setVistaDevolucion(){
        setLayout(new BorderLayout(30, 30));
        setBackground(PaletaColores.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); 
        
        // Título Superior
        JLabel lblTitulo = new JLabel("REGISTRAR DEVOLUCIÓN", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_SECUNDARIO); // Usamos color secundario aquí
        add(lblTitulo, BorderLayout.NORTH);

        // Tarjeta de Formulario (Usamos la lógica original de devoluciones)
        PanelRedondeado tarjeta = crearTarjetaDevolucion();
        add(tarjeta, BorderLayout.CENTER);
    }
    
    // ******* OTROS MÉTODOS *******
    private PanelRedondeado crearTarjetaDevolucion() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, PaletaColores.FONDO_BLANCO, PaletaColores.COLOR_SECUNDARIO, 3
        );
        tarjeta.setLayout(new BorderLayout(20, 20));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblSubtitulo = new JLabel("Título del Libro a Devolver", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSubtitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);

        // Contenedor para el campo
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO: Título del Libro ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel lblTituloDev = new JLabel("Título del Libro:");
        lblTituloDev.setFont(Estilo.FUENTE_ETIQUETA);
        panelFormulario.add(lblTituloDev, gbc);
        
        txtTituloDevolucion = new JTextField(20);
        txtTituloDevolucion.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        panelFormulario.add(txtTituloDevolucion, gbc);

        // --- Botón de Acción ---
        btnDevolver = new JButton("Confirmar Devolución");
        btnDevolver.setFont(Estilo.FUENTE_BOTON);
        btnDevolver.setBackground(PaletaColores.COLOR_SECUNDARIO);
        btnDevolver.setForeground(PaletaColores.TEXTO_CLARO);
        btnDevolver.setFocusPainted(false);
        btnDevolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        tarjeta.add(lblSubtitulo, BorderLayout.NORTH);
        tarjeta.add(panelFormulario, BorderLayout.CENTER);
        tarjeta.add(btnDevolver, BorderLayout.SOUTH);
        
        btnDevolver.addActionListener(e -> registrarDevolucion());

        return tarjeta;
    }

    
    private void registrarDevolucion() {
        JOptionPane.showMessageDialog(this, "Devolución simulada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}