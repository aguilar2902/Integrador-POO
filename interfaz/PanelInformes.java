package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.GestionBiblioteca;

public class PanelInformes extends JPanel {
    
    // ******* ATRIBUTOS *******
    private JTextArea txtResultado;
    private JComboBox<String> cmbInformes;
    private GestionBiblioteca controlador; 

    // ******* CONSTRUCTORES *******
    public PanelInformes(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.setVistaInformes();
    }
    
    // ******* SETTERS *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    private void setVistaInformes(){
        setLayout(new BorderLayout(20, 20));
        setBackground(PaletaColores.FONDO_GENERAL); 
        setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80)); 
        
        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("INFORMES Y CONSULTAS DE BIBLIOTECA", JLabel.CENTER);
        lblTitulo.setFont(Estilo.FUENTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(PaletaColores.COLOR_SECUNDARIO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Área Central: Selector y Resultado ---
        
        // Panel para el selector y el botón (arriba)
        JPanel panelControles = crearPanelControles();
        add(panelControles, BorderLayout.NORTH); 

        // Tarjeta para el resultado (centro)
        PanelRedondeado tarjetaResultado = crearTarjetaResultado();
        add(tarjetaResultado, BorderLayout.CENTER);
    }
    
    
    // ******* OTROS MÉTODOS *******
    private JPanel crearPanelControles() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setOpaque(false);

        // Opciones de Informes (Basadas en la consigna)
        String[] opciones = {
            "-- Seleccione un Informe --",
            "1. Cantidad de Socios por Tipo (Estudiante/Docente)",
            "2. Libros con Plazo de Devolución Vencido",
            "3. Listado de Títulos Únicos de la Biblioteca",
            "4. Docentes Responsables (Sin vencimientos)"
        };
        
        cmbInformes = new JComboBox<>(opciones);
        cmbInformes.setFont(Estilo.FUENTE_ETIQUETA);
        panel.add(cmbInformes);
        
        JButton btnGenerar = new JButton("Generar Informe");
        btnGenerar.setFont(Estilo.FUENTE_BOTON);
        btnGenerar.setBackground(PaletaColores.COLOR_PRIMARIO); 
        btnGenerar.setForeground(PaletaColores.TEXTO_CLARO);
        btnGenerar.setFocusPainted(false);
        
        // Listener para ejecutar el informe
        btnGenerar.addActionListener(e -> ejecutarInforme());
        
        panel.add(btnGenerar);
        
        return panel;
    }

    // ===============================================
    //               TARJETA DE RESULTADO
    // ===============================================
    
    private PanelRedondeado crearTarjetaResultado() {
        PanelRedondeado tarjeta = new PanelRedondeado(
            20, 
            PaletaColores.FONDO_BLANCO, 
            PaletaColores.COLOR_SECUNDARIO, 
            3
        );
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        
        // *** CLAVE: Usar la fuente Monospaced para los reportes formateados ***
        txtResultado.setFont(Estilo.FUENTE_REPORTE); 
        
        // Texto inicial de bienvenida
        txtResultado.setText("Seleccione una opción del menú y haga click en 'Generar Informe' para ver los resultados...");
        txtResultado.setForeground(new Color(100, 100, 100)); // Color gris sutil

        // El JScrollPane es necesario para un JTextArea que contendrá mucho texto
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Quitar el borde feo
        
        tarjeta.add(scrollPane, BorderLayout.CENTER);
        
        return tarjeta;
    }

    // ===============================================
    //               LÓGICA DE EJECUCIÓN
    // ===============================================

    private void ejecutarInforme() {
        String seleccion = (String) cmbInformes.getSelectedItem();
        txtResultado.setForeground(PaletaColores.TEXTO_OSCURO); // Cambiar a negro cuando hay resultado
        
        if (seleccion.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un informe válido.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String resultado = "";
            // Esto se mapea directamente a los métodos de tu clase Biblioteca
            
            if (seleccion.startsWith("1. Cantidad")) {
                resultado = "CANTIDAD DE SOCIOS\n" + this.controlador.obtenerResumenConteo();
            } else if (seleccion.startsWith("2. Libros con Plazo")) {
                resultado = this.controlador.listarPrestamosVencidos();
            } else if (seleccion.startsWith("3. Listado de Títulos")) {
                String[] miArray = this.controlador.listadoDeTitulos();
                
                StringBuilder texto = new StringBuilder();
                for (String linea : miArray) {
                    texto.append(linea).append("\n");
                }
                resultado = texto.toString();                
            } else if (seleccion.startsWith("4. Docentes Responsables")) {
                resultado = this.controlador.listaDocentesResponsables();
            }
            
            txtResultado.setText(resultado);
            txtResultado.setCaretPosition(0); // Scroll al inicio
            
        } catch (Exception e) {
            txtResultado.setText("¡ERROR al generar el informe!\n" + e.getMessage());
            txtResultado.setForeground(PaletaColores.COLOR_ALERTA); // Color rojo para errores
        }
    }
}