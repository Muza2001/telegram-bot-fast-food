package service.serviceIMPL;

import model.OrderCart;
import repository.OrderDeteilRepository;
import repository.repositoryIMPL.OrderDeteilRepositoryimpl;
import service.OrderDetailService;

import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    OrderDeteilRepository orderDeteilRepository = new OrderDeteilRepositoryimpl();
    @Override
    public void saveDetailForProduct(OrderCart orderCarts) {
        orderDeteilRepository.saveDetailForProduct(orderCarts);
    }
}
