package xml;

import java.util.ArrayList;

public class Grocery {
    public final ArrayList<Category> categories = new ArrayList<Category>();

    public void showAllCategories() {
        for (Category category : this.categories) {
            System.out.println(category);
        }
    }

    public void showAllProducts() {
        for (Category category : this.categories) {
            System.out.println("Category: " + category.getCategoryName());
            for (Product product : category.getProducts()) {
                System.out.println(product);
            }
        }
    }

    public void addCategory(Category category) {
        for (Category c : this.categories) {
            if (c.getCategoryId() == category.getCategoryId() || c.getCategoryName().equals(category.getCategoryName())) {
                System.out.println("Product category already exists");
                return;
            }
        }
        this.categories.add(category);
    }

    public void updateCategory(int categoryId, String categoryName) {
        for (Category c : this.categories) {
            if (c.getCategoryId() == categoryId) {
                c.setCategoryName(categoryName);
                return;
            }
        }
    }

    public void deleteCategory(int categoryId) {
        for (Category c : this.categories) {
            if (c.getCategoryId() == categoryId) {
                this.categories.remove(c);
                return;
            }
        }
    }

    public void addProduct(int categoryId, Product product) {
        for (Category category : this.categories) {
            if (category.getCategoryId() == categoryId) {
                for (Product p : category.getProducts()) {
                    if (p.getProductCode() == product.getProductCode() || p.getProductName().equals(product.getProductName())) {
                        System.out.println("Product already exists");
                        return;
                    }
                }
                category.addProduct(product);
                return;
            }
        }
    }

    public void updateProduct(int categoryId, int productCode, Product input) {
        for (Category category : this.categories) {
            if (category.getCategoryId() == categoryId) {
                int counter = 0;
                for (Product product : category.getProducts()) {
                    if (product.getProductCode() == productCode) {
                        product.setProductName(input.getProductName());
                        product.setPrice(input.getPrice());
                        product.setAmount(input.getAmount());
                        return;
                    }
                }
            }
        }
    }

    public void deleteProduct(int categoryId, int productCode) {
        for (Category category : this.categories) {
            if (category.getCategoryId() == categoryId) {
                int counter = 0;
                for (Product product : category.getProducts()) {
                    if (product.getProductCode() == productCode) {
                        category.deleteProduct(counter);
                        return;
                    }
                    counter++;
                }
            }
        }
    }
}
