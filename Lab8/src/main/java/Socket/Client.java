package Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final static Scanner scanner = new Scanner(System.in);
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;

    public static void addNewCategory() {
        System.out.println("Enter category ID: ");
        String newCategoryId = scanner.nextLine();
        System.out.println("Enter category name: ");
        String newCategoryName = scanner.nextLine();
        out.println(generateLineWithSeparator("1", newCategoryId, newCategoryName));
    }

    public static void deleteCategory() {
        System.out.println("Enter category ID: ");
        String categoryId = scanner.nextLine();
        out.println(generateLineWithSeparator("2", categoryId));
    }

    public static void addProductToCategory() {
        System.out.println("Enter product ID: ");
        String newProductId = scanner.nextLine();
        System.out.println("Enter product name: ");
        String newProductName = scanner.nextLine();
        System.out.println("Enter product amount: ");
        String newProductAmount = scanner.nextLine();
        System.out.println("Enter product price: ");
        String newProductPrice = scanner.nextLine();
        System.out.println("Enter product category ID: ");
        String newProductCategoryId = scanner.nextLine();
        out.println(generateLineWithSeparator("3", newProductId, newProductName,newProductAmount ,newProductPrice , newProductCategoryId));
    }

    public static void deleteProduct() {
        System.out.println("Enter product ID: ");
        String productId = scanner.nextLine();
        out.println(generateLineWithSeparator("4", productId));
    }

    public static void editProduct() {
        System.out.println("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.println("Enter product name: ");
        String newProductName = scanner.nextLine();
        System.out.println("Enter product amount: ");
        String newProductAmount = scanner.nextLine();
        System.out.println("Enter product price: ");
        String newProductPrice = scanner.nextLine();
        out.println(generateLineWithSeparator("5", productId, newProductName,newProductAmount, newProductPrice));
    }

    public static void countProductsInCategory() {
        System.out.println("Enter category ID: ");
        String categoryId = scanner.nextLine();
        out.println(generateLineWithSeparator("6", categoryId));
    }

    public static void searchProductByName() {
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();
        out.println(generateLineWithSeparator("7", productName));
    }

    public static void getAllProductsInCategory() {
        System.out.println("Enter category ID: ");
        String categoryId = scanner.nextLine();
        out.println(generateLineWithSeparator("8", categoryId));
    }

    public static void getAllCategories() {
        out.println(generateLineWithSeparator("9"));
    }

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

    public static String generateLineWithSeparator(String... args) {
        StringBuilder line = new StringBuilder();
        for (String arg : args) {
            line.append(arg).append("%");
        }
        return line.toString();
    }

    public static String[] parseLine(String line) {
        return line.split("%");
    }

    public static void readResponse() {
        try {
            String response = in.readLine();
            String[] responseParts = parseLine(response);
            System.out.println("CLIENT:");
            System.out.println("Status: ");
            for (String responsePart : responseParts) {
                System.out.println(responsePart);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8888);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String choice = "";
            while (true) {
                showMenu();
                choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addNewCategory();
                    case "2" -> deleteCategory();
                    case "3" -> addProductToCategory();
                    case "4" -> deleteProduct();
                    case "5" -> editProduct();
                    case "6" -> countProductsInCategory();
                    case "7" -> searchProductByName();
                    case "8" -> getAllProductsInCategory();
                    case "9" -> getAllCategories();
                    case "10" -> {
                        System.out.println("CLIENT: Exit");
                        out.println(generateLineWithSeparator("10"));
                        socket.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }

                readResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
