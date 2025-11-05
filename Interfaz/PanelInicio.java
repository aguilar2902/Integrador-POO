package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PanelInicio extends JPanel {

    // ******* CONSTRUCTOR *******
    public PanelInicio() { 
        this.setVistaInicio();
    }
    
    // ******* SETTERS *******
    private void setVistaInicio(){
        setLayout(new BorderLayout());
        setBackground(PaletaColores.FONDO_GENERAL);
        
        // ======= ENCABEZADO =======
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE BIBLIOTECA", JLabel.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(PaletaColores.TEXTO_OSCURO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);
        
        // ======= PANEL CENTRAL CON TARJETAS =======
        JPanel panelCentral = new JPanel(new GridLayout(2, 3, 25, 25));
        panelCentral.setBackground(PaletaColores.FONDO_GENERAL);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // --- DATOS ESTÁTICOS DE CONTEO ---
        String cantEstudiantes = "55"; // Dato Estático
        String cantDocentes = "21"; // Dato Estático
        String cantLibros = "142"; // Dato Estático
        
        
        // ======= TARJETAS SUPERIORES (Datos numéricos) - AHORA ESTÁTICOS =======
        panelCentral.add(crearTarjetaSimple("CANTIDAD DE ESTUDIANTES", cantEstudiantes)); 
        panelCentral.add(crearTarjetaSimple("CANTIDAD DE DOCENTES", cantDocentes));    
        panelCentral.add(crearTarjetaSimple("CANTIDAD DE LIBROS REGISTRADOS", cantLibros));

        // --- DATOS ESTÁTICOS DE LISTAS ---
        String[] responsablesEstaticos = {"Gómez, Laura", "Fernández, Pablo", "López, Ana"};
        String[] titulosEstaticos = {"POO con Java", "Cálculo Avanzado", "Teoría de Conjuntos"};
        String[] sociosEstaticos = {"Juan Pérez", "María Díaz", "Carlos López", "Ana Torres"};
        
        // ======= TARJETAS INFERIORES (Listas) - AHORA ESTÁTICOS =======
        
        // 1. Docentes Responsables
        // REEMPLAZO: Se usa el array estático
        panelCentral.add(crearTarjetaLista("DOCENTES RESPONSABLES", responsablesEstaticos));

        // 2. Lista de Títulos
        // REEMPLAZO: Se usa el array estático
        panelCentral.add(crearTarjetaLista("LIBROS REGISTRADOS", titulosEstaticos));

        // 3. Lista de Socios
        // REEMPLAZO: Se usa el array estático
        panelCentral.add(crearTarjetaLista("LISTA DE SOCIOS", sociosEstaticos));


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
    private JPanel crearTarjetaSimple(String titulo, String dato) {
        // ... (Tu implementación de crearTarjetaSimple, sin cambios necesarios) ...
        PanelRedondeado tarjeta = new PanelRedondeado(
             25, // radio
             PaletaColores.FONDO_BLANCO, 
             PaletaColores.COLOR_PRIMARIO, 
             4 // grosor
         );
         tarjeta.setLayout(new BorderLayout());
         tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

         JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
         lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
         lblTitulo.setForeground(new Color(60, 60, 60));

         JLabel lblDato = new JLabel(dato, JLabel.CENTER);
         lblDato.setFont(new Font("SansSerif", Font.BOLD, 55));
         lblDato.setForeground(PaletaColores.COLOR_PRIMARIO);


         tarjeta.add(lblTitulo, BorderLayout.NORTH);
         tarjeta.add(lblDato, BorderLayout.CENTER);

         return tarjeta;
    }

    // ==== TARJETA CON LISTA ====
    private JPanel crearTarjetaLista(String titulo, String[] elementos) {
        
         PanelRedondeado tarjeta = new PanelRedondeado(
             25,
             PaletaColores.FONDO_BLANCO, 
             PaletaColores.COLOR_PRIMARIO, 
             4
         );
         tarjeta.setLayout(new BorderLayout());
         tarjeta.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

         JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
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