
import java.util.List;


//Alondra Sánchez Molina
//1er Parcial Modelo de Redes
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.

public class Camino {
    private List<Integer> camino;
    private double latencia;

    public Camino(List<Integer> camino, double latencia) {
        this.camino = camino;
        this.latencia = latencia;
    }

    public List<Integer> getCamino() {
        return camino;
    }

    public double getLatencia() {
        return latencia;
    }

}
