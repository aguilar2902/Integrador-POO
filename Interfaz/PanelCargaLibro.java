package interfaz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controlador.*;

public class PanelCargaLibro extends JPanel {
    
    // ******* Atributos *******
    private JTextField txtTitulo, txtAutor;
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
        setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150)); 
        
        // --- Panel de Título con subtítulo ---
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO LIBRO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        
        JLabel lblSubtitulo = new JLabel("Complete la información del libro a registrar", JLabel.CENTER);
        lblSubtitulo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(120, 120, 120));
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
        
        add(panelTitulo, BorderLayout.NORTH);

        // --- Área Central: Tarjeta de Formulario ---
        PanelRedondeado tarjeta = crearTarjetaFormulario();
        add(tarjeta, BorderLayout.CENTER);
    }

    // ******* Otros metodos *******
    private PanelRedondeado crearTarjetaFormulario() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, 
            PaletaColores.COLOR_SECUNDARIO,
            3
        );
        tarjeta.setLayout(new BorderLayout(30, 30));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // 1. Panel de Campos (CENTRO)
        JPanel panelCampos = crearPanelCampos();
        tarjeta.add(panelCampos, BorderLayout.CENTER);

        // 2. Panel de Botón (SUR)
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelBoton.setOpaque(false);
        
        btnGuardar = crearBotonEstilizado("Registrar Libro");
        btnGuardar.addActionListener(e -> registrarLibro());
        panelBoton.add(btnGuardar);
        
        tarjeta.add(panelBoton, BorderLayout.SOUTH);

        return tarjeta;
    }

    // ******* PANEL DE CAMPOS (GridBagLayout) *******
    private JPanel crearPanelCampos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- FILA 1: TÍTULO ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblTitulo = crearEtiquetaEstilizada("Título del Libro:");
        panel.add(lblTitulo, gbc);
        
        txtTitulo = crearCampoTextoEstilizado(25);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtTitulo, gbc);

        // --- FILA 2: AUTOR ---
        /*
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        JLabel lblAutor = crearEtiquetaEstilizada("Autor:");
        panel.add(lblAutor, gbc);
        
        txtAutor = crearCampoTextoEstilizado(25);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.75;
        panel.add(txtAutor, gbc);*/

        // --- FILA 3: EDICIÓN ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        JLabel lblEdicion = crearEtiquetaEstilizada("Edición:");
        panel.add(lblEdicion, gbc);
        
        txtEdicion = crearCampoTextoEstilizado(25);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.75;
        panel.add(txtEdicion, gbc);
        
        // --- FILA 4: EDITORIAL ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.25;
        JLabel lblEditorial = crearEtiquetaEstilizada("Editorial:");
        panel.add(lblEditorial, gbc);
        
        txtEditorial = crearCampoTextoEstilizado(25);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.75;
        panel.add(txtEditorial, gbc);

        // --- FILA 5: AÑO ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.25;
        JLabel lblAnio = crearEtiquetaEstilizada("Año de Publicación:");
        panel.add(lblAnio, gbc);
        
        txtAnio = crearCampoTextoEstilizado(25);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.75;
        panel.add(txtAnio, gbc);

        return panel;
    }
    
    // ===============================================
    //        MÉTODOS DE ESTILIZACIÓN
    // ===============================================
    
    private JLabel crearEtiquetaEstilizada(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    private JTextField crearCampoTextoEstilizado(int columnas) {
        JTextField campo = new JTextField(columnas);
        campo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(campo.getPreferredSize().width, 40));
        
        // Borde redondeado con padding interno
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Efecto hover/focus
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(PaletaColores.COLOR_PRIMARIO, 2, true),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1, true),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
                ));
            }
        });
        
        return campo;
    }
    
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font(Estilo.FUENTE_BOTON.getFamily(), Font.BOLD, 15));
        boton.setPreferredSize(new Dimension(250, 45));
        boton.setBackground(PaletaColores.COLOR_PRIMARIO);
        boton.setForeground(PaletaColores.TEXTO_CLARO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Bordes redondeados
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PaletaColores.COLOR_PRIMARIO, 0, true),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        
        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(PaletaColores.COLOR_PRIMARIO.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(PaletaColores.COLOR_PRIMARIO);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(PaletaColores.COLOR_PRIMARIO.darker().darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(PaletaColores.COLOR_PRIMARIO.darker());
            }
        });
        
        return boton;
    }

    // ******* LÓGICA DE REGISTRO *******
    private void registrarLibro() {
        String titulo = txtTitulo.getText().trim();
        String edicionStr = txtEdicion.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String anioStr = txtAnio.getText().trim();
        
        if (titulo.isEmpty() || edicionStr.isEmpty() || 
            editorial.isEmpty() || anioStr.isEmpty()) {
             mostrarMensajeError("Todos los campos son obligatorios.");
             return;
        }

        try {
            int edicion = Integer.parseInt(edicionStr);
            int anio = Integer.parseInt(anioStr);
            
            // Validación de año razonable
            int anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (anio < 1000 || anio > anioActual) {
                mostrarMensajeError("El año de publicación debe estar entre 1000 y " + anioActual + ".");
                return;
            }
            
            // Validación de edición positiva
            if (edicion <= 0) {
                mostrarMensajeError("La edición debe ser un número positivo.");
                return;
            }
            
            this.controlador.nuevoLibro(titulo, edicion, editorial, anio);
            
            mostrarMensajeExito("Libro '" + titulo + "' registrado exitosamente.");
            
            // Limpiar campos después del éxito
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarMensajeError("Edición y Año deben ser números válidos.");
        } catch (Exception e) {
            mostrarMensajeError("Error de registro: " + e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtEdicion.setText("");
        txtEditorial.setText("");
        txtAnio.setText("");
        txtTitulo.requestFocus(); // Foco en el primer campo
    }
    
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(
            this, 
            mensaje, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(
            this, 
            mensaje, 
            "Registro Exitoso", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}