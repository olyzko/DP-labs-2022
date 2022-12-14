package xml;

public class Product {
    public int productCode;
    public String productName;
    public int amount;
    public int price;

    public Product(int productCode, String productName, int amount, int price) {
        this.productCode = productCode;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public int getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String toString() {
        return "Product Code: " + productCode + ", Product Name: " + productName + ", Amount: " + amount + ", Price: " + price;
    }
}
