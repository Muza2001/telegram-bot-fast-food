package service;

import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product findByProductId(Long id);

    void saveAll(List<Product> product) throws SQLException;

    List<Product> findAll(List<Category> product);

    Product findbyCategoryId(long categoryId);

    List<Product> findByProductForId(long showProductId);

    Product findByRandom(Integer productId);
}
