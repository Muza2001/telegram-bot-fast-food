package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long Id;
    private Long userId;

    public Cart(Long userId) {
        this.userId = userId;
    }
}
