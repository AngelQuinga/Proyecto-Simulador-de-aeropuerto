package Aeropuerto.sim;

import Aeropuerto.modelo.*;
import Aeropuerto.utils.FlightComparator;
import java.util.*;

public class AirportSimulator {
    
    private PriorityQueue<Flight> landingQueue; 
    private Queue<Flight> takeoffQueue;         
    private Stack<Baggage> baggageStack;        

    private Runway runwayLanding;
    private Runway runwayTakeoff;

    private static final int LANDING_DURATION = 3;
    private static final int TAKEOFF_DURATION = 2;
    private static final int BAGGAGE_PER_FLIGHT = 50;
    private static final int MAX_FLIGHTS = 100;

    private int currentStep = 0;

    public AirportSimulator() {
        this.landingQueue = new PriorityQueue<>(new FlightComparator());
        this.takeoffQueue = new LinkedList<>();
        this.baggageStack = new Stack<>();
        
        this.runwayLanding = new Runway("Pista Aterrizaje");
        this.runwayTakeoff = new Runway("Pista Despegue");
    }

    public void addFlight(Flight f) {
        if (totalFlightsPending() >= MAX_FLIGHTS) {
            System.out.println("ERROR: Capacidad máxima de vuelos alcanzada.");
            return;
        }
        if (f.getType() == FlightType.ARRIVAL) {
            landingQueue.offer(f);
            System.out.println(">> Vuelo programado para ATERRIZAJE: " + f);
        } else {
            takeoffQueue.offer(f);
            System.out.println(">> Vuelo programado para DESPEGUE: " + f);
        }
    }

    private int totalFlightsPending() {
        return landingQueue.size() + takeoffQueue.size();
    }

    public void step() {
        currentStep++;
        System.out.println("\n--- PASO DE SIMULACIÓN " + currentStep + " ---");

       
        Flight landed = runwayLanding.tick();
        Flight departed = runwayTakeoff.tick();

        
        if (landed != null) {
            System.out.println(" ATERRIZAJE COMPLETADO: " + landed.getId());
            System.out.println("   -> Descargando " + BAGGAGE_PER_FLIGHT + " maletas a la pila.");
            for (int i = 0; i < BAGGAGE_PER_FLIGHT; i++) {
                baggageStack.push(new Baggage(landed.getId()));
            }
        }

        // 3. Gestionar fin de despegue
        if (departed != null) {
            System.out.println(" DESPEGUE COMPLETADO: " + departed.getId());
        }

        // 4. Asignar nueva nave a Pista Aterrizaje (Si está libre)
        if (runwayLanding.isFree() && !landingQueue.isEmpty()) {
            Flight next = landingQueue.poll();
            runwayLanding.assignFlight(next, LANDING_DURATION);
            System.out.println(" INICIANDO ATERRIZAJE: " + next.getId() + 
                (next.isEmergency() ? " [EMERGENCIA]" : ""));
        }

        // 5. Asignar nueva nave a Pista Despegue (Si está libre)
        if (runwayTakeoff.isFree() && !takeoffQueue.isEmpty()) {
            Flight next = takeoffQueue.poll();
            runwayTakeoff.assignFlight(next, TAKEOFF_DURATION);
            System.out.println("INICIANDO DESPEGUE: " + next.getId());
        }

        // 6. Procesar equipaje (Sacar 1 de la pila)
        if (!baggageStack.isEmpty()) {
            Baggage b = baggageStack.pop();
            System.out.println(" Equipaje procesado: " + b + ". (Restantes en pila: " + baggageStack.size() + ")");
        } else {
            System.out.println(" No hay equipaje pendiente.");
        }
    }

    public void printStatus() {
        System.out.println("\n=== ESTADO DEL AEROPUERTO ===");
        System.out.println("Colas Espera Aterrizaje: " + landingQueue.size());
        System.out.println("Colas Espera Despegue:   " + takeoffQueue.size());
        System.out.println("Pila Equipaje:           " + baggageStack.size());
        System.out.println(runwayLanding);
        System.out.println(runwayTakeoff);
        System.out.println("=============================");
    }
}
