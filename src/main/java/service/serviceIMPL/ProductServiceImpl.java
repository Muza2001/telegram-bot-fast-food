package service.serviceIMPL;

import model.Category;
import model.Product;
import repository.ProductRepository;
import repository.repositoryIMPL.ProductRepositoryImpl;
import service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product findByProductId(Long id) {
        return productRepository.findByPrductId(id);
    }

    @Override
    public void saveAll(List<Product> product) throws SQLException {
        productRepository.saveAll(product);
    }

    @Override
    public List<Product> findAll(List<Category> product) {
        return null;
    }


    @Override
    public Product findbyCategoryId(long categoryId) {
        return productRepository.findbyCategoryId(categoryId);
    }

    @Override
    public List<Product> findByProductForId(long showProductId) {
        return productRepository.findByProductForId(showProductId);
    }

    @Override
    public Product findByRandom(Integer productId) {
        return productRepository.findByRandom(productId);
    }
}
