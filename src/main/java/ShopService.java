import lombok.RequiredArgsConstructor;
import lombok.With;


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
        IdService idService = new IdService();



        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId).get();
            if (productToOrder == null) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                throw new NoSuchElementException(productId);


            }
            products.add(productToOrder);
        }


        Order newOrder = new Order(idService.genrateId(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> findOrderByStatus(OrderStatus status) {
        List<Order> orderWithSameStatus = new ArrayList<>();
        orderWithSameStatus = orderRepo.getOrders()
                .stream()
                .filter(order -> order.status() == status)
                .collect(Collectors.toList());

        return orderWithSameStatus;


    }

    public Order updateOrder(String orderId) {
        Order order1 = orderRepo.getOrderById(orderId).withStatus(OrderStatus.PACKAGING);
        orderRepo.removeOrder(orderId);
        return orderRepo.addOrder(order1);


    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {

        List<OrderStatus> status = List.of(OrderStatus.values());


        return status.stream()
                .map(status1 -> findOrderByStatus(status1)
                        .stream()
                        .sorted(Comparator.comparing(Order::time))
                        .collect(Collectors.toList()))
                .filter(orders -> !orders.isEmpty())
                .map(orders -> orders.get(0))
                .collect(Collectors.toMap(Order::status, order -> order));


    }

    public void setStatus(String orderId,String status){
        Order order1 = orderRepo.getOrderById(orderId).withStatus(OrderStatus.valueOf(status));
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(order1);


    }
    public List<Order> printOrders(){
        return orderRepo.getOrders();
    }

}
