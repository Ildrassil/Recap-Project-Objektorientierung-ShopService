import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIds = List.of("1");

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
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);
        List<String> productsIds = List.of("5", "2");





        //THEN
        assertThrows(NoSuchElementException.class,() -> shopService.addOrder(productsIds));
    }

    @Test
    void findOrderByStatusTest_whenOrderStatus_expectListOfOrders(){
        //GIVEN
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);

        List<String> products= List.of("1","2","3","4");

        shopService.addOrder(products);
        shopService.addOrder(products);
        shopService.addOrder(products);

        //When
        int expected = 3;

        //Then
        assertEquals(3,shopService.findOrderByStatus(OrderStatus.PROCESSING).size() );


    }

}
