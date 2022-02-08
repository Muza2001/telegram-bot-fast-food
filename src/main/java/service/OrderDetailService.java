package service;

import model.OrderCart;

import java.util.List;

public interface OrderDetailService {
    void saveDetailForProduct(OrderCart orderCarts);
}
