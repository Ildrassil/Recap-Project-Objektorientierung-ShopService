import lombok.RequiredArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
@With
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;


    public Order addOrder(List<String> productIds) throws NoSuchElementException {

        List<Product> products = new ArrayList<>();


            for (String productId : productIds) {
                Product productToOrder = productRepo.getProductById(productId).get();
                if (productToOrder == null) {
                    System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                    throw new NoSuchElementException(productId);


                }
                products.add(productToOrder);
            }





        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }
    public List<Order> findOrderByStatus(OrderStatus status){
        List<Order> orderWithSameStatus = new ArrayList<>();
        orderWithSameStatus = orderRepo.getOrders().stream()
                .filter(order -> order.status() == status)
                .collect(Collectors.toList());

        return orderWithSameStatus;


    }
    public Order updateOrder(String orderId){
      return orderRepo.getOrderById(orderId).withStatus(OrderStatus.PACKAGING);


    }
}
