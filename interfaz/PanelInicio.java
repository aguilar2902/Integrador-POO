package interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import controlador.GestionBiblioteca;

public class PanelInicio extends JPanel {
    private GestionBiblioteca controlador;

    // ******* CONSTRUCTOR *******
    public PanelInicio(GestionBiblioteca p_controlador) { 
        this.setControlador(p_controlador);
        this.setVistaInicio();
    }
    
    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    private void setVistaInicio(){
        setLayout(new BorderLayout());
        setBackground(PaletaColores.FONDO_GENERAL);
        
        // ======= ENCABEZADO =======
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE BIBLIOTECA", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_DASHBOARD);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);
        
        // ======= PANEL CENTRAL CON TARJETAS =======
        JPanel panelCentral = new JPanel(new GridLayout(2, 2, 25, 25));
        panelCentral.setBackground(PaletaColores.FONDO_GENERAL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // --- DATOS ESTÁTICOS DE CONTEO ---
        String cantEstudiantes = String.valueOf(this.controlador.obtenerSociosPorTipo("estudiante"));
        String cantLibros = String.valueOf(this.controlador.obtenerCantidadLibrosRegistrados());
        
        
        // ======= TARJETAS SUPERIORES (Datos numéricos) - AHORA ESTÁTICOS =======
        panelCentral.add(crearTarjetaSimple("CANTIDAD DE ESTUDIANTES", cantEstudiantes, "👥"));
        panelCentral.add(crearTarjetaSimple("CANTIDAD DE LIBROS REGISTRADOS", cantLibros, "📚"));
        
        // ======= TARJETAS INFERIORES (Listas) =======
        panelCentral.add(crearTarjetaLista("DOCENTES RESPONSABLES", this.controlador.listaDeDocentesResponsables(), "👨‍🏫"));
        panelCentral.add(crearTarjetaLista("LIBROS REGISTRADOS", this.controlador.listadoDeTitulos(), "📖"));

        add(panelCentral, BorderLayout.CENTER);
        // ======= PIE DE PÁGINA =======
        JLabel lblFooter = new JLabel("Sistema de Gestión de Biblioteca", JLabel.CENTER);
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 14));
        lblFooter.setForeground(new Color(0, 0, 0));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblFooter, BorderLayout.SOUTH);
    }
    // ******* OTROS MÉTODOS *******

    // ==== TARJETA NUMÉRICA ====
    private JPanel crearTarjetaSimple(String titulo, String dato, String icono) {
        PanelRedondeado tarjeta = new PanelRedondeado(
             25, // radio
             PaletaColores.FONDO_BLANCO, 
             PaletaColores.COLOR_SECUNDARIO, 
             4 // grosor
         );
         tarjeta.setLayout(new BorderLayout());
         tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

         JLabel lblTitulo = new JLabel(icono + " " +titulo, JLabel.CENTER);
         lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
         lblTitulo.setForeground(new Color(60, 60, 60));

         JLabel lblDato = new JLabel(dato, JLabel.CENTER);
         lblDato.setFont(Estilo.FUENTE_DATO_GRANDE);
         lblDato.setForeground(PaletaColores.COLOR_PRIMARIO);
    

         tarjeta.add(lblTitulo, BorderLayout.NORTH);
         tarjeta.add(lblDato, BorderLayout.CENTER);

         return tarjeta;
    }

    // ==== TARJETA CON LISTA ====
    private JPanel crearTarjetaLista(String titulo, String[] elementos, String icono) {
        if (elementos == null || elementos.length == 0) {
            elementos = new String[]{"No hay datos disponibles"};
        }
         PanelRedondeado tarjeta = new PanelRedondeado(
             25,
             PaletaColores.FONDO_BLANCO, 
             PaletaColores.COLOR_SECUNDARIO, 
             4
         );
         tarjeta.setLayout(new BorderLayout());
         tarjeta.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

         JLabel lblTitulo = new JLabel(icono + " " + titulo, JLabel.CENTER);
         lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
         lblTitulo.setForeground(new Color(60, 60, 60));
         lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

         JList<String> lista = new JList<>(elementos);
         lista.setBackground(PaletaColores.FONDO_BLANCO); 
         lista.setForeground(PaletaColores.TEXTO_OSCURO);
         lista.setFont(new Font("SansSerif", Font.PLAIN, 14));

         JScrollPane scroll = new JScrollPane(lista);
         scroll.setBorder(BorderFactory.createEmptyBorder());

         tarjeta.add(lblTitulo, BorderLayout.NORTH);
         tarjeta.add(scroll, BorderLayout.CENTER);

         return tarjeta;
    }
}