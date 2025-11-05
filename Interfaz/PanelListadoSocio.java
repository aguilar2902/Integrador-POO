package Interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import Biblioteca.Biblioteca; 
import Biblioteca.Socio; 
import Controlador.GestionBiblioteca;

public class PanelListadoSocio extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTable tablaSocios;
    private JTextArea areaTextoConteo;
    private JComboBox<String> cmbFiltroTipo;
    private GestionBiblioteca controlador; 

    // ******* CONSTRUCTOR *******
    public PanelListadoSocio(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);        
        this.setVistaListadoSocio();
    }
    
    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    private void setVistaListadoSocio(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); 
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); 
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("LISTADO DE SOCIOS REGISTRADOS", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_PRIMARIO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Área Central: Filtro y Tabla ---
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setOpaque(false); 
        
        // 1. Panel de Filtro (Arriba)
        panelCentral.add(crearPanelFiltro(), BorderLayout.NORTH);
        
        // 2. Tabla de Listado (Centro - Dentro de una Tarjeta)
        panelCentral.add(crearTarjetaTabla(), BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Inicializa la tabla con todos los socios
        cargarDatosTabla("Todos");
    }
    
    // ******* OTROS MÉTODOS *******  
    // ===============================================
    //               MÉTODOS AUXILIARES
    // ===============================================

    private JPanel crearPanelFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setOpaque(false);
        
        JLabel lblFiltro = new JLabel("Mostrar Tipo de Socio:");
        lblFiltro.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(lblFiltro);
        
        String[] tipos = {"Todos", "Estudiante", "Docente"};
        cmbFiltroTipo = new JComboBox<>(tipos);
        cmbFiltroTipo.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(cmbFiltroTipo);
        
        // Agrega el listener para filtrar al seleccionar una opción
        cmbFiltroTipo.addActionListener(e -> {
            String tipoSeleccionado = (String) cmbFiltroTipo.getSelectedItem();
            cargarDatosTabla(tipoSeleccionado);
        });
        
        return panel;
    }
    
    private PanelRedondeado crearTarjetaTabla() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, PaletaColores.FONDO_BLANCO, PaletaColores.COLOR_SECUNDARIO, 3
        );
        // 💡 CAMBIO: Usamos BorderLayout para apilar la tabla (CENTER) y el texto (SOUTH)
        tarjeta.setLayout(new BorderLayout(10, 10)); 
        tarjeta.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // --- 1. JTable y JScrollPane (TOP/CENTER) ---
        // (Tu lógica anterior de creación de JTable y DefaultTableModel va aquí)
        String[] columnas = {"D.N.I.", "Nombre y Apellido", "Tipo", "Libros Prestados"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaSocios = new JTable(modelo);
        tablaSocios.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaSocios.setRowHeight(25);
        tablaSocios.getTableHeader().setFont(Estilo.FUENTE_ETIQUETA);
        tablaSocios.getTableHeader().setBackground(PaletaColores.FONDO_BLANCO); 
        tablaSocios.setGridColor(new Color(230, 230, 230));
        
        JScrollPane scrollPane = new JScrollPane(tablaSocios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); 
        tarjeta.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro
    
        // --- 2. JTextArea (BOTTOM/SOUTH) ---
        areaTextoConteo = new JTextArea(5, 50); // 5 filas de alto, 50 columnas de ancho
        areaTextoConteo.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14)); // Monoespacio para alineación
        areaTextoConteo.setEditable(false);
        areaTextoConteo.setBackground(PaletaColores.FONDO_GENERAL); // Para diferenciar
        areaTextoConteo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Lo agregamos envuelto en un JScrollPane por si el texto fuera muy largo
        JScrollPane scrollTexto = new JScrollPane(areaTextoConteo);
        scrollTexto.setPreferredSize(new Dimension(500, 150)); // Altura fija para el texto
        tarjeta.add(scrollTexto, BorderLayout.SOUTH); // Área de texto debajo de la tabla
        
        return tarjeta;
    }
    
    /**
     * Carga los datos en la tabla, filtrando por tipo de socio.
     */
    private void cargarDatosTabla(String tipo) {
        DefaultTableModel modelo = (DefaultTableModel) tablaSocios.getModel();
        modelo.setRowCount(0);
        // *** ESTA ES LA PARTE QUE CONECTA CON TU LÓGICA DE NEGOCIO ***
         try{
            // Obtener el String COMPLETO para el JTextArea
            String listaCompleta = this.controlador.obtenerResumenConteo(); 
            areaTextoConteo.setText(listaCompleta);
            areaTextoConteo.setCaretPosition(0); // Scroll al inicio
            
            ArrayList<String[]> datosSocios = this.controlador.obtenerListaSociosParaTabla(tipo);
             
            for (String[] filaSocio : datosSocios) {
                modelo.addRow(filaSocio);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de la aplicación al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}