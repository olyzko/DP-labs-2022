package RMI;

import Grocery.Product;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);
    public static void showMenu() {
        System.out.println("1. Add a new category");
        System.out.println("2. Delete a category");
        System.out.println("3. Add product to a category");
        System.out.println("4. Delete a product");
        System.out.println("5. Edit a product");
        System.out.println("6. Count total amount of products in a category");
        System.out.println("7. Search for a product by name");
        System.out.println("8. Get all products in a category");
        System.out.println("9. Get all categories");
        System.out.println("10. Exit");
    }

    public static void main(String[] args) throws NotBoundException, RemoteException, MalformedURLException {
        String url = "//localhost:123/Grocery";
        Grocery grocery = (Grocery) Naming.lookup(url);
        System.out.println("RMI object found");

        String choice = "";
        while (true) {
            showMenu();
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("Enter category name: ");
                    String newCategoryName = scanner.nextLine();
                    grocery.addNewCategory(newCategoryName);
                }
                case "2" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    grocery.deleteCategory(categoryName);
                }
                case "3" -> {
                    System.out.println("Enter product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product quantity: ");
                    int newProductQuantity = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product category name: ");
                    String newProductCategoryName = scanner.nextLine();
                    grocery.addProductToCategory(newProductCategoryName, newProductName, newProductPrice, newProductQuantity);
                }
                case "4" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    grocery.deleteProduct(productId);
                }
                case "5" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    System.out.println("Enter new product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter new product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product quantity: ");
                    int newProductQuantity = Integer.parseInt(scanner.nextLine());
                    grocery.editProduct(productId, categoryName, newProductName, newProductPrice, newProductQuantity);
                }
                case "6" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    System.out.println("Total amount of products in category " + categoryName + " is " + grocery.countProductsInCategory(categoryName));
                }
                case "7" -> {
                    System.out.println("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.println(grocery.searchProductByName(productName));
                }
                case "8" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                   for (Product product : grocery.getAllProductsInCategory(categoryName)) {
                       System.out.println(product);
                   }
                }
                case "9" -> {
                    System.out.println(Arrays.toString(grocery.getAllCategories().toArray()));
                }
                case "10" -> {
                    grocery.stop();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}