import java.util.*;
import java.io.*;

public class BookingSystem {
    private List<Bus> buses;
    private List<Ticket> tickets;
    private static final String BUSES_FILE = "buses.txt";
    private static final String TICKETS_FILE = "tickets.txt";

    public BookingSystem() {
        buses = new ArrayList<>();
        tickets = new ArrayList<>();
        loadBusesFromFile();
    }

    private void loadBusesFromFile() {
        try (Scanner fileScanner = new Scanner(new File(BUSES_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                int busNumber = Integer.parseInt(parts[0].trim());
                String route = parts[1].trim();
                int totalSeats = Integer.parseInt(parts[2].trim());
                double price = Double.parseDouble(parts[3].trim());

                Set<Integer> booked = new HashSet<>();
                if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
                    for (String seat : parts[4].trim().split("\\|")) {
                        booked.add(Integer.parseInt(seat));
                    }
                }

                buses.add(new Bus(busNumber, route, totalSeats, price, booked));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Buses file not found. Creating default buses.");
            createDefaultBuses();
        } catch (Exception e) {
            System.out.println("Error loading buses: " + e.getMessage());
            createDefaultBuses();
        }
    }

    private void createDefaultBuses() {
        buses.add(new Bus(101, "New York to Boston", 50, 35.99, new HashSet<>()));
        buses.add(new Bus(202, "Los Angeles to San Francisco", 60, 49.99, new HashSet<>()));
        buses.add(new Bus(303, "Chicago to Detroit", 45, 29.99, new HashSet<>()));
        saveBusesToFile(); // Save default buses
    }

    private void saveBusesToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(BUSES_FILE))) {
            for (Bus bus : buses) {
                out.println(bus.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving buses to file: " + e.getMessage());
        }
    }

    public void showBuses() {
        System.out.println("\nAvailable Buses:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }
    }

    public Bus getBusByNumber(int busNumber) {
        for (Bus bus : buses) {
            if (bus.getBusNumber() == busNumber) {
                return bus;
            }
        }
        return null;
    }

    public void bookTicket(String name, int age, String email, int busNum, Set<Integer> seatNumbers) {
        Bus bus = getBusByNumber(busNum);
        if (bus == null) {
            System.out.println("Bus not found!");
            return;
        }

        Set<Integer> successfullyBooked = new HashSet<>();
        for (int seat : seatNumbers) {
            if (!bus.bookSeat(seat)) {
                System.out.println("Seat " + seat + " is either already taken or invalid!");
                return;
            }
            successfullyBooked.add(seat);
        }

        Passenger passenger = new Passenger(name, age, email);
        Ticket ticket = new Ticket(passenger, bus, seatNumbers);
        tickets.add(ticket);
        ticket.printTicket();
        saveTicket(ticket);
        saveBusesToFile(); // Update booked seats in file
    }

    private void saveTicket(Ticket ticket) {
        try (PrintWriter out = new PrintWriter(new FileWriter(TICKETS_FILE, true))) {
            out.println(ticket.getTicketDetails());
        } catch (IOException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }

    public void readTickets() {
        System.out.println("\n=== PREVIOUS TICKETS ===");
        try (Scanner fileScanner = new Scanner(new File(TICKETS_FILE))) {
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous tickets found.");
        }
        System.out.println("=======================");
    }
}
