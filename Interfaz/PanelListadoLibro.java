package Interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import Biblioteca.Biblioteca;
import Controlador.GestionBiblioteca;

public class PanelListadoLibro extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private GestionBiblioteca controlador;

    // ******* CONSTRUCTOR *******
    public PanelListadoLibro(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.inicializarComponentes();
        // Asignar el ActionListener al botón de búsqueda
        btnBuscar.addActionListener(e -> buscarLibros());
    }
    
    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    // ******* MÉTODOS PRINCIPALES *******
    private void inicializarComponentes(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); 
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("LISTADO DE LIBROS", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel central (buscador + tabla) ---
        JPanel panelCentral = new JPanel(new BorderLayout(15, 15));
        panelCentral.setOpaque(false);
        panelCentral.add(crearPanelBusqueda(), BorderLayout.NORTH);
        panelCentral.add(crearTarjetaTabla(), BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
    }
    
    // ******* PANEL DE BÚSQUEDA *******
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setOpaque(false);
        
        JLabel lblFiltrar = new JLabel("Filtrar por Título/Estado:");
        lblFiltrar.setFont(Estilo.FUENTE_ETIQUETA);
        
        txtBusqueda = new JTextField(30);
        txtBusqueda.setFont(Estilo.FUENTE_ETIQUETA);
        txtBusqueda.setToolTipText("Ingrese parte del título o el estado del libro");
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(Estilo.FUENTE_BOTON);
        btnBuscar.setBackground(PaletaColores.COLOR_PRIMARIO);
        btnBuscar.setForeground(PaletaColores.TEXTO_CLARO);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        panel.add(lblFiltrar);
        panel.add(txtBusqueda);
        panel.add(btnBuscar);
        
        return panel;
    }
    
    // ******* TARJETA CON TABLA *******
    private PanelRedondeado crearTarjetaTabla() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, 
            PaletaColores.COLOR_SECUNDARIO, 
            3
        );
        tarjeta.setLayout(new BorderLayout(10,10));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Definición del Modelo de la Tabla
        String[] columnas = {"Nro", "Titulo", "Estado Préstamo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
             // Anulación para que las celdas no sean editables
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }
        };

        // 2. Creación de la JTable
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaLibros.setRowHeight(25); // Altura de fila para un aspecto más limpio
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.setShowHorizontalLines(true);
        tablaLibros.getTableHeader().setFont(Estilo.FUENTE_ETIQUETA);
        tablaLibros.getTableHeader().setBackground(PaletaColores.FONDO_BLANCO); 
        tablaLibros.getTableHeader().setReorderingAllowed(false);
        tablaLibros.setGridColor(new Color(230, 230, 230)); // Líneas de cuadrícula sutiles
 
        // 👇 *** CENTRAR EL CONTENIDO DE LAS CELDAS ***
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
    
        // Aplicar el renderizador a todas las columnas
        for (int i = 0; i < tablaLibros.getColumnCount(); i++) {
            tablaLibros.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        // 3. Agregar la tabla a un JScrollPane (imprescindible para JTable)
        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde feo del JScrollPane
        
        tarjeta.add(scrollPane, BorderLayout.CENTER);
        
        // Llamada a la función para cargar datos (inicialmente vacía)
        cargarDatosTabla();
        
        return tarjeta;
    }
    
    /**
     * Lógica para cargar los datos en el modelo de la tabla.
     * En tu proyecto POO, esto llamará a biblioteca.listaDeLibros()
     */
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        try {
            // 👇 Obtener datos desde el controlador
            ArrayList<String[]> listaLibros = controlador.listaDeLibros();
            
            if (listaLibros != null && !listaLibros.isEmpty()) {
                for (String[] libro : listaLibros) {
                    modeloTabla.addRow(libro); // libro ya es [titulo, estado]
                }
            } else {
                // Mensaje si no hay libros
                modeloTabla.addRow(new Object[]{"No hay libros registrados", ""});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar los libros: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ******* MÉTODO DE BÚSQUEDA/FILTRADO *******
    private void buscarLibros() {
        String busqueda = txtBusqueda.getText().trim().toLowerCase();
        
        // Si el campo está vacío, recargar todo
        if (busqueda.isEmpty()) {
            cargarDatosTabla();
            return;
        }
        
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        try {
            // Obtener lista completa
            ArrayList<String[]> listaCompleta = controlador.listaDeLibros();
            
            // Filtrar por título o estado
            boolean encontrado = false;
            for (String[] libro : listaCompleta) {
                String titulo = libro[0].toLowerCase();
                String estado = libro[1].toLowerCase();
                
                // Verificar si coincide con título o estado
                if (titulo.contains(busqueda) || estado.contains(busqueda)) {
                    modeloTabla.addRow(libro);
                    encontrado = true;
                }
            }
            
            // Si no se encontró nada
            if (!encontrado) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron libros que coincidan con: " + txtBusqueda.getText(),
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla(); // Recargar todos los datos
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar libros: " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void recargarTabla() {
        cargarDatosTabla();
    }

    public String getTextoBusqueda() {
        return txtBusqueda.getText().trim();
    }

    public JTable getTabla() {
        return tablaLibros;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}