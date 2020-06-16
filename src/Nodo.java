//Alondra Sánchez Molina
//1er Parcial Modelo de Redes
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.

public class Nodo {
    private int noNodo;
    private double TC;

    public Nodo(int noNodo, double TC) {
        this.noNodo = noNodo;
        this.TC = TC;
    }

    public int getNoNodo() {
        return noNodo;
    }

    public double getTC() {
        return TC;
    }

    public void setTC(float TC) {
        this.TC = TC;
    }

}
