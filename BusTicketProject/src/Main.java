import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n--- Bus Ticket Booking System ---");
                System.out.println("1. View Buses");
                System.out.println("2. Book Ticket");
                System.out.println("3. View Saved Tickets");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");

                int choice;
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input! Please enter a number (1-4).");
                    continue;
                }
                sc.nextLine(); // Clear newline

                switch (choice) {
                    case 1:
                        system.showBuses();
                        break;
                    case 2:
                        bookTicketProcess(system, sc);
                        break;
                    case 3:
                        system.readTickets();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please select 1-4.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    private static void bookTicketProcess(BookingSystem system, Scanner sc) {
        // Name
        String name;
        while (true) {
            System.out.print("Enter name: ");
            name = sc.nextLine();
            if (name.matches("[a-zA-Z ]+")) break;
            System.out.println("Invalid name! Only letters and spaces allowed.");
        }

        // Age
        int age = 0;
        while (true) {
            try {
                System.out.print("Enter age: ");
                age = sc.nextInt();
                sc.nextLine();
                if (age >= 0 && age <= 150) break;
                System.out.println("Invalid age! Must be between 0 and 150.");
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input! Please enter a number for age.");
            }
        }

        // Email
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = sc.nextLine();
            if (Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email)) break;
            System.out.println("Invalid email format! Please enter a valid email.");
        }

        system.showBuses();

        // Bus number
        int busNum;
        Bus selectedBus;
        while (true) {
            System.out.print("Enter bus number: ");
            busNum = sc.nextInt();
            selectedBus = system.getBusByNumber(busNum);
            if (selectedBus != null) break;
            System.out.println("Invalid bus number! Please choose from available buses.");
        }

        // Show available seats
        Set<Integer> availableSeats = selectedBus.getAvailableSeats();
        System.out.println("\nAvailable Seats: " + availableSeats);

        // Ask if user wants to continue booking
        System.out.print("Do you want to book seats now?\n(Enter 'yes' to continue/Enter anything to return home page): ");
        sc.nextLine();  // clear leftover newline
        String answer = sc.nextLine().trim().toLowerCase();
        if (!answer.equals("yes")) {
            System.out.println("Returning to home page...");
            return;
        }

        // Seat selection
        Set<Integer> selectedSeats = new HashSet<>();
        int seatsToBook;
        while (true) {
            try {
                System.out.print("How many seats do you want to book? ");
                seatsToBook = sc.nextInt();
                sc.nextLine();
                if (seatsToBook > 0 && seatsToBook <= availableSeats.size()) break;
                System.out.println("Invalid number! Must be between 1 and " + availableSeats.size());
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        for (int i = 0; i < seatsToBook; i++) {
            while (true) {
                try {
                    System.out.print("Enter seat number " + (i + 1) + ": ");
                    int seatNum = sc.nextInt();
                    sc.nextLine();

                    if (seatNum < 1 || seatNum > selectedBus.getTotalSeats()) {
                        System.out.println("Seat number must be between 1 and " + selectedBus.getTotalSeats());
                    } else if (!availableSeats.contains(seatNum)) {
                        System.out.println("Seat " + seatNum + " is not available!");
                    } else if (selectedSeats.contains(seatNum)) {
                        System.out.println("You already selected this seat!");
                    } else {
                        selectedSeats.add(seatNum);
                        break;
                    }
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("Invalid input! Please enter a number.");
                }
            }
        }

        system.bookTicket(name, age, email, busNum, selectedSeats);
    }
}
