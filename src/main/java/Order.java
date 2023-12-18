import lombok.Value;

import java.util.List;
@Value
public record Order(
        String id,
        List<Product> products,
        OrderStatus status
) {
}
