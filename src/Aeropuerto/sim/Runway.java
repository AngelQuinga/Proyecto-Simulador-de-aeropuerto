package Aeropuerto.sim;

import Aeropuerto.modelo.Flight;

public class Runway {
    private Flight currentFlight;
    private int remainingSteps;
    private String name;

    public Runway(String name) {
        this.name = name;
        this.remainingSteps = 0;
    }

    public boolean isFree() {
        return remainingSteps <= 0;
    }

    public void assignFlight(Flight f, int duration) {
        this.currentFlight = f;
        this.remainingSteps = duration;
    }

    public Flight getCurrentFlight() {
        return currentFlight;
    }

    public int getRemainingSteps() {
        return remainingSteps;
    }

    // Avanza el tiempo un paso. Retorna el vuelo si acaba de terminar, null si sigue ocupado.
    public Flight tick() {
        if (remainingSteps > 0) {
            remainingSteps--;
            if (remainingSteps == 0) {
                Flight finished = currentFlight;
                currentFlight = null;
                return finished;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        if (isFree()) return name + ": LIBRE";
        return name + ": Ocupada por " + currentFlight.getId() + " (" + remainingSteps + " pasos restantes)";
    }
}