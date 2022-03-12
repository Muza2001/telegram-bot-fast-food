package service.serviceIMPL;

import dto.OrderCartDto;
import model.OrderCart;
import repository.OrderCartRepository;
import repository.repositoryIMPL.OrderCartRepositoryImpl;
import service.OrderCartService;

import java.sql.SQLException;
import java.util.List;

public class OrderCartServiceImpl implements OrderCartService {
   public static OrderCartRepository orderCartRepository = new OrderCartRepositoryImpl();
    @Override
    public void save(OrderCart orderCart) {
        orderCartRepository.save(orderCart);
    }

    @Override
    public List<OrderCart> findAll() {
        return null;
    }

    @Override
    public OrderCart findById(Long id) {
        return orderCartRepository.findById(id);
    }

    @Override
    public OrderCart findByCartAndProduct(Long id, long productId) throws SQLException {
        return orderCartRepository.findByCartAndProduct(id, productId);
    }

    @Override
    public List<OrderCartDto> findByCartId(Long cartId) {
        return orderCartRepository.findByCartId(cartId);
    }

    @Override
    public Double AllProductTotalPrice(Long id) {
        return orderCartRepository.AllProductTotalPrice(id);
    }
}
