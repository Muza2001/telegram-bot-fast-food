package service.serviceIMPL;

import model.Cart;
import repository.CartRepository;
import repository.repositoryIMPL.CartRepositoryImpl;
import service.CartService;

import java.util.List;

public class CartServiceImpl implements CartService {
    CartRepository cartRepository = new CartRepositoryImpl();
    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public boolean existsByUserId(Long lastSavedId) {
        return cartRepository.existsByUserId(lastSavedId);
    }

    @Override
    public Cart findByUserId(Long user_id) {
        return cartRepository.findByUser(user_id);
    }

    @Override
    public void removeAll(Long id) {
        cartRepository.removeAll(id);
    }
}
