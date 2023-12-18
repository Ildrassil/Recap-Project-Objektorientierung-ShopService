import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIdOrder1 = List.of("1","2","1","2","4","4");
        List<String> productsIdOrder2 = List.of("1","1","1","2","3","3");
        List<String> productsIdOrder3 = List.of("2","2","2","2","4","4");
        shopService.addOrder(productsIdOrder1);
        shopService.addOrder(productsIdOrder2);
        shopService.addOrder(productsIdOrder3);


    }
}
