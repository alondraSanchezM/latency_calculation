//Alondra Sánchez Molina
//1er Parcial Modelo de Redes
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.

public class Enlace {
    private int noOrigen;
    private int noDestino;
    private int tasaTransfer;
    private int distancia;
    private int DC;
    private int DU;

    public Enlace(int noOrigen, int noDestino, int velEnlace, int distancia, int DC, int DU) {
        this.noOrigen = noOrigen;
        this.noDestino = noDestino;
        this.tasaTransfer = velEnlace;
        this.distancia = distancia;
        this.DC = DC;
        this.DU = DU;
    }

    public int getNoOrigen() {
        return noOrigen;
    }

    public int getNoDestino() {
        return noDestino;
    }

    public int getTasaTransfer() {
        return tasaTransfer;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getDC() {
        return DC;
    }

    public int getDU() {
        return DU;
    }

}
