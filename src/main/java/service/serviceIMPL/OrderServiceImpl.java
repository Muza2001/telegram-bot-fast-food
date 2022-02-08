package service.serviceIMPL;

import repository.OrderRepository;
import repository.repositoryIMPL.OrderRepositoryImpl;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository = new OrderRepositoryImpl();
    @Override
    public void saveOrder(Double totalPrice, Long id) {
        orderRepository.saveOrder(totalPrice, id);
    }
}
