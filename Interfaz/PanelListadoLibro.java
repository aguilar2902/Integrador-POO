package Interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class PanelListadoLibro extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTable tablaLibros;

    // ******* CONSTRUCTOR *******
    public PanelListadoLibro() {
        this.setVistaListadoLibro();
    }
    
    // ******* SETTERS *******
    private void setVistaListadoLibro(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); // Fondo Gris Claro
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Padding externo
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("LISTADO DE LIBROS EN INVENTARIO", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Área Central: Búsqueda y Tabla ---
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setOpaque(false); // Transparente para usar el fondo gris claro
        
        // 1. Barra de Búsqueda (Arriba)
        panelCentral.add(crearPanelBusqueda(), BorderLayout.NORTH);
        
        // 2. Tabla de Listado (Centro - Dentro de una Tarjeta)
        panelCentral.add(crearTarjetaTabla(), BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
    }
    
    // ******* OTROS MÉTODOS *******
    // ===============================================
    //               MÉTODOS AUXILIARES
    // ===============================================

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setOpaque(false);
        
        JTextField txtBusqueda = new JTextField(30);
        txtBusqueda.setFont(Estilo.FUENTE_ETIQUETA);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(Estilo.FUENTE_BOTON);
        btnBuscar.setBackground(PaletaColores.COLOR_PRIMARIO); // Botón de acción principal
        btnBuscar.setForeground(PaletaColores.TEXTO_CLARO);

        JLabel lblFiltrar = new JLabel("Filtrar por Título/Estado:");
        lblFiltrar.setFont(Estilo.FUENTE_ETIQUETA); // Aplicar la fuente en una línea separada

        panel.add(lblFiltrar); // Añadir la etiqueta al panel
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        
        return panel;
    }
    
    private PanelRedondeado crearTarjetaTabla() {
        // La tabla se inserta en tu PanelRedondeado para el look moderno de "tarjeta"
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, // Fondo Blanco puro
            PaletaColores.COLOR_SECUNDARIO, // Borde Azul Índigo
            3
        );
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 1. Definición del Modelo de la Tabla
        String[] columnas = {"Título", "Edición", "Editorial", "Año", "Estado Préstamo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
             // Anulación para que las celdas no sean editables
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };

        // 2. Creación de la JTable
        tablaLibros = new JTable(modelo);
        tablaLibros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaLibros.setRowHeight(25); // Altura de fila para un aspecto más limpio
        tablaLibros.getTableHeader().setFont(Estilo.FUENTE_ETIQUETA);
        tablaLibros.getTableHeader().setBackground(PaletaColores.FONDO_BLANCO); 
        tablaLibros.setGridColor(new Color(230, 230, 230)); // Líneas de cuadrícula sutiles

        // Llamada a la función para cargar datos (inicialmente vacía)
        cargarDatosTabla(modelo); 
        
        // 3. Agregar la tabla a un JScrollPane (imprescindible para JTable)
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde feo del JScrollPane
        
        tarjeta.add(scrollPane, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    /**
     * Lógica para cargar los datos en el modelo de la tabla.
     * En tu proyecto POO, esto llamará a biblioteca.listaDeLibros()
     */
    private void cargarDatosTabla(DefaultTableModel modelo) {
        // *** ESTA ES LA PARTE QUE CONECTA CON TU LÓGICA DE NEGOCIO ***
        
        // 1. Limpiar datos antiguos
        modelo.setRowCount(0);

        // 2. Datos de prueba (Reemplazar con la lógica de tu Biblioteca)
        /*
        ArrayList<Libro> lista = biblioteca.getLibros(); 
        for (Libro libro : lista) {
            String estado = libro.prestado() ? "PRESTADO 🚫" : "DISPONIBLE ✅";
            modelo.addRow(new Object[]{
                libro.getTitulo(),
                libro.getEdicion(),
                libro.getEditorial(),
                libro.getAnio(),
                estado
            });
        }
        */
        
        // Datos Estáticos para Testear el Diseño:
        modelo.addRow(new Object[]{"JAVA. Como Programar", 7, "Pearson", 2015, "DISPONIBLE ✅"});
        modelo.addRow(new Object[]{"Estructuras de Datos", 3, "Mc Graw Hill", 2018, "PRESTADO 🚫"});
        modelo.addRow(new Object[]{"Teoría de la Computación", 1, "Cengage", 2012, "DISPONIBLE ✅"});
        modelo.addRow(new Object[]{"POO con Python", 5, "O'Reilly", 2020, "PRESTADO 🚫"});
    }

    // --- Método para actualizar el listado ---
    public void actualizarListado() {
        // cargarDatosTabla((DefaultTableModel) tablaLibros.getModel());
        // Esto sería útil para recargar la lista si, por ejemplo, se registra un nuevo préstamo.
    }
}