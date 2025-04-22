/**
 * Demonstrates COLLECTIONS and ENCAPSULATION
 * - Uses Set<Integer> for distinct seat management
 * - Provides built-in methods for seat operations(eg.contains(),add())
 */

import java.util.*;

public class Bus {
    private int busNumber;
    private String route;
    private int totalSeats;
    private Set<Integer> bookedSeats;
    private double price;

    public Bus(int busNumber, String route, int totalSeats, double price, Set<Integer> bookedSeats) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.price = price;
        this.bookedSeats = bookedSeats != null ? bookedSeats : new HashSet<>();
    }

    /**
     * Demonstrates INPUT VALIDATION (2.4)
     * - Prevents invalid seat bookings
     * - Returns boolean for clear success/failure
     */

    public boolean bookSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats || bookedSeats.contains(seatNumber)) {
            return false;
        }
        return bookedSeats.add(seatNumber);
    }

    public Set<Integer> getAvailableSeats() {
        Set<Integer> available = new HashSet<>();
        for (int i = 1; i <= totalSeats; i++) {
            if (!bookedSeats.contains(i)) available.add(i);
        }
        return available;
    }

    public int getAvailableSeatCount() {
        return totalSeats - bookedSeats.size();
    }

    public int getBusNumber() {
        return busNumber;
    }

    public String getRoute() {
        return route;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getPrice() {
        return price;
    }

    public Set<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        for (Integer seat : bookedSeats) {
            sb.append(seat).append("|");
        }

        String booked;
        if (sb.length() > 0) {
            // Remove the last extra "|"
            booked = sb.substring(0, sb.length() - 1);
        } else {
            booked = "";
        }

        return String.format("%d,%s,%d,%.2f,%s", busNumber, route, totalSeats, price, booked);
    }

    @Override
    public String toString() {
        return String.format("Bus %s - %s - Seats Available: %d/%d - Price: $%.2f",
                busNumber, route, getAvailableSeatCount(), totalSeats, price);
    }
}
