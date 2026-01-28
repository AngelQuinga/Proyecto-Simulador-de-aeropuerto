package Aeropuerto.modelo;

public class Baggage {
    private String flightId;

    public Baggage(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightId() {
        return flightId;
    }

    @Override
    public String toString() {
        return "Equipaje del vuelo " + flightId;
    }
}
