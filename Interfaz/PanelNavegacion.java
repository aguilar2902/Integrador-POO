package interfaz;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import controlador.*;

public class PanelNavegacion extends JPanel{
    
    // ******* Atributos *******
    private final int ANCHO_SIDEBAR = 250; //Configuramos ancho fijo para menu lateral
    
    private JPanel panelSubSocios, panelSubLibros;
    private JButton btnInicio, btnPrestar, btnSocio, btnLibro, btnInformes;
    
    private VentanaPrincipal ventana;
    private GestionBiblioteca controlador;

    // ******* Constructor *******
    public PanelNavegacion(VentanaPrincipal p_ventana, GestionBiblioteca p_controlador) { 
        this.setVentana(p_ventana);
        this.setControlador(p_controlador);
        
        this.configurarPanelPrincipal();
        this.construirMenu();
    }
    
    // ******* Setters *******
    private void setVentana(VentanaPrincipal p_ventana){
        this.ventana = p_ventana;
    }
    
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    
    private void configurarPanelPrincipal(){
        // --- 1. Configuración del Panel ---
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertical para el menu lateral
        setBackground(PaletaColores.COLOR_SECUNDARIO); // Color Azul Índigo Oscuro
        setPreferredSize(new Dimension(ANCHO_SIDEBAR, 1000)); // menu lateral con ancho fijo y altura total de la ventana
        
        //JPanel header = crearEncabezado();
        //header.setAlignmentX(Component.CENTER_ALIGNMENT);
        //add(header);
        add(crearEncabezado());
    }
    
    private void construirMenu(){
        // BOTÓN: INICIO (Dashboard)
        btnInicio = crearBotonNavegacion("Inicio", "/img/home.png");
        btnInicio.addActionListener(e -> {
            cerrarSubmenus(); // <--- CERRAR SUBMENÚS
            ventana.mostrarPanel(new PanelInicio(controlador));
        });
        add(btnInicio);
        add(crearSeparador());
        // --------- BOTÓN SOCIOS (Desplegable) ----------------------
        btnSocio = crearBotonNavegacion("Socios", "/img/user.png");
        btnSocio.addActionListener(e -> alternarSubmenu(panelSubSocios));
        add(btnSocio);

        panelSubSocios = crearSubmenuContainer(
            crearSubBoton("Listar Socios", e -> ventana.mostrarPanel(new PanelListadoSocio(controlador))),
            crearSubBoton("Registrar Socio", e -> ventana.mostrarPanel(new PanelCargaSocio(controlador)))
        );
        add(panelSubSocios);
        add(crearSeparador());
        //----------------------------BOTÓN LIBROS (desplegable) -----------------------------------------
        
        btnLibro = crearBotonNavegacion("Libros", "/img/libro.png");
        btnLibro.addActionListener(e -> alternarSubmenu(panelSubLibros));
        add(btnLibro);

        panelSubLibros = crearSubmenuContainer(
            crearSubBoton("Registrar Nuevo Libro", e -> ventana.mostrarPanel(new PanelCargaLibro(controlador))),
            crearSubBoton("Listado de Libros", e -> ventana.mostrarPanel(new PanelListadoLibro(controlador))),
            crearSubBoton("Prestar Libro", e -> ventana.mostrarPanel(new PanelPrestamo(controlador))),
            crearSubBoton("Devolver Libro", e -> ventana.mostrarPanel(new PanelDevolucion(controlador)))
        );
        add(panelSubLibros);
        add(crearSeparador());
        
        // BOTÓN: INFORMES
        btnInformes = crearBotonNavegacion("Informes y Listados", "/img/informe.png");
        btnInformes.addActionListener((e -> {
            cerrarSubmenus(); // <--- CERRAR SUBMENÚS
            ventana.mostrarPanel(new PanelInformes());
        }));
        add(btnInformes);
        // Un espacio flexible para empujar el footer hacia abajo (si hubiera)
        add(Box.createVerticalGlue());
    }

    /**
     * Crea el encabezado con imagen, texto y línea divisoria.
     */

