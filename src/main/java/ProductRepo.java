import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {
    private List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
        products.add(new Product("1", "Apfel"));
        products.add(new Product("4","Birne"));
        products.add(new Product("2","Zwiebel"));
        products.add(new Product("3","Pilze"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional<Product> getProductById(String id) {
        for (Product product : products) {
            if (product.id().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.ofNullable(null);


    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
        for (Product product : products) {
           if (product.id().equals(id)) {
               products.remove(product);
               return;
           }
        }
    }
}
