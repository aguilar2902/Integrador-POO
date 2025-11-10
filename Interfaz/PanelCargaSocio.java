package interfaz;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controlador.GestionBiblioteca;

public class PanelCargaSocio extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTextField txtDni, txtNombre;
    private JComboBox<String> cmbTipoSocio;
    private JButton btnGuardar;
    private JTextField txtCarrera, txtArea;
    private JPanel panelEspecifico; 
    private CardLayout cardLayout = new CardLayout();
    private GestionBiblioteca controlador;

    // ******* CONSTRUCTOR *******
    public PanelCargaSocio(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.setVistaCargaSocio();
    }

    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    private void setVistaCargaSocio(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); 
        setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));
        
        // --- Título Superior ---
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO SOCIO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        
        JLabel lblSubtitulo = new JLabel("Complete el formulario con los datos del socio", JLabel.CENTER);
        lblSubtitulo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(120, 120, 120));
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
        
        add(panelTitulo, BorderLayout.NORTH);

        // --- Área Central: Tarjeta de Formulario ---
        PanelRedondeado tarjeta = crearTarjetaFormulario();
        add(tarjeta, BorderLayout.CENTER);
    }
    
    // ******* OTROS MÉTODOS *******
    private PanelRedondeado crearTarjetaFormulario() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, 
            PaletaColores.COLOR_SECUNDARIO, 
            3
        );
        tarjeta.setLayout(new BorderLayout(30, 30));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // 1. Panel Común (DNI, Nombre, ComboBox)
        JPanel panelComun = crearPanelComun();
        tarjeta.add(panelComun, BorderLayout.NORTH);

        // 2. Panel Específico (Dinámico)
        panelEspecifico = new JPanel(cardLayout);
        panelEspecifico.setOpaque(false);
        panelEspecifico.add(crearPanelEstudiante(), "Estudiante");
        panelEspecifico.add(crearPanelDocente(), "Docente");
        tarjeta.add(panelEspecifico, BorderLayout.CENTER);

        // 3. Botón Guardar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelBoton.setOpaque(false);
        
        btnGuardar = crearBotonEstilizado("Registrar Socio");
        btnGuardar.addActionListener(e -> registrarSocio());
        panelBoton.add(btnGuardar);
        
        tarjeta.add(panelBoton, BorderLayout.SOUTH);

        return tarjeta;
    }

    // ===============================================
    //           PANEL DE CAMPOS COMUNES
    // ===============================================

    private JPanel crearPanelComun() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO 1: DNI ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblDni = crearEtiquetaEstilizada("D.N.I.:");
        panel.add(lblDni, gbc);
        
        txtDni = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtDni, gbc);

        // --- CAMPO 2: Nombre y Apellido ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        JLabel lblNombre = crearEtiquetaEstilizada("Nombre y Apellido:");
        panel.add(lblNombre, gbc);
        
        txtNombre = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.75;
        panel.add(txtNombre, gbc);

        // --- CAMPO 3: Selector de Tipo de Socio ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        JLabel lblTipo = crearEtiquetaEstilizada("Tipo de Socio:");
        panel.add(lblTipo, gbc);
        
        String[] tipos = {"Estudiante", "Docente"};
        cmbTipoSocio = crearComboBoxEstilizado(tipos);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.75;
        panel.add(cmbTipoSocio, gbc);
        
        // Listener para cambiar el panel dinámico
        cmbTipoSocio.addActionListener(e -> {
            String seleccionado = (String) cmbTipoSocio.getSelectedItem();
            cardLayout.show(panelEspecifico, seleccionado);
        });

        return panel;
    }

    // ===============================================
    //           PANEL DE CAMPOS DOCENTE
    // ===============================================
    
    private JPanel crearPanelDocente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PaletaColores.COLOR_SECUNDARIO, 1, true),
                "Datos Específicos del Docente",
                0,
                0,
                new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.BOLD, 13),
                PaletaColores.COLOR_PRIMARIO
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO: Área ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblArea = crearEtiquetaEstilizada("Área de Especialización:");
        panel.add(lblArea, gbc);
        
        txtArea = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtArea, gbc);
        
        return panel;
    }

    // ===============================================
    //           PANEL DE CAMPOS ESTUDIANTE
    // ===============================================
    
    private JPanel crearPanelEstudiante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PaletaColores.COLOR_SECUNDARIO, 1, true),
                "Datos Específicos del Estudiante",
                0,
                0,
                new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.BOLD, 13),
                PaletaColores.COLOR_PRIMARIO
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO: Carrera ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        JLabel lblCarrera = crearEtiquetaEstilizada("Carrera:");
        panel.add(lblCarrera, gbc);
        
        txtCarrera = crearCampoTextoEstilizado(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.75;
        panel.add(txtCarrera, gbc);
        
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
    
    private JComboBox<String> crearComboBoxEstilizado(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font(Estilo.FUENTE_ETIQUETA.getFamily(), Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(combo.getPreferredSize().width, 40));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        return combo;
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

    // ===============================================
    //           LÓGICA DE REGISTRO
    // ===============================================

    private void registrarSocio() {
        String dniStr = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String tipo = (String) cmbTipoSocio.getSelectedItem();
        
        if (dniStr.isEmpty() || nombre.isEmpty()) {
             mostrarMensajeError("El DNI y Nombre son campos obligatorios.");
             return;
        }

        try {
            int dni = Integer.parseInt(dniStr);
            
            if (tipo.equals("Estudiante")) {
                String carrera = txtCarrera.getText().trim();
                if (carrera.isEmpty()) throw new IllegalArgumentException("La carrera es obligatoria.");
                
                this.controlador.nuevoSocioEstudiante(dni, nombre, carrera);
                mostrarMensajeExito("Estudiante " + nombre + " registrado exitosamente.");
            } else if (tipo.equals("Docente")) {
                String area = txtArea.getText().trim();
                if (area.isEmpty()) throw new IllegalArgumentException("El área es obligatoria.");
                this.controlador.nuevoSocioDocente(dni, nombre, area);
                mostrarMensajeExito("Docente " + nombre + " registrado exitosamente.");
            }
            
            // Limpiar campos después del éxito
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarMensajeError("El DNI debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        } catch (Exception e) {
            mostrarMensajeError("Error de registro: " + e.getMessage());
        }
    }
    
    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombre.setText("");
        txtCarrera.setText("");
        txtArea.setText("");
        cmbTipoSocio.setSelectedIndex(0);
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