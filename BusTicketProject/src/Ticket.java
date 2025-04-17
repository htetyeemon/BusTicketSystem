import java.util.Set;

public class Ticket {
    private Passenger passenger;
    private Bus bus;
    private Set<Integer> seatNumbers;
    private double totalPrice;

    public Ticket(Passenger passenger, Bus bus, Set<Integer> seatNumbers) {
        this.passenger = passenger;
        this.bus = bus;
        this.seatNumbers = seatNumbers;
        this.totalPrice = bus.getPrice() * seatNumbers.size();
    }

    public String getTicketDetails() {
        return passenger.name + "," + bus.getBusNumber() + "," + bus.getRoute() +
                "," + seatNumbers + "," + totalPrice;
    }

    public void printTicket() {
        System.out.println("\n=== TICKET DETAILS ===");
        passenger.displayDetails();
        System.out.println("Bus Number: " + bus.getBusNumber());
        System.out.println("Route: " + bus.getRoute());
        System.out.println("Price per seat: $" + bus.getPrice());
        System.out.println("Seat Numbers: " + seatNumbers);
        System.out.println("TOTAL PRICE: $" + totalPrice);
        System.out.println("======================");
    }
}