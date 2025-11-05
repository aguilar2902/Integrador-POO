package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import Controlador.GestionBiblioteca;
import Biblioteca.Socio;
import Biblioteca.Libro;


public class PanelPrestamo extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTextField txtDniPrestamo, txtTituloPrestamo;
    private JButton btnPrestar;
    private GestionBiblioteca controlador;

    // ******* CONSTRUCTOR *******
    public PanelPrestamo(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.setVistaPrestamo();
    }
    
    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
         this.controlador = p_controlador;
    }
        
    private void setVistaPrestamo(){
        // Inicialización y Layout del Panel
        setLayout(new BorderLayout(30, 30));
        setBackground(PaletaColores.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200)); 
            
        // Título Superior
        JLabel lblTitulo = new JLabel("REGISTRAR NUEVO PRÉSTAMO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        add(lblTitulo, BorderLayout.NORTH);
    
        // Tarjeta de Formulario (Usamos la lógica original de préstamos)
        PanelRedondeado tarjeta = crearTarjetaPrestamo();
        add(tarjeta, BorderLayout.CENTER);
    }
    
    // ******* OTROS MÉTODOS *******
    private PanelRedondeado crearTarjetaPrestamo() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, PaletaColores.FONDO_BLANCO, PaletaColores.COLOR_PRIMARIO, 3
        );
        tarjeta.setLayout(new BorderLayout(20, 20));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblSubtitulo = new JLabel("Datos del Socio y Libro", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSubtitulo.setForeground(PaletaColores.COLOR_PRIMARIO);

        // Contenedor para los campos (GridBagLayout para alineación)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO 1: DNI del Socio ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel lblDniSocio = new JLabel("DNI del Socio:");
        lblDniSocio.setFont(Estilo.FUENTE_ETIQUETA);
        panelFormulario.add(lblDniSocio, gbc);
        
        txtDniPrestamo = new JTextField(20);
        txtDniPrestamo.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        panelFormulario.add(txtDniPrestamo, gbc);

        // --- CAMPO 2: Título del Libro ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2;
        JLabel lblTitulo = new JLabel("Título del Libro:");
        lblTitulo.setFont(Estilo.FUENTE_ETIQUETA);
        panelFormulario.add(lblTitulo, gbc);
        
        txtTituloPrestamo = new JTextField(20);
        txtTituloPrestamo.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.8;
        panelFormulario.add(txtTituloPrestamo, gbc);
        
        // --- Botón de Acción ---
        btnPrestar = new JButton("Confirmar Préstamo");
        btnPrestar.setFont(Estilo.FUENTE_BOTON);
        btnPrestar.setBackground(PaletaColores.COLOR_PRIMARIO);
        btnPrestar.setForeground(PaletaColores.TEXTO_CLARO);
        btnPrestar.setFocusPainted(false);
        btnPrestar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        tarjeta.add(lblSubtitulo, BorderLayout.NORTH);
        tarjeta.add(panelFormulario, BorderLayout.CENTER);
        tarjeta.add(btnPrestar, BorderLayout.SOUTH);
        
        btnPrestar.addActionListener(e -> registrarPrestamo());
        
        return tarjeta;
    }
    
    
    private void registrarPrestamo() {
        String dniStr = txtDniPrestamo.getText().trim();
        String titulo = txtTituloPrestamo.getText().trim();
            
            // 0. Validación de campos vacíos en la interfaz
        if (dniStr.isEmpty() || titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar tanto el DNI del Socio como el Título del Libro.", 
                "Datos Faltantes", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 1. Parseo y validación de DNI
            int dni = Integer.parseInt(txtDniPrestamo.getText());
            
            // 2. Búsqueda y Validación del Socio
            Socio socio = controlador.buscarSocioPorDni(dni);
            if(socio == null){
                throw new Exception("El DNI **" + dniStr + "** no corresponde a un socio registrado.");
            }
            
            // 3. Búsqueda y Validación del Libro
            Libro libro = controlador.buscarLibroPorTitulo(titulo);
            if (libro == null) {
                throw new Exception("El libro con título **'" + titulo + "'** no se encuentra en el inventario.");
            }
            // Si todo va bien
            JOptionPane.showMessageDialog(this, 
                "Préstamo del libro '" + txtTituloPrestamo.getText() + "' registrado con éxito.", 
                "Operación Exitosa", // Título profesional
                JOptionPane.INFORMATION_MESSAGE); // Icono de Información (el check azul)
        
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI, Edición y Año deben ser números válidos.", 
                "Datos Inválidos", // Título claro
                JOptionPane.ERROR_MESSAGE); // Icono de Error (X roja)
        } catch (Exception e) {
            // Manejo de tu LibroNoPrestadoException o cualquier otra lógica de negocio
            JOptionPane.showMessageDialog(this, 
                "Error en la lógica de negocio: " + e.getMessage(), 
                "Error de Procesamiento", // Título de Error
                JOptionPane.ERROR_MESSAGE);
        }
    }
}