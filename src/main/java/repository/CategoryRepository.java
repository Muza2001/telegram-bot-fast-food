package repository;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryRepository {

    Category findById(String name);

    boolean exitsById(Long id);

    void saveAll(List<Category> categories) throws SQLException;

    List<Category> findAll();

    List<Category> findByIdFor(long categoryId);
}
