/*
 Base class representing a person with common attributes.
 Uses inheritance to allow specialization in child classes.
 Good practice because:
 1. Encapsulates common person attributes (name, age)
 2. Provides base functionality that can be extended
 3. Prevents repeatition
 */

public class Person {
    protected String name;  // Protected for child class access
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}