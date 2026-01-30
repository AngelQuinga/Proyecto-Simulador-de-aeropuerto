package Aeropuerto.sim;

import Aeropuerto.modelo.*;
import Aeropuerto.utils.FlightComparator;
import java.util.*;

public class AirportSimulator {
    
    // 1. ESTRUCTURAS DE DATOS (Agrupadas para explicar)
    private PriorityQueue<Flight> landingQueue = new PriorityQueue<>(new FlightComparator());
    private Queue<Flight> takeoffQueue = new LinkedList<>();
    private Stack<Baggage> baggageStack = new Stack<>();

    // 2. RECURSOS FÍSICOS
    private Runway runwayLanding = new Runway("Pista Aterrizaje");
    private Runway runwayTakeoff = new Runway("Pista Despegue");

    // 3. CONSTANTES (Fáciles de cambiar)
    private static final int LANDING_TIME = 3;
    private static final int TAKEOFF_TIME = 2;
    private static final int MAX_FLIGHTS = 100;

    private int currentStep = 0;

    // MÉTODO PRINCIPAL: El corazón de la simulación
    public void step() {
        currentStep++;
        System.out.println("\n--- PASO DE SIMULACIÓN " + currentStep + " ---");

        actualizarPistas();
        gestionarDespegues();
        gestionarAterrizajes();
        procesarUnEquipaje();
    }

    // --- MÓDULOS PEQUEÑOS (Fáciles de explicar) ---

    private void actualizarPistas() {
        // Ejecutamos un tick y revisamos si alguien terminó
        Flight landed = runwayLanding.tick();
        Flight departed = runwayTakeoff.tick();

        if (landed != null) {
            System.out.println("ATERRIZAJE COMPLETADO: " + landed.getId());
            descargarMaletas(landed.getId());
        }
        if (departed != null) {
            System.out.println("DESPEGUE COMPLETADO: " + departed.getId());
        }
    }

    private void gestionarAterrizajes() {
        if (runwayLanding.isFree() && !landingQueue.isEmpty()) {
            Flight next = landingQueue.poll(); // Saca por prioridad
            runwayLanding.assignFlight(next, LANDING_TIME);
            String msg = next.isEmergency() ? " [EMERGENCIA]" : "";
            System.out.println("INICIANDO ATERRIZAJE: " + next.getId() + msg);
        }
    }

    private void gestionarDespegues() {
        if (runwayTakeoff.isFree() && !takeoffQueue.isEmpty()) {
            Flight next = takeoffQueue.poll(); // Saca por orden de llegada (FIFO)
            runwayTakeoff.assignFlight(next, TAKEOFF_TIME);
            System.out.println("INICIANDO DESPEGUE: " + next.getId());
        }
    }

    private void descargarMaletas(String id) {
        System.out.println("   -> Descargando 50 maletas a la pila LIFO.");
        for (int i = 0; i < 50; i++) {
            baggageStack.push(new Baggage(id));
        }
    }

    private void procesarUnEquipaje() {
        if (!baggageStack.isEmpty()) {
            Baggage b = baggageStack.pop(); // Último en entrar, primero en salir
            System.out.println("Procesando: " + b + ". (Pila: " + baggageStack.size() + ")");
        } else {
            System.out.println("Sin equipaje pendiente.");
        }
    }

    // Métodos de apoyo
    public void addFlight(Flight f) {
        if ((landingQueue.size() + takeoffQueue.size()) >= MAX_FLIGHTS) {
            System.out.println("ERROR: Capacidad llena.");
            return;
        }
        if (f.getType() == FlightType.ARRIVAL) landingQueue.offer(f);
        else takeoffQueue.offer(f);
        System.out.println(">> Vuelo programado: " + f);
    }

    public void printStatus() {
        System.out.println("\n=== ESTADO DEL AEROPUERTO ===");
        System.out.println("Espera Aterrizaje: " + landingQueue.size() + " | Espera Despegue: " + takeoffQueue.size());
        System.out.println("Pila Equipaje: " + baggageStack.size());
        System.out.println(runwayLanding + "\n" + runwayTakeoff);
    }
}
