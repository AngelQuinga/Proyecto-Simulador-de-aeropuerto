package Aeropuerto.utils;

import Aeropuerto.modelo.Flight;
import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight f1, Flight f2) {

        // 1. Emergencias primero
        int emergencyCompare = Boolean.compare(f2.isEmergency(), f1.isEmergency());
        if (emergencyCompare != 0) return emergencyCompare;

        // 2. Llega antes = mayor prioridad
        int timeCompare = Long.compare(f1.getTimestamp(), f2.getTimestamp());
        if (timeCompare != 0) return timeCompare;

        // 3. Desempate por ID
        return f1.getId().compareTo(f2.getId());
    }
}
