
package foodsyste;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Food {
    String food_name;
    String beverages;
    String dessert;
    boolean isOrdered;

    private static final String MENU_FILE_PATH = "D:\\java assignment\\foodsyste\\src\\foodsyste\\menu.txt";

    private Map<String, Double> menu;

    public Food() {
        this.menu = new HashMap<>();

    }
    public void loadMenu() {
        try (BufferedReader br = new BufferedReader(new FileReader(MENU_FILE_PATH))) {
            System.out.println("Menu");
            System.out.printf("%-15s %-15s %s\n", "Category", "Food Item", "Price");
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String category = parts[0].trim();
                    String foodItem = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    menu.put(foodItem, price);
                    System.out.printf("%-15s %-15s %.2f\n", category, foodItem, price);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading menu: " + e.getMessage());
        }
    }




    public Map<String, Double> getMenu() {
        return menu;
    }


    public Food(String food_name, String beverages, String dessert) {
        this.food_name = food_name;
        this.beverages = beverages;
        this.dessert = dessert;
    }
    public void insertFoodData(double totalAmount, int foodQuantity, int beverageQuantity, int dessertQuantity) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement statement = conn.createStatement()) {
            String query = "INSERT INTO foods (food_name, beverages, dessert, food_quantity, beverage_quantity, dessert_quantity, price) " +
                    "VALUES ('" + food_name + "', '" + beverages + "', '" + dessert + "', " + foodQuantity + ", " + beverageQuantity + ", " + dessertQuantity + ", " + totalAmount + ")";
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Food data inserted successfully!");
            } else {
                System.out.println("Failed to insert food data.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting food data: " + e.getMessage());
        }
    }




    public void order_status() {
        System.out.println("Food order status - " + food_name + ":");
        System.out.println("Ordered: " + isOrdered);
    }
}

class Customer {
    String customer_name;
    String customer_address;
    String id;
    String password;
    String customer_phoneno;

    public Customer(String customer_name, String customer_address, String id, String password, String customer_phoneno) {
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.id = id;
        this.password = password;
        this.customer_phoneno = customer_phoneno;
    }

    void login() {
        System.out.println("Logged in as " + this.customer_name);
    }
}

class Restaurant {
    String restaurant_name;
    String restaurant_address;
    String phoneNumber;
    double total;

    public Restaurant(String restaurant_name, String restaurant_address, String phoneNumber) {
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        this.phoneNumber=phoneNumber;
    }
    public void insertRestaurantData() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement statement = conn.createStatement()) {
            String query = "INSERT INTO restaurants (name,address,phone_no) VALUES ('" + restaurant_name + "', '" + restaurant_address + "','"+ phoneNumber+ "')";
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Restaurant data inserted successfully!");
            } else {
                System.out.println("Failed to insert restaurant data.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting restaurant data: " + e.getMessage());
        }
    }
    void deliver() {
        System.out.println("Food delivered from: " + restaurant_address);
    }

    void calculate_total(double food_price) {
        if (food_price > 0) {
            total = food_price + 50.00;
        } else {
            total = 0.0;
        }
        System.out.println(" Minimum Delivery charge is 50.00");
        System.out.println("Total amount to be paid: " + total);
    }

}

class Payment {
    String card_number;
    String card_holder_name;
    String payment_type;

    public void makeOnlinePayment(String card_number, String card_holder_name, String payment_type, double totalAmount) {
        System.out.println("Online payment successful!");
        System.out.println("Card Number: " + card_number);
        System.out.println("Card Holder Name: " + card_holder_name);
        System.out.println("Payment Type: " + payment_type);
        System.out.println("Total Amount Paid: " + totalAmount);

    }

    public void makeOfflinePayment(double totalAmount) {
        System.out.println("Offline payment successful!");

        System.out.println("Total Amount Paid: " + totalAmount);
    }
}



public class Main {
    private static final String PHONE_NUMBERS_FILE = "D:\\java assignment\\foodsyste\\src\\foodsyste\\phone_numbers.txt";
    private static Map<String, String> phoneNumbersMap = new HashMap<>();

