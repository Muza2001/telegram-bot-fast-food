package repository;

public interface OrderRepository {

    void saveOrder(Double totalPrice, Long id);
}