    private JPanel crearEncabezado() {
        JPanel panelHeader = new JPanel(new BorderLayout()); 
        panelHeader.setBackground(PaletaColores.COLOR_SECUNDARIO);
        panelHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 40)));
        // --- Contenedor Interno (Para el logo y el texto) ---
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(PaletaColores.COLOR_SECUNDARIO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); 
        // El componente interno (JLabel) también debe estar centrado
        JLabel lblLogo = new JLabel(cargarIcono("/img/logo.png"));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        JLabel lblTitulo = new JLabel("BIBLIOTECA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(PaletaColores.TEXTO_CLARO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 3. Añadir Logo y Título al Contenedor Interno
        panelContenido.add(lblLogo);
        panelContenido.add(lblTitulo);
        panelHeader.add(panelContenido, BorderLayout.CENTER); 
    
        // Forzar el tamaño
        Dimension headerDim = new Dimension(ANCHO_SIDEBAR, 150); 
        panelHeader.setMaximumSize(headerDim); 
        panelHeader.setPreferredSize(headerDim);
        // --- LÍNEA DIVISORIA (Simulada con un borde) ---
        // El borde se aplica al panelHeader, que tiene el tamaño forzado
        /*panelHeader.setBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, PaletaColores.TEXTO_CLARO)
        );*/
        
        return panelHeader;
    }
    
    // Método auxiliar para crear botones grandes y estilizados
    private JButton crearBotonNavegacion(String texto, String rutaIcono) {
        // 2. Creación del Botón
        JButton boton = new JButton(texto, cargarIcono(rutaIcono));
        boton.setFont(Estilo.FUENTE_MENU);
        boton.setForeground(PaletaColores.TEXTO_CLARO);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); 
        boton.setAlignmentX(Component.CENTER_ALIGNMENT); 
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(10); 
        boton.setMaximumSize(new Dimension(ANCHO_SIDEBAR - 20, 60));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new HoverEfecto(boton,
            PaletaColores.COLOR_SECUNDARIO,
            new Color(45, 60, 170),
            PaletaColores.TEXTO_CLARO,
            PaletaColores.COLOR_PRIMARIO));

        return boton;
    }
    
    
    
    /**
     * Alterna la visibilidad del submenú y refresca la ventana
     */
    private void toggleSubmenu(JPanel panelACambiar) {
        // 1. Array de todos los paneles desplegables que deben ser controlados.
        // Añade aquí cualquier otro panel que crees que deba tener este comportamiento.
        JPanel[] todosLosSubpaneles = {panelSubSocios, panelSubLibros};

        // 2. Comprobación de Estado: ¿El panel de destino ya está abierto?
        boolean isOpening = !panelACambiar.isVisible();
        // 3. CERRAR TODOS LOS DEMÁS PANELES
        for (JPanel panel : todosLosSubpaneles) {
            if (panel.isVisible()) {
                panel.setVisible(false);
            }
        }
        // 4. ABRIR EL PANEL DE DESTINO (SOLO si no estaba abierto)
        if (isOpening) {
            panelACambiar.setVisible(true);
        }
        
        ventana.revalidate(); 
        ventana.repaint();
    }
    
    
    
    /**
     * Crea botones de submenú con un estilo más compacto.
     */
    private JButton crearSubBoton(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        boton.setForeground(PaletaColores.TEXTO_CLARO);
        boton.setBackground(new Color(26, 45, 140));
        // --- Espaciado visual coherente ---
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(26, 45, 140)),
            BorderFactory.createEmptyBorder(10, 32, 10, 17)
        ));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(ANCHO_SIDEBAR - 20, 40)); 
        boton.setAlignmentX(Component.CENTER_ALIGNMENT); // mantiene centrado dentro del panel padre
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        // --- Interacción UX: hover + cursor ---
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addActionListener(accion);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(45, 70, 180));
                boton.setForeground(PaletaColores.COLOR_PRIMARIO);
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 3, 0, 0, PaletaColores.COLOR_PRIMARIO),
                    BorderFactory.createEmptyBorder(10, 32, 10, 17)
                ));
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(26, 45, 140));
                boton.setForeground(PaletaColores.TEXTO_CLARO);
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 3, 0, 0, new Color(26, 45, 140)),
                    BorderFactory.createEmptyBorder(10, 32, 10, 17)
                ));
            }
        });
    
        return boton;
    }
    
    /**
     * Crea un panel contenedor para los submenús.
     * Se inicializa oculto.
     */
    private JPanel crearSubmenuContainer(JButton... botones) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PaletaColores.COLOR_SECUNDARIO); // color similar al del menú lateral
        panel.setVisible(false); // inicialmente oculto
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (JButton b : botones) {
            panel.add(b);
        }
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        return panel;
    }

    
    private JComponent crearSeparador() {
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setForeground(new Color(255, 255, 255, 40));
        separador.setMaximumSize(new Dimension(ANCHO_SIDEBAR - 40, 1));
        separador.setAlignmentX(Component.CENTER_ALIGNMENT);
        return separador;
    }
    // ----------------------------------------------------------------
    // FUNCIONES DE COMPORTAMIENTO
    // ----------------------------------------------------------------
    private void alternarSubmenu(JPanel panel) {
        boolean abrir = !panel.isVisible();
        cerrarSubmenus();
        panel.setVisible(abrir);
        revalidate();
        repaint();
    }
    
    private void cerrarSubmenus() {
        JPanel[] submenus = {panelSubSocios, panelSubLibros};
        // Cerramos todos los que estén visibles
        for (JPanel panel : submenus) {
            panel.setVisible(false);
        }
        this.revalidate(); 
        this.repaint();
    }
    
    private Icon cargarIcono(String ruta) {
        URL url = getClass().getResource(ruta);
        return (url != null) ? new ImageIcon(url) : null;
    }
    // ----------------------------------------------------------------
    // CLASE AUXILIAR PARA EFECTOS DE HOVER
    // ----------------------------------------------------------------
    private static class HoverEfecto extends MouseAdapter {
        private final JButton boton;
        private final Color colorNormal, colorHover, textoNormal, textoHover;

        HoverEfecto(JButton boton, Color colorNormal, Color colorHover, Color textoNormal, Color textoHover) {
            this.boton = boton;
            this.colorNormal = colorNormal;
            this.colorHover = colorHover;
            this.textoNormal = textoNormal;
            this.textoHover = textoHover;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            boton.setBackground(colorHover);
            boton.setForeground(textoHover);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            boton.setBackground(colorNormal);
            boton.setForeground(textoNormal);
        }
    }
    
}