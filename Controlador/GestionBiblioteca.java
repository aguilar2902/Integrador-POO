// Archivo: GestionBiblioteca.java (en el paquete controlador)
package Controlador; // 👈 Cambiar el paquete

import Interfaz.*;
import javax.swing.SwingUtilities;
import java.util.*;
import java.util.regex.*;

// Debes importar la clase de tu capa de Lógica de Negocio
import Biblioteca.Biblioteca;
import Biblioteca.Socio;
import Biblioteca.Libro;
import java.util.ArrayList; 
import Persistencia.Persistencia; 

public class GestionBiblioteca {

    // Paso 1: Atributo para la Lógica de Negocio
    private Biblioteca bibliotecaActual;
    
    // Paso 2: El constructor maneja la CARGA de la persistencia
    public GestionBiblioteca() {
        // Carga de datos al inicio de la aplicación
        this.bibliotecaActual = Persistencia.cargar();
    }

    // Paso 3: Método de ENLACE para ser llamado por la GUI (al cerrar)
    public void salirYGuardar() {
        Persistencia.guardar(this.bibliotecaActual);
    }
    
    // Nuevo método para la interfaz
    public void nuevoSocioEstudiante(int dni, String nombre, String carrera) throws IllegalArgumentException {
        // Delega al método de la capa de lógica de negocio (Biblioteca)
        this.bibliotecaActual.nuevoSocioEstudiante(dni, nombre, carrera); 
    }
    
    public void nuevoSocioDocente(int dni, String nombre, String area) throws IllegalArgumentException {
        // Delega al método de la capa de lógica de negocio (Biblioteca)
        this.bibliotecaActual.nuevoSocioDocente(dni, nombre, area);
    }
    
    public void nuevoLibro(String p_titulo,int p_edicion,String p_editorial,int  p_anio){
        this.bibliotecaActual.nuevoLibro(p_titulo,p_edicion,p_editorial,p_anio);
    }
    
    public Socio buscarSocioPorDni(int dni){
        return this.bibliotecaActual.buscarSocio(dni);
    }
    
    public void registrarNuevoPrestamo(Socio socio, Libro libro) throws IllegalArgumentException {
    // Aquí puedes usar Calendar.getInstance() para la fecha actual
    Calendar fechaHoy = Calendar.getInstance(); 
    
    // Delega a la lógica de negocio, capturando la excepción si ocurre
    this.bibliotecaActual.prestarLibro(fechaHoy, socio, libro); 
}
    /**
     * Obtiene el String formateado, lo parsea y lo convierte en una ArrayList de 
     * String arrays (filas) para la JTable.
     * @param tipoFiltro El filtro a aplicar ("Todos", "Estudiante", "Docente").
     * @return ArrayList<String[]> lista de socios lista para JTable.
     */
    public ArrayList<String[]> obtenerListaSociosParaTabla(String tipoFiltro) {
        
        // 1. Obtener el String obligatorio de la capa de Negocio
        String listaCompleta = this.bibliotecaActual.listaDeSocios();
        
        // 2. Parsear el String para obtener una lista de String[]
        ArrayList<String[]> datosTabla = parsearStringSocios(listaCompleta);
        
        // 3. Aplicar el filtro final y devolver
        return aplicarFiltroTabla(datosTabla, tipoFiltro);
    }
    public Libro buscarLibroPorTitulo(String titulo) {
    // Obtenemos la lista maestra de libros de la capa de Negocio.
    ArrayList<Libro> libros = this.bibliotecaActual.getLibros(); 
    
    if (libros == null) {
        return null; // No hay inventario.
    }

    // Iteramos sobre los objetos Libro para encontrar la coincidencia por título
    for (Libro libro : libros) {
        // Usamos equalsIgnoreCase para una búsqueda más amigable
        if (libro.getTitulo().equalsIgnoreCase(titulo.trim())) { 
            return libro; // Devuelve el objeto Libro encontrado
        }
    }
    
    return null; // Libro no encontrado
}
    // --- MÉTODOS AUXILIARES DENTRO DE GESTIONBIBLIOTECA ---
    
