package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controlador.GestionBiblioteca;

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
        setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150)); // Más padding lateral
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("REGISTRO DE NUEVO SOCIO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO); // Usamos color primario aquí
        add(lblTitulo, BorderLayout.NORTH);

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
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

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
        btnGuardar = new JButton("Registrar Socio");
        btnGuardar.setFont(Estilo.FUENTE_BOTON);
        btnGuardar.setBackground(PaletaColores.COLOR_PRIMARIO);
        btnGuardar.setForeground(PaletaColores.TEXTO_CLARO);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> registrarSocio());
        tarjeta.add(btnGuardar, BorderLayout.SOUTH);

        return tarjeta;
    }

    // ===============================================
    //           PANEL DE CAMPOS COMUNES
    // ===============================================

    private JPanel crearPanelComun() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO 1: DNI ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel lblDni = new JLabel("D.N.I.:");
        lblDni.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblDni, gbc);
        
        txtDni = new JTextField(20);
        txtDni.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        panel.add(txtDni, gbc);

        // --- CAMPO 2: Nombre y Apellido ---
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2;
        JLabel lblNombre = new JLabel("Nombre y Apellido:");
        lblNombre.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblNombre, gbc);
        
        txtNombre = new JTextField(20);
        txtNombre.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.8;
        panel.add(txtNombre, gbc);

        // --- CAMPO 3: Selector de Tipo de Socio ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.2;
        JLabel lblTipo = new JLabel("Tipo de Socio:");
        lblTipo.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblTipo, gbc);
        
        String[] tipos = {"Estudiante", "Docente"};
        cmbTipoSocio = new JComboBox<>(tipos);
        cmbTipoSocio.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.8;
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos Específicos del Docente"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO: Área ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel lblArea = new JLabel("Área de Especialización:");
        lblArea.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblArea, gbc);
        
        txtArea = new JTextField(20);
        txtArea.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        panel.add(txtArea, gbc);
        
        return panel;
    }

    // ===============================================
    //           PANEL DE CAMPOS ESTUDIANTE
    // ===============================================
    
    private JPanel crearPanelEstudiante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("Datos Específicos del Estudiante"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // --- CAMPO: Carrera ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        JLabel lblCarrera = new JLabel("Carrera:");
        lblCarrera.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblCarrera, gbc);
        
        txtCarrera = new JTextField(20);
        txtCarrera.setFont(Estilo.FUENTE_ETIQUETA);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        panel.add(txtCarrera, gbc);
        
        return panel;
    }

    // ===============================================
    //           LÓGICA DE REGISTRO
    // ===============================================

    private void registrarSocio() {
        String dniStr = txtDni.getText();
        String nombre = txtNombre.getText();
        String tipo = (String) cmbTipoSocio.getSelectedItem();
        
        if (dniStr.isEmpty() || nombre.isEmpty()) {
             JOptionPane.showMessageDialog(this, "El DNI y Nombre son campos obligatorios.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
             return;
        }

        try {
            int dni = Integer.parseInt(dniStr);
            
            if (tipo.equals("Estudiante")) {
                String carrera = txtCarrera.getText();
                if (carrera.isEmpty()) throw new IllegalArgumentException("La carrera es obligatoria.");
                
                this.controlador.nuevoSocioEstudiante(dni, nombre, carrera);
                JOptionPane.showMessageDialog(this, "Estudiante " + nombre + " (" + carrera + ") registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else if (tipo.equals("Docente")) {
                String area = txtArea.getText();
                if (area.isEmpty()) throw new IllegalArgumentException("El área es obligatoria.");
                this.controlador.nuevoSocioDocente(dni, nombre, area);
                JOptionPane.showMessageDialog(this, "Docente " + nombre + " (" + area + ") registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Limpiar campos después del éxito
            txtDni.setText("");
            txtNombre.setText("");
            txtCarrera.setText("");
            txtArea.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error de registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}