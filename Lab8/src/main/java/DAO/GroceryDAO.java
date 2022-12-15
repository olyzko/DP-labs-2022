package DAO;

import Grocery.Product;
import Grocery.Category;

import java.sql.*;
import java.util.ArrayList;

public class GroceryDAO {
    private final String DB_URL = "jdbc:mysql://localhost:3306/grocery";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "root";

    Statement statement = null;
    Connection connection = null;

    public GroceryDAO() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        statement = connection.createStatement();
    }

    public int findCategoryId(String categoryName) throws SQLException {
        String idQuery = "SELECT id FROM grocery.categories WHERE categoryName = '" + categoryName + "'";
        ResultSet idSet = statement.executeQuery(idQuery);
        idSet.next();

        return idSet.getInt("id");
    }

    public void addProduct(String categoryName, String productName, int price, int amount) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "INSERT INTO grocery.products (categoryId, productName, price, amount) VALUES (" + categoryId + ", '" + productName + "', " + price + ", " + amount + ")";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductCategory(String categoryName) {
        try {
            String query = "INSERT INTO grocery.categories (categoryName) VALUES ('" + categoryName + "')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(int productId) {
        try {
            String query = "DELETE FROM grocery.products WHERE id = " + productId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProductCategory(String categoryName) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "DELETE FROM grocery.categories WHERE id = " + categoryId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(int productId, String categoryName, String productName, int price, int amount) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "UPDATE grocery.products SET categoryId = " + categoryId + ", productName = '" + productName + "', price = " + price + ", amount = " + amount + " WHERE id = " + productId;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countProductsInCategory(String categoryName) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "SELECT COUNT(*) FROM grocery.products WHERE categoryId = " + categoryId;
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product searchProductByName(String name) {
        try {
            String query = "SELECT * FROM grocery.products WHERE productName = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            Product product = new Product(resultSet.getInt("id"), resultSet.getString("productName"), resultSet.getInt("amount"), resultSet.getInt("price"));
            resultSet.close();
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> getAllProductsInCategory(String categoryName) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "SELECT * FROM grocery.products WHERE categoryId = " + categoryId;
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Product> products = new ArrayList<Product>();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"), resultSet.getString("productName"), resultSet.getInt("amount"), resultSet.getInt("price")));
             }
            resultSet.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Category> getAllCategories() {
        try {
            String query = "SELECT * FROM grocery.categories";
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Category> categories = new ArrayList<Category>();
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("categoryName")));
            }
            resultSet.close();
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
