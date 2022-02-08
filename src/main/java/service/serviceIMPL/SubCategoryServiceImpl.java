package service.serviceIMPL;

import model.Category;
import repository.SubCategoryRepository;
import repository.repositoryIMPL.SubCategoryRepositoryImpl;
import service.SubCategoryService;

import java.sql.SQLException;
import java.util.List;

public class SubCategoryServiceImpl implements SubCategoryService {
    SubCategoryRepository subCategoryRepository = new SubCategoryRepositoryImpl();

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void saveAll(List<Category> categories) throws SQLException {
        subCategoryRepository.saveAll(categories);
    }

    @Override
    public List<Category> findAll(List<Category> categories) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public List<Category> findAllCategoryById(long categoryId) {
        return subCategoryRepository.findAllCategoryById(categoryId);
    }

}