    public static void main(String[] args) {
        getConnection();
        Scanner scanner = new Scanner(System.in);
        double food_price=0.0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("------Welcome to Food Delivery System------");
            System.out.println("\nCustomer Information: ");
            System.out.println("Please enter your name:");
            String customer_name = scanner.nextLine();

            System.out.println("\nPlease enter your address:");
            String customer_address = scanner.nextLine();

            System.out.println("\nPlease enter your ID:-------- ");
            String id = scanner.nextLine();

            System.out.println("\nPlease enter your password:");
            String password = scanner.nextLine();

            System.out.println("\nPlease enter your phone number: ");
            String customer_phoneno = scanner.nextLine();

            insertData(id, customer_name, customer_address, password, customer_phoneno);

            Customer customer = new Customer(customer_name, customer_address, id, password, customer_phoneno);
            customer.login();
            System.out.println("\n\n\nRestaurant Information:");
            System.out.println("Please enter the restaurant name: ");
            String restaurant_name = scanner.nextLine();

            System.out.println("\nPlease enter the restaurant address: ");
            String restaurant_address = scanner.nextLine();

            loadPhoneNumbers();

            String phoneNumber = phoneNumbersMap.get(restaurant_name);
            if (phoneNumber != null) {
                System.out.println("\nRestaurant Phone Number: " + phoneNumber);


            } else {
                System.out.println("\nRestaurant phone no not found in the list.");
            }
            Restaurant restaurant = new Restaurant(restaurant_name, restaurant_address, phoneNumber);
            restaurant.insertRestaurantData();


            Food food = new Food();
            food.loadMenu();
            Map<String, Double> prices = food.getMenu();
            System.out.println("Please enter your food_name choices separated by commas (e.g., food1,food2,food3): ");
            String[] foodInputs = scanner.nextLine().toLowerCase().split(",");
            Map<String, Integer> foodQuantities = new HashMap<>();
            for (String foodInput : foodInputs) {
                System.out.println("Please enter the quantity for " + foodInput.trim() + ": ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                foodQuantities.put(foodInput.trim(), quantity);
            }

            System.out.println("Please enter your beverage choices separated by commas (e.g., bev1,bev2,bev3): ");
            String[] beverageInputs = scanner.nextLine().toLowerCase().split(",");
            Map<String, Integer> beverageQuantities = new HashMap<>();
            for (String beverageInput : beverageInputs) {
                System.out.println("Please enter the quantity for " + beverageInput.trim() + ": ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                beverageQuantities.put(beverageInput.trim(), quantity);
            }

            System.out.println("Please enter your dessert choices separated by commas (e.g., des1,des2,des3): ");
            String[] dessertInputs = scanner.nextLine().toLowerCase().split(",");
            Map<String, Integer> dessertQuantities = new HashMap<>();
            for (String dessertInput : dessertInputs) {
                System.out.println("Please enter the quantity for " + dessertInput.trim() + ": ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                dessertQuantities.put(dessertInput.trim(), quantity);
            }

            for (String food_name : foodQuantities.keySet()) {
                int foodQuantity = foodQuantities.get(food_name);

                for (String beverage : beverageQuantities.keySet()) {
                    int beverageQuantity = beverageQuantities.get(beverage);

                    for (String dessert : dessertQuantities.keySet()) {
                        int dessertQuantity = dessertQuantities.get(dessert);

                        Food foodname = new Food(food_name, beverage, dessert);
                        double foodPrice = prices.getOrDefault(food_name, 0.0) * foodQuantity
                                + prices.getOrDefault(beverage, 0.0) * beverageQuantity
                                + prices.getOrDefault(dessert, 0.0) * dessertQuantity;
                        foodname.insertFoodData(foodPrice, foodQuantity, beverageQuantity, dessertQuantity);
                        food_price += foodPrice;
                    }
                }

            }

            Payment payment = new Payment();
            boolean foodAvailable = food_price > 0;
            boolean paymentSuccessful = false;

            System.out.println("You have successfully entered all details.");
            if (foodAvailable) {

                System.out.println("Your order total is: " + food_price);
                restaurant.calculate_total(food_price);

                double totalAmount = food_price + 50.00; // Assuming delivery charge
                System.out.println("Please select payment method (1 for online, 2 for offline): ");
                int paymentMethod = scanner.nextInt();

                if (paymentMethod == 1) {
                    // Online payment
                    System.out.println("Please enter your card details:");
                    System.out.print("Card Number: ");
                    String card_number = scanner.next();
                    System.out.print("Card Holder Name: ");
                    String card_holder_name = scanner.next();
                    System.out.print("Payment Type: ");
                    String payment_type = scanner.next();
                    payment.makeOnlinePayment(card_number, card_holder_name, payment_type, totalAmount);

                } else if (paymentMethod == 2) {
                    // Offline payment
                    System.out.println("Please pay cash on delivery.");

                } else {
                    System.out.println("Invalid payment method.");
                }
                paymentSuccessful = true;
            } else {
                System.out.println("No food ordered. No payment required.");
            }
            if (paymentSuccessful) {
                System.out.println("Thank you for your order! Your food will be delivered to " + customer.customer_address);
                restaurant.deliver();
            } else {
                System.out.println("Thank you for being with us.");
            }
            // getData();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }

    }
    public static void loadPhoneNumbers() {
        try (BufferedReader br = new BufferedReader(new FileReader(PHONE_NUMBERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String phoneNumber = parts[1].trim();
                    phoneNumbersMap.put(name, phoneNumber);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading phone numbers: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String databaseUrl = "jdbc:mysql://localhost:3306/fooddelivery";
            String userName = "root";
            String password = "Karthik420(5)";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(databaseUrl, userName, password);
            System.out.println("connection done");
            return con;
        } catch (Exception e) {
            System.out.println("some error" + e);
        }
        return null;
    }
    public static void insertData(String id, String name, String address, String password, String phoneNo) {
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            String query = "INSERT INTO customers (id, name, address, password, phone_no) VALUES ('" + id + "', '" + name + "', '" + address + "', '" + password + "', '" + phoneNo + "')";
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully! in database");
            } else {
                System.out.println("Failed to insert data.");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }
}

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fooddelivery";
    private static final String USER = "root";
    private static final String PASSWORD = "Karthik420(5)";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
