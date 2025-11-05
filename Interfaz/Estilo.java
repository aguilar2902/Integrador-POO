package Interfaz;


import java.awt.Font;

public final class Estilo {
    // Fuente principal en negrita para menús y títulos de formulario
    public static final Font FUENTE_MENU = new Font("SansSerif", Font.BOLD, 16);
    
    // Fuente para títulos grandes (ej: PanelInicio)
    public static final Font FUENTE_TITULO_PRINCIPAL = new Font("SansSerif", Font.BOLD, 30);
    
    // Fuente monoespaciada para JTextArea (vital para reportes formateados)
    public static final Font FUENTE_REPORTE = new Font("Monospaced", Font.PLAIN, 14);

    // Fuente para etiquetas de formulario
    public static final Font FUENTE_ETIQUETA = new Font("SansSerif", Font.BOLD, 14);

    // Fuente para botones
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 16);
}