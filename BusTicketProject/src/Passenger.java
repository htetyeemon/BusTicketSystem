/*
 Specialized Person class.
 Demonstrates inheritance and method overriding.
 Good practice because:
 1. Extends Person with passenger-specific attributes (email)
 2. Overrides displayDetails() to show complete information
 3. extensible
 */

public class Passenger extends Person {
    private String email;

    public Passenger(String name, int age, String email) {
        super(name, age);  // Reusing parent constructor
        this.email = email;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();  // Reusing parent method
        System.out.println("Email: " + email);  // Extending functionality
    }
}