package Biblioteca;


import java.io.*;
import java.util.*;

/**
 * Clase derivada de Socio. Representa a un estudiante de la biblioteca.
 * 
 * @author Grupo 1.1 Sosa Alejandro
 * @Clase Estudiante - TP7
 * 
 */
public class Estudiante extends Socio implements Serializable
{
    /**
     * Atributos de la clase Estudiante
     */
    private String carrera;

    /**
     * Constructor con parámetros, instancia un objeto Estudiante
     * @param p_dniSocio D.N.I. del estudiante
     * @param p_nombre Nombre completo del estudiante
     * @param p_carrera Carrera del estudiante
     */
    public Estudiante(int p_dniSocio, String p_nombre, String p_carrera)
    {
        super(p_dniSocio, p_nombre, 20); // Los estudiantes tienen 20 días de préstamo
        this.carrera = p_carrera;
    }

    //setter
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }
    
    //getter
    public String getCarrera(){
        return this.carrera;
    }
    
    /**
     * Método que indica si el estudiante puede pedir un libro
     * @return true si no tiene préstamos vencidos y no supera los 3 libros prestados
     * @Override
     */
    
    public boolean puedePedir()
    {
        Calendar fechaActual = Calendar.getInstance();
        
        if(this.cantLibrosPrestados() >= 3){
            return false;
        }
        
        for(Prestamo prestamo: this.getPrestamos()){
            if(prestamo.vencido(fechaActual)){
                return false;
            }
        }
        return true;
    }

    /**
     * Método que devuelve el tipo de socio
     * @return "Estudiante"
     * @Override
     */
    
    public String soyDeLaClase()
    {
        return "Estudiante";
    }
}