    // 💡 El método ahora devuelve ArrayList<String[]>
    // Dentro de la clase GestionBiblioteca

    private ArrayList<String[]> parsearStringSocios(String listaCompleta) {
        
        ArrayList<String[]> datosTabla = new ArrayList<>();
        String[] lineas = listaCompleta.split("\n");
        
        for (String linea : lineas) {
            String lineaLimpia = linea.trim();
            
            // 🛑 LÍNEA DE CONTROL CLAVE: Detener el parseo cuando se encuentra el separador
            // Esto asegura que NO se intenten parsear las líneas de conteo.
            if (linea.contains("*" + "*".repeat(37))) { 
                break; 
            }
            
            // Criterio de identificación: Comienza con índice seguido de ')'
            if (lineaLimpia.matches("^\\d+\\).*")) { 
                try {
                    // 1. Quitar índice: "1) D.N.I.:..." -> "D.N.I.:..."
                    String datos = lineaLimpia.substring(lineaLimpia.indexOf(")") + 2);
                    String[] partes = datos.split(" \\|\\| "); 
                    
                    if (partes.length >= 3) {
                        
                        // Extracción de campos... (Lógica de parseo que ya tenías)
                        String dni = partes[0].substring(partes[0].indexOf(":") + 2).trim();
                        String tipoNombre = partes[1];
                        String nombreCompleto = tipoNombre.substring(0, tipoNombre.lastIndexOf("(")).trim();
                        String tipo = tipoNombre.substring(tipoNombre.lastIndexOf("(") + 1, tipoNombre.lastIndexOf(")")).trim();
                        String cantPrestados = partes[2].substring(partes[2].indexOf(":") + 2).trim();
    
                        datosTabla.add(new String[]{dni, nombreCompleto, tipo, cantPrestados}); 
                    }
                } catch (Exception e) {
                    System.err.println("Advertencia: No se pudo parsear la línea: " + lineaLimpia);
                }
            }
        }
        return datosTabla;
    }
    
    /**
     * Llama al String obligatorio y extrae únicamente la sección de conteo y resumen.
     * @return String con solo los conteos de Estudiantes y Docentes.
     */
    public String obtenerResumenConteo() {
        // 1. Obtener el String completo de la capa de Negocio
        String listaCompleta = this.bibliotecaActual.listaDeSocios();

        // 2. Definir el patrón para extraer la sección de conteo
        // Patrón busca: Línea de asteriscos, seguidos de líneas de "Cantidad de Socios...",
        // y finaliza con otra línea de asteriscos.
        // Utiliza '(?s)' para que el punto (.) coincida con saltos de línea.
        String regex = "(?s)(\\*+\\s*Cantidad de Socios del tipo Estudiante:.*?\\*+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(listaCompleta);

        if (matcher.find()) {
            // Devuelve el grupo capturado (todo el bloque de conteo)
            return matcher.group(1).trim();
        } else {
            // Si el patrón no coincide (por ejemplo, lista vacía o formato cambiado)
            return "No hay datos de resumen disponibles.";
        }
    }
    
    // 💡 El método ahora recibe y devuelve ArrayList<String[]>
    private ArrayList<String[]> aplicarFiltroTabla(ArrayList<String[]> datosCompletos, String tipoFiltro) {
        if (tipoFiltro.equals("Todos")) {
            return datosCompletos;
        }
        
        ArrayList<String[]> filtrados = new ArrayList<>();
        // 2 es el índice de la columna "Tipo"
        for (String[] fila : datosCompletos) {
            if (fila[2].equals(tipoFiltro)) { // fila[2] es el String del tipo de socio
                filtrados.add(fila);
            }
        }
        return filtrados;
    }
    // Paso 4: El método main inicializa todo
    public static void main(String[] args){
        // Creamos la instancia del controlador (que a su vez carga la persistencia)
        final GestionBiblioteca controlador = new GestionBiblioteca(); // 👈 Instancia del controlador

        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run(){
               // Solución al Problema 2: Pasamos la instancia 'controlador' al GUI
               new VentanaPrincipal(controlador); 
           }
        });
    }
}
