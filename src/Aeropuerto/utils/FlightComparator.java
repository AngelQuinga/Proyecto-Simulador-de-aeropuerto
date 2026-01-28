package Aeropuerto.utils;

import Aeropuerto.modelo.Flight;
import java.util.Comparator;

public class FlightComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight f1, Flight f2) {
        // 1. Prioridad: Emergencia (true va antes que false)
        if (f1.isEmergency() && !f2.isEmergency()) return -1;
        if (!f1.isEmergency() && f2.isEmergency()) return 1;

        // 2. Prioridad: Tiempo de llegada (menor timestamp va antes)
        int timeCompare = Long.compare(f1.getTimestamp(), f2.getTimestamp());
        if (timeCompare != 0) return timeCompare;

        // 3. Desempate: ID alfabético
        return f1.getId().compareTo(f2.getId());
    }
}