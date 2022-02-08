package repository.repositoryIMPL;

import model.Category;
import model.SubCategory;
import repository.SubCategoryRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.Main.connection;


public class SubCategoryRepositoryImpl implements SubCategoryRepository {

    @Override
    public Category findById(String name) {
        String SELECT_CATEGORY = "SELECT * FROM category WHERE name = '" + name +"'";
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Category(
                        resultSet.getLong("id"),
                        resultSet.getString("perefix"),
                        resultSet.getString("name"),
                        resultSet.getLong("parent_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exitsById(Long id) {
        String SELECT_CATEGORY = "SELECT * FROM category WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void saveAll(List<Category> categories) throws SQLException {
        for (Category category : categories) {
             Category fndById = findById(category.getName());
             if(fndById == null) {
                 String INSER_CATEGORY = "INSERT INTO category (perefix, name , parent_id) VALUES (?, ?, ?)";
                 PreparedStatement prepareStatement = connection.prepareStatement(INSER_CATEGORY);
                 prepareStatement.setString(1, category.getPerefix());
                 prepareStatement.setString(2, category.getName());
                 prepareStatement.setLong(3, category.getId());
                 prepareStatement.executeUpdate();
             }
        }
    }

    @Override
    public List<Category> findAllCategoryById(long categoryId) {
        List<Category> categories = new ArrayList<>();
        String SELECT_SUBCATEGORY = "SELECT * FROM category WHERE parent_id = ";
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_SUBCATEGORY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                categories.add(new Category(
                resultSet.getLong("id"),
                resultSet.getString("perefix"),
                resultSet.getString("name"),
                resultSet.getLong("parent_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
