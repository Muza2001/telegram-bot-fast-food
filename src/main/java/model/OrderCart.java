package model;

import enums.OrderCartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCart {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer amount;
    private Double totalPrice;
    private OrderCartStatus status;
    private LocalDateTime created_At;

    /**
     * deleted status - true (CANCELED or ORDER_SENT)
     *         status - false (OPEN)
     */
    private Boolean deleted;

    public OrderCart(Long cartId, Long productId, Integer amount,
                     Double totalPrice, OrderCartStatus status, Boolean deleted) {
        this.cartId = cartId;
        this.productId = productId;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.deleted = deleted;
    }
}
