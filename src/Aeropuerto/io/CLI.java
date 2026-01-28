package Aeropuerto.io;

import Aeropuerto.modelo.Flight;
import Aeropuerto.modelo.FlightType;
import Aeropuerto.sim.AirportSimulator;
import java.util.Scanner;

public class CLI {
    private AirportSimulator simulator;
    private Scanner scanner;

    public CLI() {
        this.simulator = new AirportSimulator();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Bienvenido al Simulador de Aeropuerto v1.0");
        System.out.println("Comandos: add, step, run <n>, status, exit");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "add":
                        handleAdd(parts);
                        break;
                    case "step":
                        simulator.step();
                        break;
                    case "run":
                        if (parts.length < 2) System.out.println("Uso: run <numero_pasos>");
                        else {
                            int n = Integer.parseInt(parts[1]);
                            for(int i=0; i<n; i++) simulator.step();
                        }
                        break;
                    case "status":
                        simulator.printStatus();
                        break;
                    case "exit":
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Comando desconocido.");
                }
            } catch (Exception e) {
                System.out.println("Error procesando comando: " + e.getMessage());
            }
        }
    }

    // Formato: add arrival AV123 true 100
    private void handleAdd(String[] parts) {
        if (parts.length < 5) {
            System.out.println("Uso: add [arrival/departure] [id] [emergency:true/false] [timestamp]");
            return;
        }
        
        FlightType type = parts[1].equalsIgnoreCase("arrival") ? FlightType.ARRIVAL : FlightType.DEPARTURE;
        String id = parts[2];
        boolean emergency = Boolean.parseBoolean(parts[3]);
        long timestamp = Long.parseLong(parts[4]);

        Flight f = new Flight(id, type, emergency, timestamp);
        simulator.addFlight(f);
    }
}
