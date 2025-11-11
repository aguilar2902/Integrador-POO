package biblioteca;

import java.util.*;
import java.io.*;
/**
 * Clase que representa un libro dentro de la biblioteca.
 * Contiene informacion basica como titulo, edicion, editorial y anio,
 * ademas de la lista de prestamos asociados a ese libro.
 * 
 * @author Grupo 1.1 Acuña Bruno
 * @version 1.0
 */
public class Libro implements Serializable
{
    /**Atributos de la clase*/
    /** Titulo del libro */
    private String titulo;
    /** Numero de edicion del libro */
    private int edicion;
    /** Editorial del libro */
    private String editorial;
    /** Anio de publicacion del libro */
    private int anio;
    /** Lista de prestamos realizados sobre el libro */
    private ArrayList<Prestamo> prestamos;
    
    /**
     * Constructor que crea un libro con una lista de prestamos existente.
     * 
     * @param p_titulo Titulo del libro
     * @param p_edicion Numero de edicion
     * @param p_editorial Nombre de la editorial
     * @param p_anio Anio de publicacion
     * @param p_prestamos Lista de prestamos asociados
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, ArrayList<Prestamo> p_prestamos){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(p_prestamos);
    }
    
    /**
     * Constructor que crea un libro sin prestamos iniciales.
     * 
     * @param p_titulo Titulo del libro
     * @param p_edicion Numero de edicion
     * @param p_editorial Nombre de la editorial
     * @param p_anio Anio de publicacion
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setPrestamos(new ArrayList<Prestamo>());
    }
    
    // Setters
    
    /** Asigna el titulo del libro */
    public void setTitulo(String p_titulo) {
        this.titulo = p_titulo;
    }
    
    /** Asigna la edicion del libro */
    public void setEdicion(int p_edicion) {
        this.edicion = p_edicion;
    }
    
    /** Asigna la editorial del libro */
    public void setEditorial(String p_editorial) {
        this.editorial = p_editorial;
    }
    
    /** Asigna el anio del libro */
    public void setAnio(int p_anio) {
        this.anio = p_anio;
    }
    
    /** Asigna la lista de prestamos del libro */
    public void setPrestamos(ArrayList<Prestamo> p_prestamos) {
        this.prestamos = p_prestamos;
    }
    
    // Getters
    
    /** Devuelve el titulo del libro */
    public String getTitulo() {
        return this.titulo;
    }
    
    /** Devuelve el numero de edicion del libro */
    public int getEdicion() {
        return this.edicion;
    }
    
    /** Devuelve la editorial del libro */
    public String getEditorial() {
        return this.editorial;
    }
    
    /** Devuelve el anio de publicacion del libro */
    public int getAnio() {
        return this.anio;
    }
    
    /** Devuelve la lista de prestamos asociados al libro */
    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos;
    }
    
    // Otros metodos
    
    /**
     * Agrega un prestamo a la lista de prestamos del libro.
     * 
     * @param p_prestamo Prestamo a agregar
     * @return true si se agrego correctamente
     */
    public boolean agregarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    
    /**
     * Quita un prestamo de la lista de prestamos del libro.
     * 
     * @param p_prestamo Prestamo a quitar
     * @return true si se elimino correctamente
     */
    public boolean quitarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().remove(p_prestamo);
    }
    
    /**
     * Indica si el libro se encuentra actualmente prestado.
     * 
     * @return true si el ultimo prestamo no tiene fecha de devolucion, false en caso contrario
     */
    public boolean prestado(){
        if(this.ultimoPrestamo() == null){
            return false;
        } else {
            return this.ultimoPrestamo().getFechaDevolucion() == null;
        }
    }
    
    /**
     * Devuelve el ultimo prestamo realizado sobre el libro.
     * 
     * @return Ultimo objeto Prestamo, o null si no hay prestamos
     */
    public Prestamo ultimoPrestamo(){
        if(this.getPrestamos().isEmpty()){
            return null;
        } else {
            return this.getPrestamos().get(this.getPrestamos().size() - 1);
        }
    }
    
    /**
     * Devuelve una representacion en texto del libro.
     * 
     * @return Cadena con el titulo del libro
     */
    public String toString(){
        return "Titulo: " + this.getTitulo();
    }
}