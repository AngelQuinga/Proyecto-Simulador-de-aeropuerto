package Aeropuerto.modelo;

public class Flight {
    private String id;
    private FlightType type;
    private boolean emergency;
    private long timestamp; // Hora de solicitud

    public Flight(String id, FlightType type, boolean emergency, long timestamp) {
        this.id = id;
        this.type = type;
        this.emergency = emergency;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public FlightType getType() { return type; }
    public boolean isEmergency() { return emergency; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %s (Emergencia: %s, Hora: %d)", 
            type, id, emergency, timestamp);
    }
}