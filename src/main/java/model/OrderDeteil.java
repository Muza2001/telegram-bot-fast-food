package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeteil {
    private Long id;
    private Long order_id;
    private Long product_id;
    private Integer amount;
    private Double totalPrice;
}
