import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Builder
@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus status,
        ZonedDateTime time
) {

}
