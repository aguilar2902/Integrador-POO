package biblioteca;

import java.util.*;
import java.io.*;
/**
 * La clase Biblioteca administra los préstamos de sus libros y gestiona las colecciones
 * de Socios, Libros y Prestamos del sistema.
 * Es la clase responsable de aplicar las reglas de negocio (limites de prestamo, vencimientos, etc.).
 * 
 * @author Grupo 1.1 Aguilar-Bengochea-Espindola
 * @version 1
 */
public class Biblioteca implements Serializable
{   
    /**
     * Atributos de la clase
     */
    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;

    /**
     * Constructor de la clase Biblioteca
     * Inicializa la Biblioteca con un nombre y una lista inicial de Libros y otra de socios.
     * @param p_nombre nombre a asignar a la biblioteca
     * @param p_libros lista de libros
     * @param p_socios lista de socios
     */
    public Biblioteca(String p_nombre,ArrayList<Libro> p_libros, ArrayList<Socio> p_socios){
        this.setNombre(p_nombre);
        this.setLibros(p_libros);
        this.setSocios(p_socios);
    }
    
    /**
     * Constructor de la clase Biblioteca
     * Inicializa la Biblioteca con un nombre y todas las colecciones vacías
     * @param p_nombre nombre a asignar a la biblioteca
     */
    public Biblioteca(String p_nombre){
        this.setNombre(p_nombre);
        this.setLibros(new ArrayList<Libro>());
        this.setSocios(new ArrayList<Socio>());
    }
    //Mutadores
    /**
     * Método privado, utilizado internamente para asignar el nombre a la biblioteca.
     * (Generalmente llamado solo desde el constructor). 
     * @param  p_nombre nombre de la biblioteca
     */
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    /**
     * Método privado, utilizado  para asignar la lista completa de libros a la biblioteca.
     * Si el parámetro recibido es {@code null}, se inicializa una nueva lista vacía
     * para evitar referencias nulas en el sistema.
     * @param p_libros La colección de objetos Libro a asignar a la biblioteca.Si es {@code null}, 
     * se crea una nueva lista vacía.
     */
    private void setLibros(ArrayList<Libro> p_libros){
        if(p_libros == null){
            this.libros = new ArrayList<Libro>();
        } else {
            this.libros = p_libros;
        }
    }
    
    /**
     * Método privado, utilizado  para asignar la lista completa de socios a la biblioteca.
     * Si el parámetro recibido es {@code null}, se inicializa una nueva lista vacía
     * para evitar referencias nulas y posibles errores en tiempo de ejecución.
     * @param p_socios La colección de objetos {@code Socio} a asignar a la biblioteca.
     * Si es {@code null}, se crea una nueva lista vacía.
     */
    private void setSocios(ArrayList<Socio> p_socios){
         if(p_socios == null){
            this.socios = new ArrayList<Socio>();
        } else {
            this.socios = p_socios;
        }
    }
    //Observadores
    /**
     * Método publico, utilizado para obtener el nombre de la biblioteca.
     * 
     * @return un String con el nombre actual de la biblioteca.
     */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
     * Método publico, utilizado para obtener la colección de libros de la biblioteca.
     * 
     * @return Retorna un ArrayList con todos los objetos Libro almacenados.
     */
    public ArrayList<Libro> getLibros(){
       return this.libros;
    }
    
    /**
     * Método publico, utilizado para obtener la colección de socios de la biblioteca.
     * 
     * @return Retorna un ArrayList con todos los objetos Socio almacenados en la colección.
     */
    public ArrayList<Socio> getSocios(){
        return this.socios;
    } 
    //Otros Métodos
    /**
     * Este método agrega un libro a la coleccion de libros.
     * @param p_libro libro a agregar.
     * @return un booleano que confirma la operación.
     */
    public boolean agregarLibro(Libro p_libro){
        return this.getLibros().add(p_libro);
    }
    
    /**
     * Este método quita un libro de la colección de libros.
     * @param p_libro libro a quitar.
     * @return un booleano que confirma la operación.
     */
    public boolean quitarLibro(Libro p_libro){
        return this.getLibros().remove(p_libro);
    }
    
    /**
     * Este método agrega un socio a la colección.
     * @param p_socio socio a agregar.
     * @return un booleano confirmando la operación.
     */
    public boolean agregarSocio(Socio p_socio){
        return this.getSocios().add(p_socio);
    }
    
    /**
     * Este método aquita un socio a la coleccion de Socio, que es pasado por parametro
     * @param p_socio socio a quitar.
     * @return booleano confirmando la operación.
     */
    public boolean quitarSocio(Socio p_socio){
        return this.getSocios().remove(p_socio);
    }
    
    /**
     * Crea una nueva instancia de Libro y la añade a la colección de la biblioteca.
     * Este método está diseñado para registrar ejemplares individuales; 
     * por lo tanto, no se incluye validación de duplicados de título/edición.
     *
     * @param p_titulo El título del libro.
     * @param p_edicion El número de la edición.
     * @param p_editorial La editorial del libro.
     * @param p_anio El año de publicación.
     */

