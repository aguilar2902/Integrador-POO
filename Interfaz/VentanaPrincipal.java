package interfaz;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.FlatLightLaf;
import biblioteca.*;
import controlador.GestionBiblioteca;


public class VentanaPrincipal extends JFrame {
    
    // ******* Atributos *******
    private JPanel panelActual, panelContenido;
    private GestionBiblioteca controlador;

    // ******* Constructor *******
    public VentanaPrincipal(GestionBiblioteca p_controlador) {
        this.setControlador(p_controlador);
        this.configurarApariencia();
        this.configurarVentana();
        this.configurarPaneles();
        setVisible(true);
    }
    
    // ******* Setter *******
    private void setControlador(GestionBiblioteca p_controlador){
        this.controlador = p_controlador;
    }
    
    // ******* Otros metodos *******
    private void configurarApariencia(){
        // Uso de la libreria FlatLaf - sirve para mejorar la apariencia y la experiencia del usuario
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void configurarVentana() {
        setTitle("Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Esto evita que la aplicación se cierre automáticamente y nos permite controlarlo.
        setSize(1600, 1000);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- CONFIRMACIÓN DE CIERRE ---
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarCierre();
            }
        });
    }
    
    private void configurarPaneles(){
        setLayout(new BorderLayout());
        add(new PanelNavegacion(this, controlador), BorderLayout.WEST);
        panelContenido = new JPanel(new BorderLayout()); 
        panelContenido.setBackground(PaletaColores.FONDO_GENERAL); 
        add(panelContenido, BorderLayout.CENTER);
        
        // Mostrar Panel de Inicio por defecto
        mostrarPanel(new PanelInicio(controlador)); 
    }

    // ------- CAMBIA EL PANEL MOSTRADO EN EL CENTRO DE LA VENTANA -------
    public void mostrarPanel(JPanel nuevo) {
        panelContenido.removeAll();
        panelContenido.add(nuevo, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    // ------- MÉTODO PARA MOSTRAR EL DIÁLOGO DE CIERRE DE APLICACION -------
    private void confirmarCierre() {
        int respuesta = JOptionPane.showConfirmDialog(
            this, // El componente padre
            "¿Estás seguro que deseas salir de la aplicación?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION, // Mostrar botones SÍ y NO
            JOptionPane.QUESTION_MESSAGE); // Mostrar icono de pregunta

        if (respuesta == JOptionPane.YES_OPTION) {
            controlador.salirYGuardar(); // Finaliza la aplicación de forma segura, guardando los datos en el archivo.
            System.exit(0); 
        }

    }
}