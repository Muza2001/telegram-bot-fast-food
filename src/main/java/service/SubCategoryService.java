package service;

import model.Category;
import java.sql.SQLException;
import java.util.List;

public interface SubCategoryService {

    Category save(Category category);

    void saveAll(List<Category> categories) throws SQLException;

    List<Category> findAll(List<Category> categories);

    Category findById(Long id);

    List<Category> findAllCategoryById(long categoryId);
}
