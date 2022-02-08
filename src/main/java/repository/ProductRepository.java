package repository;

import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {

    Product findById(String name);

    Product findByPrductId(Long id);

    boolean exitsById(Long id);

    void saveAll(List<Product> products) throws SQLException;

    Product findbyCategoryId(long categoryId);

    List<Product> findByProductForId(long showProductId);

    Product findByRandom(Integer productId);
}
