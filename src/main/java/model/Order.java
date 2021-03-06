package model;

import enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Double totalPrice;
    private LocalDateTime orderData;
    private OrderStatus status;
    private Long userId;
}
