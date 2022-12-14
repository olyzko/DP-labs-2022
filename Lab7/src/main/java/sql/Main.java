package sql;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/grocery";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static void showMenu() {
        System.out.println("1. Show all categories");
        System.out.println("2. Show all products");
        System.out.println("3. Show all products in a category");
        System.out.println("4. Add a new category");
        System.out.println("5. Add a new product");
        System.out.println("6. Update a category");
        System.out.println("7. Update a product");
        System.out.println("8. Delete a category");
        System.out.println("9. Delete a product");
        System.out.println("10. Exit");
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Grocery grocery = new Grocery(DB_URL, DB_USER, DB_PASSWORD);
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        while (true) {
            showMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> grocery.showAllCategories();
                case "2" -> grocery.showAllProducts();
                case "3" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    grocery.showAllProductsInCategory(categoryName);
                }
                case "4" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    grocery.addCategory(categoryName);
                }
                case "5" -> {
                    System.out.println("Enter product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product quantity: ");
                    int newProductQuantity = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category name: ");
                    String newCategoryName = scanner.nextLine();
                    grocery.addProduct(newCategoryName, newProductName, newProductPrice, newProductQuantity);
                }
                case "6" -> {
                    System.out.println("Enter category name: ");
                    String oldName = scanner.nextLine();
                    System.out.println("Enter new category name: ");
                    String newName = scanner.nextLine();
                    grocery.updateCategory(oldName, newName);
                }
                case "7" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter new product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product quantity: ");
                    int newProductQuantity = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new category name: ");
                    String newCategory = scanner.nextLine();
                    grocery.updateProduct(productId, newCategory, newProductName, newProductPrice, newProductQuantity);
                }
                case "8" -> {
                    System.out.println("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    grocery.deleteCategory(categoryName);
                }
                case "9" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    grocery.deleteProduct(productId);
                }
                case "10" -> {
                    grocery.stop();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
