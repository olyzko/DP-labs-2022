package Socket;

import Grocery.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static ServerSocket server = null;
    static Socket socket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static Grocery grocery = new Grocery();

    public static String generateLineWithSeparator(String... args) {
        StringBuilder line = new StringBuilder();
        for (String arg : args) {
            line.append(arg).append("%");
        }
        return line.toString();
    }

    public static void sendResponse(String... args) {
        out.println(generateLineWithSeparator(args));
    }

    public static boolean paramsAreInvalid(int expected, int actual) {
        if (expected != actual) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            server = new ServerSocket(8888);
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNewCategory(String[] queryParts) {
        try {
            System.out.println("SERVER: addNewCategory");
            if (paramsAreInvalid(3, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            String categoryName = queryParts[2];
            grocery.addProductCategory(new Category(categoryId, categoryName));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Category added successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void deleteCategory(String[] queryParts) {
        try {
            System.out.println("SERVER: deleteCategory");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            grocery.deleteProductCategory(categoryId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Category deleted successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void addProductToCategory(String[] queryParts) {
        try {
            System.out.println("SERVER: addProductToCategory");
            if (paramsAreInvalid(6, queryParts.length)) return;
            int productId = Integer.parseInt(queryParts[1]);
            String productName = queryParts[2];
            int productAmount = Integer.parseInt(queryParts[3]);
            int productPrice = Integer.parseInt(queryParts[4]);
            int categoryId = Integer.parseInt(queryParts[5]);
            grocery.addProduct(categoryId, new Product(productId, productName, productAmount, productPrice));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Product added successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void deleteProduct(String[] queryParts) {
        try {
            System.out.println("SERVER: deleteProduct");
            if (paramsAreInvalid(3, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            int productId = Integer.parseInt(queryParts[2]);
            grocery.deleteProduct(categoryId, productId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Product deleted successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void editProduct(String[] queryParts) {
        try {
            System.out.println("SERVER: editProduct");
            if (paramsAreInvalid(7, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            int productId = Integer.parseInt(queryParts[2]);
            String productName = queryParts[3];
            int productPrice = Integer.parseInt(queryParts[4]);
            int productAmount = Integer.parseInt(queryParts[5]);
            grocery.updateProduct(categoryId, productId, new Product(productId, productName, productAmount, productPrice));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Product updated successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void countProductsInCategory(String[] queryParts) {
        try {
            System.out.println("SERVER: countProductsInCategory");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            int count = grocery.countProductsInCategory(categoryId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Count: " + String.valueOf(count));
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

   public static void searchProductByName(String[] queryParts) {
        try {
            System.out.println("SERVER: searchProductByName");
            if (paramsAreInvalid(2, queryParts.length)) return;
            String productName = queryParts[1];
            Product product = grocery.searchProductByName(productName);
            if (product == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Product not found");
            } else {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Product found", product.toString() );
            }

        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void getAllProductsInCategory(String[] queryParts) {
        try {
            System.out.println("SERVER: getAllProductsInCategory");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int categoryId = Integer.parseInt(queryParts[1]);
            Product[] products = grocery.getAllProductsInCategory(categoryId);
            if (products == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Category not found");
            } else {
                for (Product product : products) {
                    sendResponse(product.toString());
                }
            }
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void getAllCategories() {
        try {
            System.out.println("SERVER: showAllCategories");
            Category[] categories = grocery.getAllProductCategories();
            if (categories == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "No categories found");
            } else {
                for (Category category : categories) {
                    sendResponse(category.toString());
                }
            }
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static boolean processQuery() {
        try {
            String query = in.readLine();
            String[] queryParts = parseQuery(query);
            String command = queryParts[0];
            switch (command) {
                case "1" -> addNewCategory(queryParts);
                case "2" -> deleteCategory(queryParts);
                case "3" -> addProductToCategory(queryParts);
                case "4" -> deleteProduct(queryParts);
                case "5" -> editProduct(queryParts);
                case "6" -> countProductsInCategory(queryParts);
                case "7" -> searchProductByName(queryParts);
                case "8" -> getAllProductsInCategory(queryParts);
                case "9" -> getAllCategories();
                case "10" -> {
                    System.out.println("SERVER: Exit");
                    socket.close();
                    server.close();
                    return false;
                }
                default ->  sendResponse(ServerResponse.QUERY_NOT_FOUND.getStatus());
            }
            return true;
        } catch (Exception e) {
            System.out.println("SERVER: Error: " + e.getMessage());
            return false;
        }
    }

    public static String[] parseQuery(String query) {
        return query.split("%");
    }
}
