package Biblioteca;


import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Clase que representa un prestamo de la biblioteca.
 * Contiene la informacion de la fecha de retiro, devolucion,
 * el socio que lo realizo y el libro prestado.
 * 
 * @author Grupo 1.1 Acuña Alejo
 * @version 1.0
 */
public class Prestamo implements Serializable
{
    /**Atributos de la clase*/
    /** Fecha en la que se realizo el retiro del libro */
    private Calendar fechaRetiro;
    /** Fecha en la que se devolvio el libro (puede ser null si aun no se devolvio) */
    private Calendar fechaDevolucion;
    /** Socio que realizo el prestamo */
    private Socio socio;
    /** Libro que fue prestado */
    private Libro libro;
    
    /**
     * Constructor que crea un nuevo prestamo.
     * 
     * @param p_retiro Fecha de retiro del libro
     * @param p_socio Socio que realiza el prestamo
     * @param p_libro Libro que se presta
     */
    public Prestamo (Calendar p_retiro, Socio p_socio, Libro p_libro){
        this.setFechaRetiro(p_retiro);
        this.setFechaDevolucion(null);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }
    
    // Setters
    
    /** Asigna la fecha de retiro del libro */
    private void setFechaRetiro(Calendar p_retiro){
        this.fechaRetiro = p_retiro;
    }
    
    /** Asigna la fecha de devolucion del libro */
    private void setFechaDevolucion(Calendar p_devolucion){
        this.fechaDevolucion = p_devolucion;
    }
    
    /** Asigna el socio asociado al prestamo */
    private void setSocio(Socio p_socio){
        this.socio = p_socio;
    }
    
    /** Asigna el libro asociado al prestamo */
    private void setLibro(Libro p_libro){
        this.libro = p_libro;
    }
    
    // Getters
    
    /** Devuelve la fecha de retiro del libro */
    public Calendar getFechaRetiro(){
        return this.fechaRetiro;
    }
    
    /** Devuelve la fecha de devolucion del libro (puede ser null) */
    public Calendar getFechaDevolucion(){
        return this.fechaDevolucion;
    }
    
    /** Devuelve el socio que realizo el prestamo */
    public Socio getSocio(){
        return this.socio;
    }
    
    /** Devuelve el libro prestado */
    public Libro getLibro(){
        return this.libro;
    }
    
    // Otros metodos
    
    /**
     * Registra la fecha de devolucion del libro.
     * 
     * @param p_fecha Fecha en la que se devolvio el libro
     */
    public void registrarFechaDevolucion(Calendar p_fecha){
        this.setFechaDevolucion(p_fecha);
    }
    
    /**
     * Verifica si el prestamo esta vencido.
     * Un prestamo se considera vencido si la fecha actual es posterior
     * a la fecha de retiro mas los dias de prestamo asignados al socio.
     * 
     * @param p_fecha Fecha actual o de comparacion
     * @return true si el prestamo esta vencido, false en caso contrario
     */
    public boolean vencido(Calendar p_fecha){
        // Utilizamos clone para evitar modificar la fecha original de retiro
        Calendar fechaVencimiento = (Calendar) this.getFechaRetiro().clone();
        fechaVencimiento.add(Calendar.DAY_OF_YEAR, this.getSocio().getDiasPrestamos());
    
        // Si la fecha actual es posterior a la fecha de vencimiento, el prestamo esta vencido
        return p_fecha.after(fechaVencimiento);
    }
    
    /**
     * Devuelve una representacion en texto del prestamo.
     * Muestra las fechas de retiro y devolucion, el titulo del libro y el nombre del socio.
     * 
     * @return Cadena con la informacion formateada del prestamo
     */
    public String toString(){
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
        
        String retiro = fecha.format(this.getFechaRetiro().getTime());
        String devolucion;
        
        if(this.getFechaDevolucion() != null){
            devolucion = fecha.format(this.getFechaDevolucion().getTime());
        } else {
            devolucion = "pendiente";
        }
        
        return "Retiro: " + retiro + " - Devolucion: " + devolucion 
                + "\nLibro: " + this.getLibro().getTitulo()
                + "\nSocio: " + this.getSocio().getNombre();
    }
}