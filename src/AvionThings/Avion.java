package AvionThings;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Avion {
    public static void main(String[] args) {
        Avion w = new Avion(0, false,"Europa");
        w.embarcarPasajeros();
        System.err.println("fjsdklfds");
    }
    int CAPACITYMAX;
    private int prioridad;
    private boolean isEmergency;
    Queue<Pasajero> pasajeros = new LinkedList<>();
    String nombreVuelo;

    private Avion(int prioridad, boolean isEmergency, String nombreVuelo){
        CAPACITYMAX = 10;
        this.prioridad  = prioridad;
        this.isEmergency = isEmergency;
        this.nombreVuelo = nombreVuelo;
        


    }

    public void embarcarPasajeros(){
        StringBuilder nombres = new StringBuilder(nombreVuelo);
        for ( int i = 1; i < CAPACITYMAX+1; i++){
            int numEquipajeMano = (int)(Math.random()*2); //lleva 1 || no Lleva 2
            int numEquipajeBodega = (int) (Math.random()*3); // 0 || 1 || 2
            String add = "pasajero" + i;
            nombres.append(add);
            Pasajero p = new Pasajero(nombres.toString());
            System.out.println("------------------");
            p.equiparEquipaje(numEquipajeMano, numEquipajeBodega);
            System.out.println("---------------");
            nombres.delete(nombres.length() - add.length(), nombres.length());
            pasajeros.add(p);
            

        }
        while (!pasajeros.isEmpty()) {
            System.out.println();
            Pasajero p = pasajeros.poll();
            System.out.println(p.nombre);
            System.out.println(p.equipajeMano.size());
            System.out.println(p.equipajesBodega.size());
            System.out.println(p.equipajeMano.toString());
            System.out.println(p.equipajesBodega.toString());


        }
    }
    
}
