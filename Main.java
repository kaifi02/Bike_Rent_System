import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bike {
    private String bikeId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Bike(String bikeId, String brand, String model, double basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnBike() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class BikeRentalSystem {
    private List<Bike> bikes;
    private List<Customer> customers;
    private List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentBike(Bike bike, Customer customer, int days) {
        if (bike.isAvailable()) {
            bike.rent();
            rentals.add(new Rental(bike, customer, days));

        } else {
            System.out.println("Bike is not available for rent.");
        }
    }

    public void returnBike(Bike bike) {
        bike.returnBike();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getBike() == bike) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Bike was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Bike Rental System =====");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Bike ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Bikes:");
                for (Bike bike : bikes) {
                    if (bike.isAvailable()) {
                        System.out.println(bike.getBikeId() + " - " + bike.getBrand() + " " + bike.getModel());
                    }


                }

                System.out.print("\nEnter the bike ID you want to rent: ");
                String bikeId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Bike selectedBike = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && bike.isAvailable()) {
                        selectedBike = bike;
                        break;
                    }
                }

                if (selectedBike != null) {
                    double totalPrice = selectedBike.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Bike: " + selectedBike.getBrand() + " " + selectedBike.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentBike(selectedBike, newCustomer, rentalDays);
                        System.out.println("\nBike rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid bike selection or bike not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Bike ==\n");
                System.out.print("Enter the bike ID you want to return: ");
                String bikeId = scanner.nextLine();

                Bike bikeToReturn = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && !bike.isAvailable()) {
                        bikeToReturn = bike;
                        break;
                    }
                }

                if (bikeToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getBike() == bikeToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnBike(bikeToReturn);
                        System.out.println("Bike returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Bike was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid bike ID or bike is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Bike Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();

        Bike bike1 = new Bike("C001", "BMW", "1000RR", 60.0); // Different base price per day for each car
        Bike bike2 = new Bike("C002", "Yamaha", "Ninja H2R", 70.0);
        Bike bike3 = new Bike("C003", "KTM", "1190", 150.0);
        rentalSystem.addBike(bike1);
        rentalSystem.addBike(bike2);
        rentalSystem.addBike(bike3);

        rentalSystem.menu();
    }
}