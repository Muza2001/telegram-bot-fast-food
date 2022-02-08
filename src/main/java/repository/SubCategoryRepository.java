package repository;


import model.Category;
import model.SubCategory;

import java.sql.SQLException;
import java.util.List;

public interface SubCategoryRepository {

    Category findById(String name);

    boolean exitsById(Long id);

    void saveAll(List<Category> categories) throws SQLException;

    List<Category> findAllCategoryById(long categoryId);
}
