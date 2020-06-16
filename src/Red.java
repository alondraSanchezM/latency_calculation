
import java.util.ArrayList;
import java.util.List;

//Alondra Sánchez Molina
//1er Parcial Modelo de Redes
//Este programa obtiene el mejor tiempo y ruta para transferir un archivo de un origen a un destino en una red de computadora;
//tomando como base el concepto de latencia.


@SuppressWarnings("unchecked")
public class Red {
    private ArrayList <Nodo> nodos;
    private ArrayList <Enlace> enlaces;
    private ArrayList <Camino> caminos;
    private ArrayList<Integer>[] listaAdyacen;
    private ArrayList<Integer>[] transporte;
    
    private int totNodos;
    private int totEnlaces;
    private int tam1erPaquete;
    private double mejorLatencia;
   
    private final double velLuz = 300000;
    private final double converMetros = 0.001;
    private final double converMbps = 0.000008; 
    
    
    public Red(int totNodos, int totEnlaces) {
        this.totNodos = totNodos+1;
        this.totEnlaces = totEnlaces;
        
        nodos = new ArrayList();
        enlaces = new ArrayList();
        caminos = new ArrayList();
        
        iniListas();
    }
    
    private void iniListas() {
        listaAdyacen = new ArrayList[totNodos];
        for (int i = 0; i < totNodos; i++) 
            listaAdyacen[i] = new ArrayList<>();
        
        transporte = new ArrayList[totEnlaces];
        for (int j = 0; j < totEnlaces; j++) 
            transporte[j] = new ArrayList<>();
    }
    
    public void agregarNodo(int noNodo, double TC){
        Nodo n = new Nodo(noNodo,TC);
        nodos.add(n);
    }
    
    public void agregarEnlace(int noOrigen, int noDestino, int velEnlace, int distancia, int DC, int DU) {
        Enlace e = new Enlace(noOrigen, noDestino, velEnlace, distancia, DC, DU);
        Enlace ev = new Enlace(noDestino, noOrigen, velEnlace, distancia, DC, DU);
        enlaces.add(e);
        enlaces.add(ev);
        listaAdyacen[noOrigen].add(noDestino);
        listaAdyacen[noDestino].add(noOrigen);
    }

    public void calcularAllCaminos(int nodoInicial, int nodoFinal) {
        for(Nodo n : nodos){
            if(n.getNoNodo() == nodoInicial)
                n.setTC(0);
            if(n.getNoNodo() == nodoFinal)
                n.setTC(0);
        }
        boolean[] visitado = new boolean[totNodos];
        ArrayList<Integer> camino = new ArrayList<>();

        camino.add(nodoInicial);
        calcularCamino(nodoInicial, nodoFinal, visitado, camino);

    }

    private void calcularCamino(Integer nI, Integer nF, boolean[] visitado, List<Integer> cadCamino) {
        visitado[nI] = true;

        if (nI.equals(nF)) {
            calculoLatencia(cadCamino);
            visitado[nI] = false;
            return;
        }

        for (Integer i : listaAdyacen[nI]) {
            if (!visitado[i]) {
                cadCamino.add(i);
                calcularCamino(i, nF, visitado, cadCamino);
                cadCamino.remove(i);
            }
        }
        
        visitado[nI] = false;
    }
    
    private void calculoLatencia(List<Integer> cadCamino) {  //CALCULO LATENCIA CAMINO
        double latenciaCamino=0, latenciaTramo;  
        int iEnlace;
        
        for(int i=0; i<cadCamino.size()-1; i++) {   //CALCULO DE LATENCIA TRAMO
            iEnlace =obtenerIndiceEnlace(cadCamino.get(i), cadCamino.get(i+1));
            
            if(i==0){
                transporte[0].add(enlaces.get(iEnlace).getDU());
                tam1erPaquete = enlaces.get(iEnlace).getDU();
            }
           
            latenciaTramo =(((enlaces.get(iEnlace).getDistancia()*converMetros)/velLuz)+
                           (((enlaces.get(iEnlace).getDC()+enlaces.get(iEnlace).getDU())*converMbps)/enlaces.get(iEnlace).getTasaTransfer())+nodos.get(cadCamino.get(i+1)-1).getTC())
                           *calculoNumPaquetes(enlaces.get(iEnlace).getDU(),i); 
            
            latenciaCamino = latenciaCamino + latenciaTramo;
        }
        
        limpiarTransporte();
        
        Camino c = new Camino(cadCamino,latenciaCamino);
        caminos.add(c);
        System.out.println("Camino: "+c.getCamino()+" Latencia: "+c.getLatencia());
    }
    
