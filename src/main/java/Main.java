import java.util.List;

public class Main {
    public static void main(String[] args) {
        IdService idService = new IdService();
        String Id1 = idService.genrateId();
        String Id2 = idService.genrateId();
        String Id3 = idService.genrateId();
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIdOrder1 = List.of("1","2","1","2","4","4");
        List<String> productsIdOrder2 = List.of("1","1","1","2","3","3");
        List<String> productsIdOrder3 = List.of("2","2","2","2","4","4");
        shopService.addOrder(Id1,productsIdOrder1);
        shopService.addOrder(Id2,productsIdOrder2);
        shopService.addOrder(Id3,productsIdOrder3);


    }
}
