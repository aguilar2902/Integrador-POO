package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controlador.GestionBiblioteca;

public class PanelCargaLibro extends JPanel {
    
    // ******* Atributos *******
    private JTextField txtTitulo;
    private JTextField txtEdicion;
    private JTextField txtEditorial;
    private JTextField txtAnio;
    private JButton btnGuardar;
    private GestionBiblioteca controlador;

    // ******* Constructor *******
    public PanelCargaLibro(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.setVistaCargaLibro();
    }
    
    // ******* setter *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    private void setVistaCargaLibro(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); 
        setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); 
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO LIBRO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Área Central: Tarjeta de Formulario ---
        PanelRedondeado tarjeta = crearTarjetaFormulario();
        add(tarjeta, BorderLayout.CENTER);
    }

    // ******* Otros metodos *******
    private PanelRedondeado crearTarjetaFormulario() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, 
            PaletaColores.COLOR_PRIMARIO,
            3
        );
        tarjeta.setLayout(new BorderLayout(30, 30));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // 1. Subtítulo
        JLabel lblSubtitulo = new JLabel("Ingresar Datos del Libro", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblSubtitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);
        tarjeta.add(lblSubtitulo, BorderLayout.NORTH);


        // 2. Panel de Campos (CENTRO)
        JPanel panelCampos = crearPanelCampos();
        tarjeta.add(panelCampos, BorderLayout.CENTER);

        // 3. Botón Guardar (SUR)
        btnGuardar = new JButton("Guardar Libro");
        btnGuardar.setFont(Estilo.FUENTE_BOTON);
        btnGuardar.setBackground(PaletaColores.COLOR_PRIMARIO);
        btnGuardar.setForeground(PaletaColores.TEXTO_CLARO);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> registrarLibro());
        tarjeta.add(btnGuardar, BorderLayout.SOUTH);

        return tarjeta;
    }

    // ******* PANEL DE CAMPOS (GridBagLayout) *******
    private JPanel crearPanelCampos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10); // Más espacio vertical
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- FILA 1: TÍTULO ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblTitulo, gbc);
        
        txtTitulo = new JTextField(25);
        txtTitulo.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtTitulo, gbc);

        // --- FILA 2: EDICIÓN ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        JLabel lblEdicion = new JLabel("Edición:");
        lblEdicion.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblEdicion, gbc);
        
        txtEdicion = new JTextField(25);
        txtEdicion.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.75;
        panel.add(txtEdicion, gbc);
        
        // --- FILA 3: EDITORIAL ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        JLabel lblEditorial = new JLabel("Editorial:");
        lblEditorial.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblEditorial, gbc);
        
        txtEditorial = new JTextField(25);
        txtEditorial.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.75;
        panel.add(txtEditorial, gbc);

        // --- FILA 4: AÑO ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.25;
        JLabel lblAnio = new JLabel("Año de Publicación:");
        lblAnio.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblAnio, gbc);
        
        txtAnio = new JTextField(25);
        txtAnio.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.75;
        panel.add(txtAnio, gbc);

        return panel;
    }
    

    // ******* LÓGICA DE REGISTRO *******
    private void registrarLibro() {
        String titulo = txtTitulo.getText();
        String edicionStr = txtEdicion.getText();
        String editorial = txtEditorial.getText();
        String anioStr = txtAnio.getText();
        
        if (titulo.isEmpty() || edicionStr.isEmpty() || editorial.isEmpty() || anioStr.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
             return;
        }

        try {
            int edicion = Integer.parseInt(edicionStr);
            int anio = Integer.parseInt(anioStr);
            
            this.controlador.nuevoLibro(titulo, edicion, editorial, anio);
            
            JOptionPane.showMessageDialog(this, "Libro '" + titulo + "' registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar campos después del éxito
            txtTitulo.setText("");
            txtEdicion.setText("");
            txtEditorial.setText("");
            txtAnio.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edición y Año deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error de registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}