    private int obtenerIndiceEnlace(int nodoInicioTramo, int nodoFinTramo){
        int indiceEnlace = 0;
        for (int i=0; i<enlaces.size(); i++)
            if(enlaces.get(i).getNoOrigen()==nodoInicioTramo && enlaces.get(i).getNoDestino() == nodoFinTramo)  
                indiceEnlace = i;         
        return indiceEnlace;
    }
    
    
    private int calculoNumPaquetes(int DU, int indice) {
        int total_paquetes = 1;
        int fullpaquetes = 0;

        for (int j = 0; j < transporte[indice].size(); j++){
            int valor = transporte[indice].get(j);
            
            if (valor > DU) {

                fullpaquetes = valor / DU;
                int restante = valor - (DU * fullpaquetes);

                for (int k = 0; k < fullpaquetes; k++) 
                    transporte[indice + 1].add(DU);

                transporte[indice + 1].add(restante);
                total_paquetes++;
            }else
                transporte[indice + 1].add(valor);
        }
        return transporte[indice + 1].size();
    }
    

    private void limpiarTransporte() {
        for (int i = 0; i < totEnlaces; i++) 
            transporte[i].clear();
    }
    
    public void mejorLatencia(){   
        for (int i = 0; i < caminos.size()-1; i++){
            
            for (int j = i+1; j < caminos.size(); j++) {
                if(caminos.get(i).getLatencia()<caminos.get(j).getLatencia())                                        
                    mejorLatencia = caminos.get(i).getLatencia();
            }
        }
        
        System.out.println("\nExisten "+caminos.size() +" caminos posibles");
    }
    
    public void mostrarTiempoTotalTransferencia(double tamTransferencia){
        double numPaquetes = Math.ceil((tamTransferencia*Math.pow(1024,3))/tam1erPaquete);            
        double tiempoTotalTransferencia = numPaquetes * mejorLatencia;
        
        System.out.println("\nMejor latencia: "+mejorLatencia);
        System.out.println("Tiempo total de transferencia en segundos: "+tiempoTotalTransferencia);
        System.out.println("Tiempo total de transferencia en minutos: "+tiempoTotalTransferencia/60);
        System.out.println("Tiempo total de transferencia en horas: "+tiempoTotalTransferencia/3600);
    }
    
    public void mostrarListaDeAdaycencia(){
        for (int i = 0; i < totNodos; i++) 
            System.out.println(i+" "+listaAdyacen[i]);      
    }
    
    public void mostrarListasTransporte(){
        for (int i = 0; i < totEnlaces; i++) 
            System.out.println(i+" "+transporte[i]);      
    }
    
    public void mostrarDatosNodos(){
    	System.out.println("\nLista de nodos");
        for(Nodo n : nodos)
            System.out.println("Num nodo: "+n.getNoNodo()+"   TC: "+n.getTC());
    }
    
    public void mostrarDatosEnlaces(){
    	System.out.println("\nLista de enlaces");
        for(Enlace e : enlaces){
            System.out.println("num Origen: "+e.getNoOrigen()+"   num Destino: "+e.getNoDestino()+"   tasa de transfer: "+e.getTasaTransfer()+
                                "   distancia: "+e.getDistancia()+"   DC: "+e.getDC()+"  DU: "+e.getDU());
        }
    }
  
}
