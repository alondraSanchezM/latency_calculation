
import java.io.IOException;
import java.util.ArrayList;

//Alondra Sánchez Molina
//1er Parcial Modelo de Redes - BUAP
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.

public class ManejadorRed {
    public static void main(String[] args) throws IOException {
        
        String[] parts, totales, extremos;
        Archivo archivo = new Archivo();
        ArrayList<String> texto = archivo.leerArchivo();
        
        totales = texto.get(0).split(",");
        
        Red red = new Red(Integer.parseInt(totales[0]),Integer.parseInt(totales[1]));
        
        for (int i=1; i<=Integer.parseInt(totales[0]); i++) {
            parts = texto.get(i).split(",");
            red.agregarNodo(Integer.parseInt(parts[0]),Double.parseDouble(parts[1]));
        }
        
        for (int i=(Integer.parseInt(totales[0])+1); i<(Integer.parseInt(totales[0])+1)+Integer.parseInt(totales[1]); i++) {
            parts = texto.get(i).split(",");
            red.agregarEnlace(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]));
        }

        double tamTransferencia = Float.parseFloat(texto.get(texto.size()-2));
        
        extremos = texto.get(texto.size()-1).split(",");
        red.calcularAllCaminos(Integer.parseInt(extremos[0]),Integer.parseInt(extremos[1]));
        
        red.mejorLatencia();
        red.mostrarTiempoTotalTransferencia(tamTransferencia);
        
    }
}
