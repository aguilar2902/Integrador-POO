package Biblioteca;


import java.util.*;
import java.io.*;
/**
 * Clase derivada de Socio. Representa a un docente de la biblioteca.
 * 
 * @author Grupo 1.1 Sosa Alejandro
 * @Clase Docente - TP7
 * @version 1.0 
 */
public class Docente extends Socio implements Serializable
{
    /**
     * Atributos de la clase Docente
     */
    private String area;

    /**
     * Constructor con parámetros, instancia un objeto Docente
     * @param p_dniSocio D.N.I. del docente
     * @param p_nombre Nombre completo del docente
     * @param p_area Área a la que pertenece
     */
    public Docente(int p_dniSocio, String p_nombre, String p_area)
    {
        super(p_dniSocio, p_nombre, 5); // Todos los docentes comienzan con 5 días de préstamo
        this.setArea(p_area);
    }

    //setter
    private void setArea(String p_area){
        this.area = p_area;
    }
    
    //getter
    public String getArea(){
        return this.area;
    }



    /**
     * Método que indica si el Docente nunca tuvo ni tiene préstamos vencidos
     * @return true si no hay préstamos vencidos
     */
    public boolean esResponsable()
    {
        Calendar fechaActual = Calendar.getInstance();
        
        for(Prestamo prestamo: this.getPrestamos()){
            if(prestamo.getFechaDevolucion() != null){
                //quiere decir que ya devolvió
                if (prestamo.vencido(prestamo.getFechaDevolucion())) {
                    return false; 
                }
            }else if(prestamo.vencido(fechaActual)){
                //aun no devolvio, pero controlar si no se venció
                return false;
            }
        }
        return true;
    }

    /**
     * Método que adiciona días de préstamo al docente
     * @param p_dias Días a adicionar al plazo de préstamo
     */
    public void cambiarDiasDePrestamo(int p_dias)
    {
        this.setDiasPrestamo(this.getDiasPrestamos() + p_dias);
    }

    /**
     * Método que indica si el docente puede pedir un libro
     * @return true si no tiene préstamos vencidos
     * @Override
     */
    
    public boolean puedePedir()
    {
        for(Prestamo p : this.getPrestamos())
        {
            Calendar hoy = Calendar.getInstance();
            if(p.vencido(hoy))
                return false;
        }
        return true;
    }

    /**
     * Método que devuelve el tipo de socio
     * @return "Docente"
     * @Override
     */
    
    public String soyDeLaClase()
    {
        return "Docente";
    }
}
