package repository;

import dto.OrderCartDto;
import model.OrderCart;

import java.util.List;

public interface OrderCartRepository {
    void save(OrderCart orderCart);
    List<OrderCart> findAll();
    OrderCart findById(Long id);

    OrderCart findByCartAndProduct(Long id, long productId);

    List<OrderCartDto> findByCartId(Long cartId);

    Double AllProductTotalPrice(Long id);
}
