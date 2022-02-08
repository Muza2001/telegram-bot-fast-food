package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDto {

    /**
     * Order_Cart data
     */
    private Long id;
    private Integer amount;
    private Double totalPrice;
    private LocalDateTime crated_at;

    /**
     * Product data
     */
    private String product_name;
    private Double product_price;

    public OrderCartDto(Integer amount, Double totalPrice, LocalDateTime crated_at, String product_name, Double product_price) {
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.crated_at = crated_at;
        this.product_name = product_name;
        this.product_price = product_price;
    }
}
