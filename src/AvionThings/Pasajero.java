package AvionThings;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Pasajero {
    //cada pasajero
    String nombre;
    ArrayList<String> equipajeMano = new ArrayList<>(); //solo uno 
    //el equipaje entra como Cola y sale como pila
    Queue<String> equipajesBodega = new LinkedList<>(); //puede haber hasta 2 
    //despues hago el builder usare el de defecto
    public Pasajero(String nombre){
        this.nombre = nombre;

    }
    public void equiparEquipaje(int mano, int bodega){
        System.out.println("Nombre pasajero: "+ nombre);
        System.out.println("numero EQ mano: "+mano);
        System.out.println("numero EQ bodega: "+(bodega));
        String equipaje;
        for(int i = 0; i <mano ; i++){
            equipaje = nombre.substring(0, 2) + (i+1);
            System.out.println(String.format("Equipaje de mano:%d %s", (i+1),equipaje));
            equipajeMano.add(equipaje);
            equipaje = "";
        }
        for(int i = 0; i <bodega ; i++){
            equipaje = nombre.substring(0, 2) +"_bodega_"+ (i+1);
            System.out.println(String.format("Equipaje de bodega:%d %s", (i+1),equipaje));
            equipajesBodega.add(equipaje);
            equipaje = "";
        }
    }
}
