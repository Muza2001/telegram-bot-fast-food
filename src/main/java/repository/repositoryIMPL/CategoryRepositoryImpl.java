package repository.repositoryIMPL;

import model.Category;
import repository.CategoryRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.Main.connection;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public void saveAll(List<Category> categories) throws SQLException {
            for (Category category : categories) {
                Category fndName = findById(category.getName());
                if(fndName == null) {
                    String INSER_CATEGORY = "INSERT INTO category(perefix, name ) values ( ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(INSER_CATEGORY);
                    statement.setString(1, category.getPerefix());
                    statement.setString(2, category.getName());
                    statement.executeUpdate();
                }
            }
    }

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
                   resultSet.getLong("parent_id")
            );
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
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String SELECT_All = "SELECT * from category WHERE parent_id isnull" ;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_All);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                categories.add(new Category(
                    resultSet.getLong( "id"),
                    resultSet.getString("perefix"),
                    resultSet.getString("name"),
                    resultSet.getLong("parent_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
    }
        return categories;
    }

    @Override
    public List<Category> findByIdFor(long categoryId) {
        List<Category> categories = new ArrayList<>();
        String SELECT_CATEGORY_FOR_SUB = "SELECT * FROM category Where parent_id = " + categoryId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_FOR_SUB);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                categories.add(new Category(
                        set.getLong("id"),
                        set.getString("perefix"),
                        set.getString("name"),
                        set.getLong("parent_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
