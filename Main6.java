/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main6;

/**
 *
 * @author nayak
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class Food {
    String food_name;
    String beverages;
    String dessert;
    boolean isOrdered;
    boolean isPrepared;
    boolean isReplaced;
    boolean isCanceled;

    public Food(String food_name, String beverages, String dessert) {
        this.food_name = food_name;
        this.beverages = beverages;
        this.dessert = dessert;
       
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
    String restaurant_phoneno;
    double total;

    public Restaurant(String restaurant_name, String restaurant_address, String restaurant_phoneno) {
        this.restaurant_name = restaurant_name;
        this.restaurant_address = restaurant_address;
        
    }

    void deliver() {
        System.out.println("Food delivered from: " + restaurant_address);
    }

    void calculate_total(double food_price) {
    if (food_price > 0) {
        total = food_price + 2.99; // Assuming delivery charge
    } else {
        total = 0.0; // No food ordered, no delivery charge
    }
    System.out.println("Total amount to be paid: $" + total);
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
         System.out.println("Total Amount Paid: $" + totalAmount);
        
    }

    public void makeOfflinePayment(double totalAmount) {
        System.out.println("Offline payment successful!");
        System.out.println("Total Amount Paid: $" + totalAmount);
    }
}

public class Main6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Food Delivery System!");
        System.out.println("Customer Information:");
        System.out.println("Please enter your name: ");
        String customer_name = scanner.nextLine();

        System.out.println("Please enter your address: ");
        String customer_address = scanner.nextLine();

        System.out.println("Please enter your ID: ");
        String id = scanner.nextLine();

        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        System.out.println("Please enter your phone number: ");
        String customer_phoneno = scanner.nextLine();
        
        Customer customer = new Customer(customer_name, customer_address, id, password, customer_phoneno);
        customer.login();
        System.out.println("Restaurent Information:");
        System.out.println("Please enter the restaurant name: ");
        String restaurant_name = scanner.nextLine();

        System.out.println("Please enter the restaurant address: ");
        String restaurant_address = scanner.nextLine();

         Map<String, String> restaurantPhones = new HashMap<>();
        restaurantPhones.put("swadesh", "123-456-7890");
        restaurantPhones.put("top in tiwn", "987-654-3210");
        restaurantPhones.put("fst food", "456-789-0123");

        String phoneNumber = restaurantPhones.get(restaurant_name);
        if (phoneNumber != null) {
            System.out.println("Restaurant Phone Number: " + phoneNumber);
            Restaurant restaurant = new Restaurant(restaurant_name, restaurant_address, phoneNumber);
            // Continue with the rest of the program
        } else {
            System.out.println("Restaurant phone no not found in the list.");
            // Handle the case when the restaurant is not found
        }
        Restaurant restaurant = new Restaurant(restaurant_name, restaurant_address, phoneNumber);

          Map<String, List<String>> menu = new HashMap<>();
        menu.put("Beverages", Arrays.asList("cola", "orange", "mango juice"));
        menu.put("Food Name", Arrays.asList("pasta", "pizza", "burger"));
        menu.put("Desserts", Arrays.asList("ice cream", "cake", "brownie"));

        // Prices for each item
        Map<String, Double> prices = new HashMap<>();
        prices.put("cola", 30.00);
        prices.put("orange", 10.00);
        prices.put("mango juice", 50.00);
        prices.put("pasta", 65.00);
        prices.put("pizza", 90.00);
        prices.put("burger", 105.99);
        prices.put("ice cream", 40.00);
        prices.put("cake", 25.00);
        prices.put("brownie", 35.00);

        // Calculate maximum length of category for formatting
        int maxLength = menu.keySet().stream().mapToInt(String::length).max().orElse(0);

        // Print menu in table-like format
         System.out.printf("%-" + (maxLength + 5) + "s%s%17s\n", "Category", "Items", "Price");
        for (String category : menu.keySet()) {
            System.out.printf("%-" + (maxLength + 5) + "s", category + ":");
            List<String> items = menu.get(category);
            for (int i = 0; i < items.size(); i++) {
                String item = items.get(i);
                double price = prices.get(item);
                if (i > 0) {
                    System.out.printf("%" + (maxLength + 5) + "s", "");
                }
                System.out.printf("- %-15s $%.2f\n", item, price);
            }
            System.out.println();
        }
          System.out.println("Please enter your food_name choice: ");
        String food_name = scanner.nextLine().toLowerCase();

        System.out.println("Please enter your beverage choice: ");
        String beverages = scanner.nextLine().toLowerCase();

        System.out.println("Please enter your dessert choice: ");
        String dessert = scanner.nextLine().toLowerCase();

     double food_price = prices.getOrDefault(food_name, 0.0) +
                    prices.getOrDefault(beverages, 0.0) +
                    prices.getOrDefault(dessert, 0.0);


        Food food = new Food(food_name, beverages, dessert);

        Payment payment = new Payment();
        boolean foodAvailable = food_price > 0;
        boolean paymentSuccessful = false;

        System.out.println("You have successfully entered all details.");
        if(foodAvailable){
        System.out.println("Your order total is: $" + food_price);
        restaurant.calculate_total(food_price);

double totalAmount = food_price + 2.99; // Assuming delivery charge
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
paymentSuccessful=true;
        }
        else{
             System.out.println("No food ordered. No payment required.");
        }
        if (paymentSuccessful) {
    System.out.println("Thank you for your order! Your food will be delivered to " + customer.customer_address);
    restaurant.deliver();
} else {
    System.out.println("Thank you for beeing with us");
}
        
    }
}

