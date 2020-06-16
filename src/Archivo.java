
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Alondra Sánchez Molina
//1er Parcial Modelo de Redes
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.

public class Archivo {
    
    public ArrayList<String> leerArchivo() throws FileNotFoundException, IOException {
        String cadena;
        ArrayList<String> lineas = new ArrayList<>();
        
        BufferedReader bf = new BufferedReader(new FileReader("src/datos.txt"));
        while ( ( cadena = bf.readLine() ) != null ) {
            lineas.add(cadena);
            }
        bf.close();
        return lineas;
    }
}
