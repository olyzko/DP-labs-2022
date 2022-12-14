package xml;

import javax.xml.transform.TransformerException;
import java.util.Scanner;

public class Main {
    public static final String PATH = "src\\main\\resources\\grocery.xml";
    static Scanner scanner = new Scanner(System.in);
    static Grocery grocery = new Grocery();

    public static void showMenu() {
        System.out.println("1. Show all categories");
        System.out.println("2. Show all products");
        System.out.println("3. Add a new category");
        System.out.println("4. Add a new product");
        System.out.println("5. Update a category");
        System.out.println("6. Update a product");
        System.out.println("7. Delete a category");
        System.out.println("8. Delete a product");
        System.out.println("9. Save all data");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) throws TransformerException {
        String choice;
        grocery = Parser.loadData(PATH);
        while (true) {
            showMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> grocery.showAllCategories();
                case "2" -> grocery.showAllProducts();
                case "3" -> {
                    System.out.println("Enter category ID: ");
                    int newCategoryId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category name: ");
                    String newCategoryName = scanner.nextLine();
                    grocery.addCategory(new Category(newCategoryId, newCategoryName));
                }
                case "4" -> {
                    System.out.println("Enter product ID: ");
                    int newProductId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product amount: ");
                    int newProductAmount = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category ID: ");
                    int newCategoryId = Integer.parseInt(scanner.nextLine());
                    grocery.addProduct(newCategoryId, new Product(newProductId, newProductName, newProductPrice, newProductAmount));
                }
                case "5" -> {
                    System.out.println("Enter category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new category name: ");
                    String newCategoryName = scanner.nextLine();
                    grocery.updateCategory(categoryId, newCategoryName);
                }
                case "6" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product name: ");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter new product price: ");
                    int newProductPrice = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product amount: ");
                    int newProductAmount = Integer.parseInt(scanner.nextLine());
                    grocery.updateProduct(categoryId, productId, new Product(productId, newProductName, newProductPrice, newProductAmount));
                }
                case "7" -> {
                    System.out.println("Enter category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    grocery.deleteCategory(categoryId);
                }
                case "8" -> {
                    System.out.println("Enter product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter category ID: ");
                    int categoryId = Integer.parseInt(scanner.nextLine());
                    grocery.deleteProduct(categoryId, productId);
                }
                case "9" -> Parser.saveData(PATH, grocery);
                case "0" -> System.exit(0);
                default -> System.out.println("Incorrect choice");
            }
        }
    }
}
