import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;



public record Order(
        String id,
        List<Product> products,
        OrderStatus status
) {
}