    public void nuevoLibro( String p_titulo,int p_edicion,String p_editorial,int  p_anio){
        Libro libro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        if(this.agregarLibro(libro)){
            System.out.println("¡Libro agregado a la biblioteca!");
        } else {
            System.out.println("No se ha podido agregar el Libro");
        }
    }
    
    /**
     * Crea una nueva instancia de Estudiante y la añade a la colección de socios de la biblioteca.
     *
     * @param p_dniSocio El numero de DNI del nuevo socio-Estudiante.
     * @param p_nombre El nombre del nuevo socio-Estudiante.
     * @param p_carrera El nombre de la carrera del nuevo socio-Estudiante.
     * @throws IllegalArgumentException cuando se intenta agregar un socio-Estudiante con un dni que ya está registrado.
     */
    public void nuevoSocioEstudiante(int p_dniSocio,String p_nombre,String p_carrera) throws IllegalArgumentException{
        for (Socio socio : this.getSocios()) {
            if (socio.getDniSocio() == p_dniSocio) {
                throw new IllegalArgumentException("ERROR: Ya hay un socio con el DNI " + p_dniSocio + " registrado.");
            }
        }
        Estudiante estudiante = new Estudiante (p_dniSocio,p_nombre, p_carrera);
        this.agregarSocio(estudiante);
    }
    
    /**
     * Crea una nueva instancia de Docente y la añade a la colección de la biblioteca.
     * Este método está diseñado para registrar socios individuales; 
     *
     * @param p_dniSocio El numero de DNI del nuevo socio-Docente.
     * @param p_nombre El nombre del nuevos socio-Docente.
     * @param p_area El nombre del area al que pertenece el nuevo socio-Docente.
     * @throws IllegalArgumentException cuando se intenta agregar un socio-Docente con un dni que ya está registrado.
     */
    public void nuevoSocioDocente(int p_dniSocio,String p_nombre,String p_area) throws IllegalArgumentException{
        for (Socio socio : this.getSocios()) {
            if (socio.getDniSocio() == p_dniSocio) {
                throw new IllegalArgumentException("ERROR: Ya hay un socio con el DNI " + p_dniSocio + " registrado.");
            }
        }
        Docente docente = new Docente (p_dniSocio,p_nombre,p_area);
        this.agregarSocio(docente);
    }
    
    /**
     * El método verifica que el libro este en existencia, luego corrobora que el socio este en condiciones de retirar el libro,
     * por ultimo realiza el prestamo y lo asigna al socio y al libro.
     *
     * @param p_fechaRetiro Indica la fecha en que se retira el libro
     * @param p_socio Socio que solicita el préstamo. 
     * @param p_libro Libro requerido.
     * @return true si el préstamo se realizó.
     * @throws IllegalArgumentException cuando el libro no se encuentra disponible o el socio no tiene permitido pedir alguno.
     */
    public boolean prestarLibro (Calendar p_fechaRetiro, Socio p_socio,Libro p_libro) throws IllegalArgumentException{
        if (p_libro.prestado()) { 
            throw new IllegalArgumentException("El libro '" + p_libro.getTitulo() + "' ya se encuentra prestado.");
        }
        
        if (!p_socio.puedePedir()) { 
            throw new IllegalArgumentException("El Socio " + p_socio.getNombre() + " no cumple las condiciones para pedir.");
        }
        
        Prestamo nuevoPrestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
        p_socio.agregarPrestamo(nuevoPrestamo);
        p_libro.agregarPrestamo(nuevoPrestamo);
        return true;
    }
    /**
     * Este método se encarga de asignar la fecha de devolución del préstamo con la fecha actual.
     * Si el libro no se encuentra prestado lanza una excepción (LibroNoPrestadoException),
     * @param p_libro Libro que se desea devolver
     * @throws LibroNoPrestadoException Si se intenta devolver un libro que no está prestado.
     */
    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException{
         if(p_libro.prestado()){
            Calendar fechaHoy = Calendar.getInstance();
            p_libro.ultimoPrestamo().registrarFechaDevolucion(fechaHoy);
            System.out.println("¡Devolución del libro " + p_libro.getTitulo() + " registrada!");
        } else {
            throw new LibroNoPrestadoException("El libro " + p_libro.getTitulo() + " no se puede devolver ya que se encuentra en la biblioteca");
        }   
    }
    
    /**
     * Este método devuelve la cantidad de socios del tipo pasado como parámetro.
     * 
     * @param p_objeto tipo de socio del cual se quiere conocer la cantidad
     * @return valor entero que representa la cantidad de socios del tipo solicitado por parametro
     */
    public int cantidadDeSociosPorTipo(String p_objeto){
        int cantSocios = 0;
        for (Socio socios: this.getSocios()){
            if (socios.soyDeLaClase().equalsIgnoreCase(p_objeto)){
                cantSocios ++;
            }
        }
        return cantSocios;
    }
    
