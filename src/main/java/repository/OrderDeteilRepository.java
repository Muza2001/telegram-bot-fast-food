package repository;

import model.OrderCart;

import java.util.List;

public interface OrderDeteilRepository {
    void saveDetailForProduct(OrderCart orderCarts);
}
