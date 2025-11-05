package Persistencia;

import java.io.*;
import Biblioteca.Biblioteca;

public class Persistencia {
    
    // Ruta del archivo binario
    private static final String NOMBRE_ARCHIVO = "datos_biblioteca.dat";

    /**
     * Guarda el objeto Biblioteca en un archivo binario.
     */
    public static void guardar(Biblioteca p_biblioteca) {
        try {
            
            FileOutputStream fileOut = new FileOutputStream(NOMBRE_ARCHIVO);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            
            objectOut.writeObject(p_biblioteca); // Serializa el objeto Biblioteca
            
            objectOut.close();
            fileOut.close();
            System.out.println("Biblioteca guardada en " + NOMBRE_ARCHIVO);
            
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Error al guardar la Biblioteca.");
        }
    }

    /**
     * Carga el objeto Biblioteca desde un archivo binario.
     */
    public static Biblioteca cargar() {
        Biblioteca biblioteca = null;
        try {
            FileInputStream fileIn = new FileInputStream(NOMBRE_ARCHIVO);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            // Deserializa y realiza el casting
            biblioteca = (Biblioteca) objectIn.readObject(); 
            
            objectIn.close();
            fileIn.close();
            System.out.println("Biblioteca cargada desde " + NOMBRE_ARCHIVO);
            
        } catch (FileNotFoundException f) {
            System.out.println("Archivo de datos no encontrado. Se crea una nueva Biblioteca.");
        } catch (IOException  | ClassNotFoundException e) {
            System.out.println("Error crítico al cargar datos. Inicializando Biblioteca nueva.");
            e.printStackTrace();
        }
        
        if (biblioteca == null) {
            biblioteca = new Biblioteca("Biblioteca App");
        }
        
        return biblioteca;
    }
}