    /**
     * Este método devuelve una colección con los préstamos vencidos al día de la fecha.
     * 
     * @return una lista (ArrayList) de objetos {@code Prestamo} que están vencidos,
     * al momento de la consulta. Si no hay préstamos vencidos, la lista se devuelve vacía.
     */
    public ArrayList<Prestamo> prestamosVencidos(){
        ArrayList<Prestamo> prestamosVencidos = new ArrayList<Prestamo>();
        Calendar fechaActual = Calendar.getInstance();
        for (Libro libros : this.getLibros()) {/// el recorrio socio for(Socio socio : this.getSocios())
            for (Prestamo prestamo : libros.getPrestamos()) {//for(Prestamo prestamo : socio.getPrestamos()
                 if (prestamo.vencido(fechaActual)) {       
                     prestamosVencidos.add(prestamo); 
                 }
            }
        }
        return prestamosVencidos;
    }
    
    /**
     * Este método devuelve un colección con los docentes responsables.
     * @return una lista (ArrayList) de objetos {@code Docente} que son responsables ,
     * al momento de la consulta. Si no hay docetes responsables, la lista se devuelve vacía.
     */
    public ArrayList<Docente> docentesResponsables(){
        ArrayList<Docente> responsables = new ArrayList<Docente>();
        for (Socio socio : this.getSocios()) {
            if (socio instanceof Docente docente) {
                //Anulado debido a que la variable docente ya se genera arriba
                //Docente docente = (Docente) socios; 
                if (docente.esResponsable()) {
                    responsables.add(docente);
                }
            }
        }
        return responsables;
    }
    
    /**
     * Este método obtiene el nombre del Socio que tiene el libro con el título ingresado.
     * @param p_libro titulo del libro del que se desea consultar
     * @return el nombre del socio que tiene el libro en su poder
     * throws LibroNoPrestadoException Si el libro no está actualmente prestado.
     */
    public String quienTieneElLibro(Libro p_libro)throws LibroNoPrestadoException{
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException("El libro '" + p_libro.getTitulo() + "' se encuentra en la biblioteca.");
        }
        Prestamo ultimo = p_libro.ultimoPrestamo();
        Socio socio = ultimo.getSocio();
        return socio.getNombre();
    }
    
    /**
     * Este método devuelve un String según el siguiente formato:
     * "D.N.I.: -dni- || -nombre y apellido- (-tipo-) || Libros Prestados: -cant. prést. actuales-"
     * @return lista de socios con sus datos básicos.
     */
    public String listaDeSocios(){
        String listaSocios = "";
        int contador = 1;
        for(Socio socio : this.getSocios()){
            listaSocios += contador + ")" + socio.toString() + "\n";
            contador++;
        }
        listaSocios += "*".repeat(38);
        listaSocios += "\nCantidad de Socios del tipo Estudiante: " + this.cantidadDeSociosPorTipo("Estudiante");
        listaSocios += "\nCantidad de Socios del tipo Docente: " + this.cantidadDeSociosPorTipo("Docente");
        listaSocios += "\n" + "*".repeat(38) + "\n";
        return listaSocios;
    }
    
    /**
     * Este método devuelve el Socio que tiene el dni pasado como parámetro, o null si no lo encuentra.
     * 
     * @return un objeto del tipo Socio o null en su defecto.
     */
    public Socio buscarSocio(int p_dni){
        for(Socio socio: this.getSocios()){
           if(p_dni == socio.getDniSocio()){ //VER como esta escrito este metodo en las sub Clases
             return socio; 
           }
        }
        return null;
    }
    
    /**
     * Este método devuelve un String según formato "Titulo: -titulo- || Prestado: (-Si|No-)"
     * @return lista de titulos de libros de la biblioteca y si están prestados o no, como cadena de texto.
     */
    public String listaDeLibros(){
        String resultado = "";
        int contador = 1;
        for(Libro libro:this.getLibros()){
            String prestado = libro.prestado() ? "Si" : "No" ;
            resultado += contador + ") " + "Titulo: " + libro.getTitulo() + " || Prestado: (" + prestado + ")\n";
            contador++;
        }
        return resultado;
    }
    
    /**
     * Este método devuelve un String con la lista de los títulos con los que cuenta la Biblioteca.
     * Coda titulo se devuelve en un linea separada
     * @return cadena de texto con todos los títulos de la biblioteca.
     */
    public String listaDeTitulos(){
        HashSet<String> listadoTitulos = new HashSet<String>();
        String resultado = "";
        int contador = 1;
        for(Libro libro : this.getLibros()){
            listadoTitulos.add(libro.getTitulo());
        }
        for(String titulo : listadoTitulos){
            resultado += contador + ") " + titulo + "\n";
            contador++;
        }
        return resultado;
    }
    
    /**
     * Este método devuelve un String según formato 3:
     * * D.N.I.: -dni- || -nombre y apellido- (-tipo-) || Libros Prestados: -cant. prést. actuales-
     * @return lista de String con los docentes responsables
     */
    public String listaDeDocentesResponsables(){
        String resultado = "\n\tLista de Docentes Responsables\n";
        if(!this.docentesResponsables().isEmpty()){
            for(Docente docente : this.docentesResponsables()){
                resultado += "* " + docente.toString() + "\n"; 
            }
        }else{
            resultado += "* No hay docentes responsables registrados. *\n";
        }
        return resultado;
    }
}