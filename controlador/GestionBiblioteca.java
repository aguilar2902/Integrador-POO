package controlador;

import interfaz.*;
import javax.swing.SwingUtilities;
import java.util.*;
import java.util.regex.*;
import biblioteca.*;
import persistencia.*;
import java.text.*;

/**
 * Write a description of class GestionBiblioteca here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GestionBiblioteca
{
    private Biblioteca biblioteca;
    public GestionBiblioteca() {
        // Carga de datos al inicio de la aplicación
        this.biblioteca = Persistencia.cargar();
    }

    //Método de ENLACE para ser llamado por la GUI (al cerrar)
    public void salirYGuardar() {
        //guarda los datos al cerrar la aplicacion
        Persistencia.guardar(this.biblioteca);
    }
    
    /*
       ----- Metodos para hacer de nexo entre la logica y la interfaz ----
    */
    public void nuevoSocioEstudiante(int dni, String nombre, String carrera) throws IllegalArgumentException {
        this.biblioteca.nuevoSocioEstudiante(dni, nombre, carrera); 
    }
    
    public void nuevoSocioDocente(int dni, String nombre, String area) throws IllegalArgumentException {
        this.biblioteca.nuevoSocioDocente(dni, nombre, area);
    }
    
    /**
     * Elimina un socio del sistema.
     * @param dni DNI del socio a eliminar
     * @return true si se eliminó exitosamente, false si no se encontró
     * @throws IllegalArgumentException Si el socio tiene préstamos activos
     */
    public boolean eliminarSocio(int dni) throws IllegalArgumentException {
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            return false; // No existe el socio
        }
        // Verificar que no tenga préstamos activos
        if (socio.cantLibrosPrestados() > 0) {
            throw new IllegalArgumentException(
                "No se puede eliminar el socio " + socio.getNombre() + 
                " porque tiene " + socio.cantLibrosPrestados() + " préstamo(s) activo(s)"
            );
        }
        return this.biblioteca.quitarSocio(socio);
    }
    
     public void nuevoLibro(String p_titulo,int p_edicion,String p_editorial,int  p_anio){
        this.biblioteca.nuevoLibro(p_titulo,p_edicion,p_editorial,p_anio);
    }
    
    public void registrarNuevoPrestamo(Date p_fecha, Socio socio, Libro libro) throws IllegalArgumentException {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(p_fecha);
        this.biblioteca.prestarLibro(fecha, socio, libro); 
    }
    
    /**
     * Procesa la devolución de un libro.
     * @param tituloLibro Título del libro a devolver
     * @throws IllegalArgumentException Si el libro no existe o no está prestado
     */
    public void procesarDevolucion(String tituloLibro) throws LibroNoPrestadoException {
        try {
            // Buscar el libro por título
            Libro libro = buscarLibroPrestadoPorTitulo(tituloLibro);
            if (libro == null) {
                throw new IllegalArgumentException("El libro '" + tituloLibro + "' no fue encontrado en el sistema");
            }
            this.biblioteca.devolverLibro(libro);
        } catch (LibroNoPrestadoException e) {
            // Convertir la excepción específica en IllegalArgumentException para la GUI
            throw new LibroNoPrestadoException(e.getMessage());
        }
    }
    
    public int obtenerSociosPorTipo(String p_tipo){
        return this.biblioteca.cantidadDeSociosPorTipo(p_tipo);
    }
    
    
    public ArrayList<Prestamo> prestamosVencidos(){
        return this.biblioteca.prestamosVencidos();
    }
    
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException{
        return this.biblioteca.quienTieneElLibro(p_libro);
    }
    
    public String listaDeSocios(){
        return this.biblioteca.listaDeSocios();
    }
    
    public Socio buscarSocioPorDni(int dni){
        return this.biblioteca.buscarSocio(dni);
    }
    
    public String listaDeLibros(){
        return this.biblioteca.listaDeLibros();
    }
    
    public String listaDeTitulos(){
        return this.biblioteca.listaDeTitulos();
    }
    
    public String listaDocentesResponsables(){
        return this.biblioteca.listaDeDocentesResponsables();
    }
    
    /* 
     * 
     * --- MÉTODOS AUXILIARES DENTRO DE GESTIONBIBLIOTECA --- 
     * 
     * 
    */
   
    public String[] listaDeDocentesResponsables(){
        String listaCompleta = listaDocentesResponsables();
        String[] lineas = listaCompleta.split("\n");
        if (lineas.length > 1) {
            // Crear un nuevo array que comienza desde el índice 2 (ahi comienza la lista de docentes)
            // lineas.length es el índice del final (exclusivo)
            String[] lineasFiltradas = Arrays.copyOfRange(lineas, 2, lineas.length);
            return lineasFiltradas;
        }
    
        // Si solo hay una línea o está vacío, devolver un array vacío o el original (según la necesidad)
        // En este caso, si solo hay una línea (que asumimos es el encabezado), devolvemos vacío.
        return new String[0];
    }
    
    public boolean esDocenteResponsable(int dni) {
        ArrayList<Socio> socios = biblioteca.getSocios();
        for (Socio socio : socios) {
            if (socio instanceof Docente && socio.getDniSocio() == dni) {
                Docente docente = (Docente) socio;
                return docente.esResponsable(); // Método que debe existir en Docente
            }
        }
        return false;
    }
    
    /**
     * Obtiene el String formateado, lo parsea y lo convierte en una ArrayList de 
     * String arrays (filas) para la JTable.
     * @param tipoFiltro El filtro a aplicar ("Todos", "Estudiante", "Docente").
     * @return ArrayList<String[]> lista de socios lista para JTable.
     */
    public ArrayList<String[]> obtenerListaSociosParaTabla(String tipoFiltro) {
        // Obtener el String obligatorio de la capa de Negocio
        String listaCompleta = this.biblioteca.listaDeSocios();
        // Parsear el String para obtener una lista de String[]
        ArrayList<String[]> datosTabla = parsearStringSocios(listaCompleta);
        // Aplicar el filtro final y devolver
        return aplicarFiltroTabla(datosTabla, tipoFiltro);
    }
    
    public ArrayList<String[]> listadoDeLibros(){
        String listaLibros = this.biblioteca.listaDeLibros();
        ArrayList<String[]> datosLibros = new ArrayList<String[]>();
        String[]lineas = listaLibros.split("\n"); //divido String por lineas
        int numeroFila = 1; 
        int indiceReal = 0;
        
        for (String linea : lineas) {
            // Ejemplo de línea: "1) Titulo: Java. Como Programar || Prestado: (No)"
            if (linea.contains("Titulo:") && linea.contains("Prestado:")) {
                // Extraer el título
                String titulo = linea.substring(linea.indexOf("Titulo:") + 8, linea.indexOf("||")).trim();
                // Extraer el estado de préstamo
                String prestado = linea.substring(linea.indexOf("Prestado: (") + 11, linea.lastIndexOf(")")).trim();
                // 👇 Convertir "Si"/"No" a formato visual con emojis
                String estadoFormateado;
                if (prestado.equalsIgnoreCase("Si")) {
                    estadoFormateado = "PRESTADO 🚫";
                } else {
                    estadoFormateado = "DISPONIBLE ✅";
                }
                // Guardar el resultado como array
                datosLibros.add(new String[]{
                    String.valueOf(numeroFila),
                    titulo, 
                    estadoFormateado,
                    String.valueOf(indiceReal)
                });
                numeroFila++;
                indiceReal++;
            }
        }
        return datosLibros;
    }
    
    public String[] listadoDeTitulos(){
        String listaCompleta = this.biblioteca.listaDeTitulos();
        String[] lineas =listaCompleta.split("\n");
        return lineas;
    }
    
    /**
     * Obtiene los días de préstamo de un docente
     */
    public int obtenerDiasPrestamoDocente(int dni) {
        ArrayList<Socio> socios = biblioteca.getSocios();
        for (Socio socio : socios) {
            if (socio instanceof Docente && socio.getDniSocio() == dni) {
                return socio.getDiasPrestamos();
            }
        }
        return 0;
    }
    
    /**
     * Modifica los días de préstamo de un docente responsable
     */
    
    public boolean modificarDiasPrestamoResponsable(int dni, int nuevosDias) {
        ArrayList<Socio> socios = biblioteca.getSocios();
        for (Socio socio : socios) {
            if (socio instanceof Docente && socio.getDniSocio() == dni) {
                Docente docente = (Docente) socio;
                // Solo permitir si es responsable
                if (docente.esResponsable()) {
                    docente.cambiarDiasDePrestamo(nuevosDias); // Método que debe existir en Docente
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Obtiene los detalles de un libro específico (usando el objeto Libro).
     * Útil cuando ya tienes la referencia exacta del libro.
     * @param libro Objeto Libro del cual obtener detalles
     * @return String con los detalles formateados
     */
    public String obtenerDetallesLibro(Libro libro) {
        if (libro == null) {
            return "Libro no encontrado";
        }
        
        try {
            StringBuilder detalles = new StringBuilder();
            detalles.append("📚 Título: ").append(libro.getTitulo()).append("\n");
            detalles.append("📖 Edición: ").append(libro.getEdicion()).append("\n");
            detalles.append("🏢 Editorial: ").append(libro.getEditorial()).append("\n");
            detalles.append("📅 Año: ").append(libro.getAnio()).append("\n\n");
            
            if (libro.prestado()) {
                Prestamo prestamo = libro.ultimoPrestamo();
                
                if (prestamo != null && prestamo.getSocio() != null) {
                    Socio socio = prestamo.getSocio();
                    
                    detalles.append("📌 ESTADO: PRESTADO\n\n");
                    detalles.append("👤 Prestado a:\n");
                    detalles.append("   • Nombre: ").append(this.biblioteca.quienTieneElLibro(libro)).append("\n");
                    detalles.append("   • DNI: ").append(socio.getDniSocio()).append("\n");
                    detalles.append("   • Días prestado: ").append(socio.getDiasPrestamos()).append("\n\n");
                    
                    detalles.append("📅 Fecha de préstamo: ")
                            .append(formatearFecha(prestamo.getFechaRetiro()))
                            .append("\n");
                    detalles.append("📅 Fecha de devolución: ")
                            .append(formatearFecha(prestamo.getFechaDevolucion()));
                } else {
                    detalles.append("📌 ESTADO: PRESTADO\n");
                    detalles.append("⚠️ No se encontró información del préstamo actual");
                }
            } else {
                detalles.append("📌 ESTADO: DISPONIBLE EN BIBLIOTECA ✅");
            }
            
            return detalles.toString();
            
        } catch (Exception e) {
            return "Error al obtener detalles: " + e.getMessage();
        }
}
    
    /**
     * Busca y obtiene detalles del primer libro DISPONIBLE con ese título.
     * @param titulo Título del libro a buscar
     * @return String con los detalles del libro disponible, o mensaje de error
     */
    public String obtenerDetallesLibroDisponible(String titulo) {
        Libro libro = buscarLibroPorTitulo(titulo); // Este ya busca uno disponible
        
        if (libro == null) {
            if (existeLibroPeroNoDisponible(titulo)) {
                return "❌ El libro \"" + titulo + "\" existe pero\ntodos los ejemplares están prestados.";
            } else {
                return "❌ No se encontró un libro con título: \"" + titulo + "\"";
            }
        }
        
        return obtenerDetallesLibro(libro);
    }
    
    /**
     * Método que obtiene los préstamos vencidos y los formatea para mostrar en un JTextArea.
     * 
     * @return String con el listado de préstamos vencidos formateado
     */
    public String listarPrestamosVencidos() {
        ArrayList<Prestamo> vencidos = this.biblioteca.prestamosVencidos();
        StringBuilder texto = new StringBuilder();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        
        texto.append("========================================\n");
        texto.append("       PRÉSTAMOS VENCIDOS\n");
        texto.append("========================================\n\n");
        
        if (vencidos.isEmpty()) {
            texto.append("No hay préstamos vencidos al día de la fecha.\n");
        } else {
            texto.append("Total de préstamos vencidos: ").append(vencidos.size()).append("\n\n");
            
            int contador = 1;
            for (Prestamo prestamo : vencidos) {
                texto.append("--- Préstamo #").append(contador).append(" ---\n");
                texto.append(prestamo.toString()).append("\n");
                
                // Calcular y mostrar días de atraso
                Calendar fechaVencimiento = (Calendar) prestamo.getFechaRetiro().clone();
                fechaVencimiento.add(Calendar.DAY_OF_YEAR, prestamo.getSocio().getDiasPrestamos());
                
                Calendar hoy = Calendar.getInstance();
                long diferencia = hoy.getTimeInMillis() - fechaVencimiento.getTimeInMillis();
                long diasAtraso = diferencia / (1000 * 60 * 60 * 24);
                
                texto.append("Vencimiento: ").append(fecha.format(fechaVencimiento.getTime())).append("\n");
                texto.append("Días de atraso: ").append(diasAtraso).append("\n");
                texto.append("\n");
                
                contador++;
            }
        }
        
        texto.append("========================================\n");
        return texto.toString();
    }
    
    
    /**
     * Obtenemos cantidad de libros registrados para ventana principal
     */
    public int obtenerCantidadLibrosRegistrados(){
        return this.biblioteca.getLibros().size();
    }
    
        
    /**
     * Busca libro por titulo para ver si esta disponible para prestar
     */
    public Libro buscarLibroPorTitulo(String titulo) {
        // Obtenemos la lista maestra de libros de la capa de Negocio.
        ArrayList<Libro> libros = this.biblioteca.getLibros(); 
        if (libros == null) {
            return null; // No hay inventario.
        }
        // Iteramos sobre los objetos Libro para encontrar la coincidencia por título
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo.trim()) && !libro.prestado()) { 
                return libro; // Devuelve el objeto Libro encontrado
            }
        }
        return null;
    }
    
    /**
     * Obtiene la lista de TODOS los libros que están actualmente prestados.
     * @return ArrayList con los datos formateados para la tabla [Nro, Título, Estado]
     */
    public ArrayList<String[]> obtenerLibrosPrestados() {
        ArrayList<String[]> librosPrestados = new ArrayList<>();
        ArrayList<String[]> todosLosLibros = listadoDeLibros();
        int contador = 1;
        // Filtrar solo los que están prestados
        for (String[] libro : todosLosLibros) {
            // libro[2] contiene el estado: "PRESTADO 🚫" o "DISPONIBLE ✅"
            if (libro[2].contains("PRESTADO")) {
                // Crear nueva fila con número correlativo
                librosPrestados.add(new String[]{
                    String.valueOf(contador),
                    libro[1], // Título
                    libro[2]  // Estado
                });
                contador++;
            }
        }
        return librosPrestados;
    }
    
    /**
     * Pasamos el String a un arrayList<String[]> de los socios para mostrar en una tabla 
     */
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
    
    
    
    
    private String formatearFecha(Calendar fecha) {
        if (fecha == null) {
            return "No devolvió hasta la fecha";
        }
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha.getTime());
    }
    
    /**
     * Llama al String obligatorio y extrae únicamente la sección de conteo y resumen.
     * @return String con solo los conteos de Estudiantes y Docentes.
     */
    public String obtenerResumenConteo() {
        String listaCompleta = this.biblioteca.listaDeSocios();
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
    private ArrayList<String[]> aplicarFiltroTabla(ArrayList<String[]> datosCompletos, String tipoFiltro) {
        if (tipoFiltro.equals("Todos")) {
            return datosCompletos;
        }
        ArrayList<String[]> filtrados = new ArrayList<>();
        
        for (String[] fila : datosCompletos) {
            if (fila[2].equals(tipoFiltro)) { // fila[2] es el String del tipo de socio
                filtrados.add(fila);
            }
        }
        return filtrados;
    }
    
    /**
     * Verifica si existe al menos un libro con ese título, aunque no esté disponible.
     * Útil para diferenciar entre "libro no existe" vs "libro existe pero prestado".
     * @param titulo Título a buscar
     * @return true si existe al menos un libro con ese título, false si no existe
     */
    public boolean existeLibroPeroNoDisponible(String titulo) {
        ArrayList<Libro> libros = this.biblioteca.getLibros();
        if (libros == null) {
            return false;
        }
        
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return true; // Existe al menos uno
            }
        }
        
        return false; // No existe ninguno con ese título
    }
    /**
     * Obtiene los detalles del libro en la posición específica del ArrayList.
     * Esto asegura que se obtenga el libro EXACTO que el usuario seleccionó en la tabla.
     * @param indice Índice del libro en el ArrayList (corresponde a la fila de la tabla)
     * @return String con los detalles formateados del libro
     */
    public String obtenerDetallesLibroPorIndice(int indice) {
        try {
            ArrayList<Libro> libros = this.biblioteca.getLibros();
            
            if (libros == null || indice < 0 || indice >= libros.size()) {
                return "❌ Error: No se pudo obtener el libro seleccionado";
            }
            
            // 👇 Obtener el libro EXACTO en esa posición
            Libro libro = libros.get(indice);
            
            // 👇 Reutilizar tu método existente para formatear los detalles
            return obtenerDetallesLibro(libro);
            
        } catch (Exception e) {
            return "❌ Error al obtener detalles: " + e.getMessage();
        }
    }
    
    /**
     * Busca un libro prestado por su título.
     * SOLO devuelve libros que SÍ están prestados.
     * @param titulo Título del libro a buscar
     * @return El primer libro prestado con ese título, o null si no hay ninguno prestado
     */
    public Libro buscarLibroPrestadoPorTitulo(String titulo) {
        ArrayList<Libro> libros = this.biblioteca.getLibros();
        if (libros == null) {
            return null;
        }
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo.trim()) && libro.prestado()) {
                return libro; // Devuelve solo si SÍ está prestado
            }
        }
        return null;
    }
    
    /*
       ----- Metodos auxiliares para mostrar los datos dentro de la interfaz ----
    */
   
   //El método main inicializa todo
    public static void main(String[] args){
        // Creamos la instancia del controlador (que a su vez carga la persistencia)
        final GestionBiblioteca controlador = new GestionBiblioteca(); // 👈 Instancia del controlador

        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run(){
               // Pasamos la instancia 'controlador' al GUI
               new VentanaPrincipal(controlador); 
           }
        });
    }
}