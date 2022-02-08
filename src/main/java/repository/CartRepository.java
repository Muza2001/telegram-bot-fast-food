package repository;

import model.Cart;

import java.util.List;

public interface CartRepository {

    void save(Cart cart);

    List<Cart> findAll();

    Cart findById(Long id);

    Cart findByUser(Long id);

    boolean existsByUserId(Long lastSavedId);

    void removeAll(Long id);
}
