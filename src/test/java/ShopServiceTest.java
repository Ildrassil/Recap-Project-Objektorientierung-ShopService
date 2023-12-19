import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        IdService idService = new IdService();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIds = List.of("1");
        String id = idService.genrateId();

        //WHEN
        Order actual = shopService.addOrder(id,productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING, ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {


        //GIVEN
        IdService idService = new IdService();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIds = List.of("5", "2");
        String id = idService.genrateId();





        //THEN
        assertThrows(NoSuchElementException.class,() -> shopService.addOrder(id,productsIds));
    }

    @Test
    void findOrderByStatusTest_whenOrderStatus_expectListOfOrders(){
        //GIVEN
        IdService idService = new IdService();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);


        //then
        List<String> products= List.of("1","2","3","4");
        String id = idService.genrateId();
        String id2 = idService.genrateId();
        String id3 = idService.genrateId();
        shopService.addOrder(id,products);
        shopService.addOrder(id2,products);
        shopService.addOrder(id3,products);
        shopService.updateOrder(id3);

        //When
        int expected = 2;

        //Then
        assertEquals(2,shopService.findOrderByStatus(OrderStatus.PROCESSING).size() );


    }
    @Test
    void getOldestOrderByStatus(){
        //GIVEN
        IdService idService = new IdService();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);

        //then
        List<String> products= List.of("1","2","3","4");
        String id = idService.genrateId();
        String id2 = idService.genrateId();
        String id3 = idService.genrateId();
        Order order1 = shopService.addOrder(id,products);
        Order order2 = shopService.addOrder(id2,products);
        Order order3 = shopService.addOrder(id3,products);
        order1 = shopService.updateOrder(id);

        Map<OrderStatus,Order> expected = new HashMap<>();

        expected.put(OrderStatus.PROCESSING,order2);
        expected.put(OrderStatus.PACKAGING,order1);


        //WHEN
        assertEquals(expected,shopService.getOldestOrderPerStatus());

    }

}
