package service;

import model.Cart;

import java.util.List;

public interface CartService {

    void save(Cart cart);

    List<Cart> findAll();

    Cart findById(Long id);

    Cart findByUserId(Long user_id);

    boolean existsByUserId(Long lastSavedId);

    void removeAll(Long id);
}
