package sql;

import java.sql.*;

public class Grocery {
    Statement statement = null;
    Connection connection = null;

    public Grocery(String url, String user, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public int findCategoryId(String categoryName) throws SQLException {
        String idQuery = "SELECT id FROM grocery.categories WHERE categoryName = '" + categoryName + "'";
        ResultSet idSet = statement.executeQuery(idQuery);
        idSet.next();

        return idSet.getInt("id");
    }

    public void showAllCategories() {
        try {
            String query = "SELECT * FROM grocery.categories";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Category ID: " + resultSet.getInt("id") + ", Category Name: " + resultSet.getString("categoryName"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllProducts() {
        try {
            String query = "SELECT * FROM grocery.products";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Product ID: " + resultSet.getInt("id") + ", Product Name: " + resultSet.getString("productName") + ", Amount: " +  resultSet.getInt("amount") + ", Price: " + resultSet.getInt("price"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllProductsInCategory(String categoryName) {
        try {
            int categoryId = findCategoryId(categoryName);
            String query = "SELECT * FROM grocery.products WHERE categoryId = " + categoryId;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Product ID: " + resultSet.getInt("id") + ", Product Name: " + resultSet.getString("productName") + ", Amount: " +  resultSet.getInt("amount") + ", Price: " + resultSet.getInt("price"));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void addCategory(String categoryName) {
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

    public void deleteCategory(String categoryName) {
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

    public void updateCategory(String oldCategoryName, String newCategoryName) {
        try {
            int categoryId = findCategoryId(oldCategoryName);
            String query = "UPDATE grocery.categories SET categoryName = '" + newCategoryName + "' WHERE id = " + categoryId;
            statement.executeUpdate(query);
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
