package interfaz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import controlador.*;
import biblioteca.*;

public class PanelPrestamo extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTextField txtDniPrestamo, txtTituloPrestamo;
    private JSpinner spinnerFecha;
    private JButton btnBuscar, btnPrestar;
    private JTextArea txtInfoSocio, txtInfoLibro;
    private GestionBiblioteca controlador;
    private Socio socioEncontrado;
    private Libro libroEncontrado;

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
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL);
        setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150)); 
        
        // Panel de Título con subtítulo
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitulo = new JLabel("REGISTRAR NUEVO PRÉSTAMO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        
        JLabel lblSubtitulo = new JLabel("Complete los datos del préstamo a registrar", JLabel.CENTER);
        lblSubtitulo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(120, 120, 120));
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
        
        add(panelTitulo, BorderLayout.NORTH);
    
        // Tarjeta de Formulario
        PanelRedondeado tarjeta = crearTarjetaPrestamo();
        add(tarjeta, BorderLayout.CENTER);
    }
    
    // ******* OTROS MÉTODOS *******
    private PanelRedondeado crearTarjetaPrestamo() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, PaletaColores.FONDO_BLANCO, PaletaColores.COLOR_SECUNDARIO, 3
        );
        tarjeta.setLayout(new BorderLayout(25, 25));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        // Panel superior: Formulario de búsqueda
        JPanel panelFormulario = crearPanelFormulario();
        tarjeta.add(panelFormulario, BorderLayout.NORTH);
        
        // Panel central: Información encontrada
        JPanel panelInformacion = crearPanelInformacion();
        tarjeta.add(panelInformacion, BorderLayout.CENTER);
        
        // Panel inferior: Botón de confirmación
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panelBoton.setOpaque(false);
        
        btnPrestar = crearBotonEstilizado("Confirmar Préstamo", PaletaColores.COLOR_PRIMARIO);
        btnPrestar.setEnabled(false); // Deshabilitado hasta que se busquen los datos
        btnPrestar.addActionListener(e -> registrarPrestamo());
        panelBoton.add(btnPrestar);
        
        tarjeta.add(panelBoton, BorderLayout.SOUTH);
        
        return tarjeta;
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PaletaColores.COLOR_SECUNDARIO, 1, true),
                "Datos del Préstamo",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.BOLD, 14),
                PaletaColores.COLOR_PRIMARIO
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- FILA 1: DNI del Socio ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblDniSocio = crearEtiquetaEstilizada("D.N.I. del Socio:");
        panel.add(lblDniSocio, gbc);
        
        txtDniPrestamo = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtDniPrestamo, gbc);

        // --- FILA 2: Título del Libro ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        JLabel lblTitulo = crearEtiquetaEstilizada("Título del Libro:");
        panel.add(lblTitulo, gbc);
        
        txtTituloPrestamo = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.75;
        panel.add(txtTituloPrestamo, gbc);
        
        // --- FILA 3: Fecha del Préstamo ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        JLabel lblFecha = crearEtiquetaEstilizada("Fecha del Préstamo:");
        panel.add(lblFecha, gbc);
        
        spinnerFecha = crearSpinnerFecha();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.75;
        panel.add(spinnerFecha, gbc);
        
        // --- FILA 4: Botón Buscar ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        btnBuscar = crearBotonEstilizado("Buscar Socio y Libro", PaletaColores.COLOR_SECUNDARIO);
        btnBuscar.addActionListener(e -> buscarDatos());
        panel.add(btnBuscar, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Panel de información del socio
        JPanel panelSocio = crearPanelInfo("Información del Socio");
        txtInfoSocio = crearAreaInfoEstilizada();
        txtInfoSocio.setText("Busque un socio para ver su información");
        JScrollPane scrollSocio = new JScrollPane(txtInfoSocio);
        scrollSocio.setBorder(BorderFactory.createEmptyBorder());
        panelSocio.add(scrollSocio, BorderLayout.CENTER);
        
        // Panel de información del libro
        JPanel panelLibro = crearPanelInfo("Información del Libro");
        txtInfoLibro = crearAreaInfoEstilizada();
        txtInfoLibro.setText("Busque un libro para ver su información");
        JScrollPane scrollLibro = new JScrollPane(txtInfoLibro);
        scrollLibro.setBorder(BorderFactory.createEmptyBorder());
        panelLibro.add(scrollLibro, BorderLayout.CENTER);
        
        panel.add(panelSocio);
        panel.add(panelLibro);
        
        return panel;
    }
    
    private JPanel crearPanelInfo(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.BOLD, 13));
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
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
        
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Efecto hover
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
    
    private JSpinner crearSpinnerFecha() {
        // Modelo de fecha con fecha actual por defecto
        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(new Date()); // Fecha actual
        
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 14));
        spinner.setPreferredSize(new Dimension(spinner.getPreferredSize().width, 40));
        
        // Editor para formato de fecha
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        
        // Estilo del campo de texto dentro del spinner
        JFormattedTextField textField = ((JSpinner.DateEditor) spinner.getEditor()).getTextField();
        textField.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        spinner.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        
        return spinner;
    }
    
    private JTextArea crearAreaInfoEstilizada() {
        JTextArea area = new JTextArea(5, 20);
        area.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 13));
        area.setEditable(false);
        area.setBackground(new Color(250, 250, 250));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
    }
    
    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font(Estilo.FUENTE_BOTON.getFamily(), Font.BOLD, 15));
        boton.setPreferredSize(new Dimension(250, 45));
        boton.setBackground(color);
        boton.setForeground(PaletaColores.TEXTO_CLARO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color, 0, true),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        
        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(color.darker());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(color);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(color.darker().darker());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(color.darker());
                }
            }
        });
        
        return boton;
    }
    
    // ===============================================
    //           LÓGICA DE BÚSQUEDA
    // ===============================================
    
    private void buscarDatos() {
        String dniStr = txtDniPrestamo.getText().trim();
        String titulo = txtTituloPrestamo.getText().trim();
        
        // Validación de campos vacíos
        if (dniStr.isEmpty() || titulo.isEmpty()) {
            mostrarMensajeError("Debe ingresar tanto el DNI del Socio como el Título del Libro.");
            return;
        }
        
        try {
            // Parseo de DNI
            int dni = Integer.parseInt(dniStr);
            
            // Búsqueda del Socio
            socioEncontrado = controlador.buscarSocioPorDni(dni);
            if (socioEncontrado == null) {
                txtInfoSocio.setText("❌ No se encontró un socio con DNI: " + dni);
                txtInfoSocio.setForeground(new Color(180, 0, 0));
                btnPrestar.setEnabled(false);
                return;
            } else {
                txtInfoSocio.setForeground(new Color(0, 120, 0));
                txtInfoSocio.setText(
                    "✓ SOCIO ENCONTRADO\n\n" +
                    "Nombre: " + socioEncontrado.getNombre() + "\n" +
                    "D.N.I.: " + socioEncontrado.getDniSocio() + "\n" +
                    "Tipo: " + socioEncontrado.getClass().getSimpleName()
                );
            }
            
            // Búsqueda del Libro
            libroEncontrado = controlador.buscarLibroPorTitulo(titulo);
            if (libroEncontrado == null) {
                // Verificar si existe el libro pero está prestado
                boolean existePeroNoDisponible = controlador.existeLibroPeroNoDisponible(titulo);
                
                if (existePeroNoDisponible) {
                    txtInfoLibro.setText(
                        "❌ El libro \"" + titulo + "\" existe pero\n" +
                        "todos los ejemplares están prestados.\n\n" +
                        "No hay copias disponibles en este momento."
                    );
                } else {
                    txtInfoLibro.setText("❌ No se encontró un libro con título:\n\"" + titulo + "\"");
                }
                
                txtInfoLibro.setForeground(new Color(180, 0, 0));
                btnPrestar.setEnabled(false);
                return;
            } else {
                txtInfoLibro.setForeground(new Color(0, 120, 0));
                txtInfoLibro.setText(
                    "✓ LIBRO ENCONTRADO\n\n" +
                    "Título: " + libroEncontrado.getTitulo() + "\n" +
                    "Editorial: " + libroEncontrado.getEditorial() + "\n" +
                    "Edición: " + libroEncontrado.getEdicion() + "\n" +
                    "Año: " + libroEncontrado.getAnio() + "\n" 
                );
            }
            
            // Si ambos fueron encontrados, habilitar el botón de préstamo
            if (socioEncontrado != null && libroEncontrado != null) {
                btnPrestar.setEnabled(true);
            }
            
        } catch (NumberFormatException e) {
            mostrarMensajeError("El DNI debe ser un número válido.");
            btnPrestar.setEnabled(false);
        } catch (Exception e) {
            mostrarMensajeError("Error al buscar datos: " + e.getMessage());
            btnPrestar.setEnabled(false);
        }
    }
    
    // ===============================================
    //           LÓGICA DE REGISTRO
    // ===============================================
    
    private void registrarPrestamo() {
        if (socioEncontrado == null || libroEncontrado == null) {
            mostrarMensajeError("Debe buscar y validar los datos antes de confirmar el préstamo.");
            return;
        }
        
        try {
            // Validar que la fecha no sea futura
            Date fechaSeleccionada = (Date) spinnerFecha.getValue();
            Date fechaActual = new Date();
            
            if (fechaSeleccionada.after(fechaActual)) {
                mostrarMensajeError("La fecha del préstamo no puede ser futura.");
                return;
            }
            
            // Registrar el préstamo
            controlador.registrarNuevoPrestamo(fechaSeleccionada, socioEncontrado, libroEncontrado);
            
            // Formato de fecha para mostrar
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaStr = sdf.format(fechaSeleccionada);
            
            mostrarMensajeExito(
                "Préstamo registrado exitosamente:\n\n" +
                "Libro: " + libroEncontrado.getTitulo() + "\n" +
                "Socio: " + socioEncontrado.getNombre() + "\n" +
                "Fecha: " + fechaStr
            );
            
            // Limpiar formulario
            limpiarFormulario();
            
        } catch (Exception e) {
            mostrarMensajeError("Error al registrar el préstamo: " + e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        txtDniPrestamo.setText("");
        txtTituloPrestamo.setText("");
        spinnerFecha.setValue(new Date());
        txtInfoSocio.setText("Busque un socio para ver su información");
        txtInfoSocio.setForeground(Color.BLACK);
        txtInfoLibro.setText("Busque un libro para ver su información");
        txtInfoLibro.setForeground(Color.BLACK);
        socioEncontrado = null;
        libroEncontrado = null;
        btnPrestar.setEnabled(false);
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
            "Préstamo Exitoso", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}