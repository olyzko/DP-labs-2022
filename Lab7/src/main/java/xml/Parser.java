package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Objects;

public class Parser {
    private static DocumentBuilder db = null;

    public static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException exception) {
            System.out.println("Warning: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }

        public void error(SAXParseException exception) {
            System.out.println("Error: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("Fatal error: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }
    }

    public static Grocery loadData(String path) {
        Grocery grocery = new Grocery();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        dbf.setValidating(true);

        try {
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new Parser.SimpleErrorHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            doc = db.parse(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Element root = Objects.requireNonNull(doc).getDocumentElement();
        if (root.getNodeName().equals("Grocery")) {
            NodeList productCategories = root.getElementsByTagName("Category");
            for (int i = 0; i < productCategories.getLength(); i++) {
                Element productCategory = (Element) productCategories.item(i);

                int categoryId = Integer.parseInt(productCategory.getAttribute("id"));
                String categoryName = productCategory.getAttribute("categoryName");
                Category category = new Category(categoryId, categoryName);

                NodeList products = productCategory.getElementsByTagName("Product");
                for (int j = 0; j < products.getLength(); j++) {
                    Element product = (Element) products.item(j);
                    Product newProduct = new Product(
                            Integer.parseInt(product.getAttribute("id")),
                            product.getAttribute("productName"),
                            Integer.parseInt(product.getAttribute("price")),
                            Integer.parseInt(product.getAttribute("amount"))
                    );
                    category.addProduct(newProduct);
                }
                grocery.categories.add(category);
            }
        }
        return grocery;
    }

    public static void saveData(String path, Grocery grocery) throws TransformerException {
        Document doc = db.newDocument();
        Element root = doc.createElement("Grocery");
        doc.appendChild(root);

        for (Category category : grocery.categories) {
            Element productCategory = doc.createElement("Category");
            productCategory.setAttribute("id", String.valueOf(category.getCategoryId()));
            productCategory.setAttribute("categoryName", category.getCategoryName());
            root.appendChild(productCategory);

            for (Product product : category.getProducts()) {
                Element productElement = doc.createElement("Product");
                productElement.setAttribute("id", String.valueOf(product.getProductCode()));
                productElement.setAttribute("productName", product.getProductName());
                productElement.setAttribute("price", String.valueOf(product.getPrice()));
                productElement.setAttribute("amount", String.valueOf(product.getAmount()));
                productCategory.appendChild(productElement);
            }
        }

        Source source = new DOMSource(doc);
        Result result = new StreamResult(new File(path));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,  "grocery.dtd" );
        transformer.transform(source, result);
    }
}
