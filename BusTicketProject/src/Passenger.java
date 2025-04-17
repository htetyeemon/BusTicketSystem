public class Passenger extends Person {
    private String email;

    public Passenger(String name, int age, String email) {
        super(name, age);
        this.email = email;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Email: " + email);
    }
}