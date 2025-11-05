package Biblioteca;


import java.util.*;
import java.io.*;
/**
 * Clase abstracta que representa a un socio de la biblioteca.
 * Un socio puede ser un Estudiante o un Docente.
 * Contiene la informacion basica y comun a todos los socios.
 * 
 * @author Grupo 1.1 Acuña Alejo
 * @version 1.0
 */
public abstract class Socio implements Serializable
{
    // Atributos
    /** Numero de documento del socio */
    public int dniSocio;
    /** Nombre completo del socio */
    private String nombre;
    /** Cantidad de dias que el socio puede tener un prestamo */
    private int diasPrestamo;
    /** Lista de prestamos asociados al socio */
    private ArrayList<Prestamo> prestamos;
    
    /**
     * Constructor que inicializa un socio con una lista de prestamos.
     * 
     * @param p_dniSocio Numero de documento del socio
     * @param p_nombre Nombre del socio
     * @param p_diasPrestamo Dias de prestamo asignados
     * @param p_prestamos Lista de prestamos del socio
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos){
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    /**
     * Constructor que inicializa un socio con un unico prestamo.
     * 
     * @param p_dniSocio Numero de documento del socio
     * @param p_nombre Nombre del socio
     * @param p_diasPrestamo Dias de prestamo asignados
     * @param p_prestamo Prestamo inicial del socio
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo){
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
    }
    
    
    /** Asigna el DNI del socio */
    private void setDniSocio(int p_dniSocio){
        this.dniSocio = p_dniSocio;
    }
    
    /** Asigna el nombre del socio */
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    /** Asigna los dias de prestamo del socio */
    protected void setDiasPrestamo(int p_diasPrestamo){
        this.diasPrestamo = p_diasPrestamo;
    }
    
    /** Asigna la lista de prestamos del socio */
    private void setPrestamos(ArrayList<Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    // Getters
    
    /** Devuelve el DNI del socio */
    public int getDniSocio(){
        return this.dniSocio;
    }
    
    /** Devuelve el nombre del socio */
    public String getNombre(){
        return this.nombre;
    }
    
    /** Devuelve la cantidad de dias de prestamo del socio */
    public int getDiasPrestamos(){
        return this.diasPrestamo;
    }
    
    /** Devuelve la lista de prestamos del socio */
    public ArrayList<Prestamo> getPrestamos(){
        return this.prestamos;
    }
    
    // Otros metodos
    
    /**
     * Agrega un prestamo a la lista del socio.
     * 
     * @param p_prestamo Prestamo a agregar
     * @return true si se agrego correctamente
     */
    public boolean agregarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    
    /**
     * Quita un prestamo de la lista del socio.
     * 
     * @param p_prestamo Prestamo a quitar
     * @return true si se elimino correctamente
     */
    public boolean quitarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().remove(p_prestamo);
    }
    
    /**
     * Calcula la cantidad de libros que el socio tiene actualmente prestados.
     * 
     * @return Cantidad de prestamos sin devolver
     */
    public int cantLibrosPrestados(){
        int cant = 0;
        for(Prestamo prestamo: this.getPrestamos()){
            if(prestamo.getFechaDevolucion() == null){
                cant++;
            }
        }
        return cant;
    }
    
    /**
     * Devuelve una representacion en texto del socio.
     * 
     * @return Cadena con la informacion del socio
     */
    public String toString(){
        return "DNI: " + this.getDniSocio() + " || " + this.getNombre() + " (" 
        + this.soyDeLaClase()+ ") || Libros Prestados: " + this.cantLibrosPrestados();
    }
    
    /**
     * Metodo abstracto que indica si el socio puede realizar un nuevo prestamo.
     * Debe ser implementado por las subclases Docente y Estudiante.
     * 
     * @return true si el socio puede pedir un libro
     */
    public abstract boolean puedePedir();
    
    /**
     * Metodo abstracto que devuelve el tipo de socio (Docente o Estudiante).
     * 
     * @return Tipo de socio en formato String
     */
    public abstract String soyDeLaClase();
}
