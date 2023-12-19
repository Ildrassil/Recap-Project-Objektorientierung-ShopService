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
        Order actual = shopService.addOrder(productsIds);

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
        assertThrows(NoSuchElementException.class,() -> shopService.addOrder(productsIds));
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

        shopService.addOrder(products);
        shopService.addOrder(products);
        Order order3 =shopService.addOrder(products);
        shopService.updateOrder(order3.id());

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

        Order order1 = shopService.addOrder(products);
        Order order2 = shopService.addOrder(products);
        Order order3 = shopService.addOrder(products);
        order1 = shopService.updateOrder(order1.id());

        Map<OrderStatus,Order> expected = new HashMap<>();

        expected.put(OrderStatus.PROCESSING,order2);
        expected.put(OrderStatus.PACKAGING,order1);


        //WHEN
        assertEquals(expected,shopService.getOldestOrderPerStatus());

    }

}
