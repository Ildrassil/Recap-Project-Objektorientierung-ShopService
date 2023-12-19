import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static Map<String, String> ids = new HashMap<>();


    public static void main(String[] args) {
        IdService idService = new IdService();
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderMapRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo,orderMapRepo);

        try {
            //Read File
            Path filePath = Path.of("/Users/jakobschneider/Desktop/NeueFische/Projekte/Recap-Project-Objektorientierung-ShopService/src/main/resources/transaction.txt");
            List<String> lines = Files.readAllLines(filePath);


            //Execute Commands
            for (String line : lines) {
                executeCommand(line, shopService);
            }
            //Catch Exception
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private static void executeCommand(String command, ShopService shopService) {
        // Split the command into tokens
        String[] tokens = command.split("\\s+");

        //IdService idService = new IdService();

        // Execute the command based on the first token
        switch (tokens[0]) {
            case "addOrder":
                if (tokens.length >= 3) {

                    List<String> productIds = Arrays.asList(Arrays.copyOfRange(tokens, 2, tokens.length));
                    Order orderSubService = shopService.addOrder(productIds);
                    ids.put(tokens[1], orderSubService.id());
                }
                break;
            case "setStatus":
                if (tokens.length == 3) {

                    String status = tokens[2];
                    shopService.setStatus(ids.get(tokens[1]), status);
                }
                break;
            case "printOrders":
                List<Order> orders = shopService.printOrders();

                orders.forEach(alias -> System.out.println(alias + ": " + alias.products()));
                break;
            // Add other command cases as needed
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
