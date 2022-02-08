package repository.repositoryIMPL;

import model.Product;
import repository.ProductRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.Main.connection;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Product findById(String name) {
        String SELECT_CATEGORY = "SELECT * FROM product WHERE name = '" + name +"'";
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("composition"),
                        resultSet.getDouble("price"),
                        resultSet.getString("imageurl"),
                        resultSet.getLong("category_id")
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exitsById(Long id) {
        return false;
    }

    @Override
    public void saveAll(List<Product> products) throws SQLException {
            for (Product product : products) {
                Product fndById = findById(product.getName());
                if(fndById == null){
                    String INSER_CATEGORY = "INSERT INTO product(name, composition, price, imageurl, category_id ) values ( ?, ?," +
                            " ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(INSER_CATEGORY);
                    statement.setString(1,product.getName());
                    statement.setString(2,product.getComposition());
                    statement.setDouble(3, product.getPrice());
                    statement.setString(4, product.getImageUrl());
                    statement.setLong(5, product.getCategory_id());
                    statement.executeUpdate();
                }}
            }

    @Override
    public Product findByPrductId(Long id) {
        String SELECT_PRODUCT_ALL = "SELECT * FROM product WHERE id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_ALL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("composition"),
                        resultSet.getDouble("price"),
                        resultSet.getString("imageurl"),
                        resultSet.getLong("category_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findByProductForId(long showProductId) {
        String SELECT_ALL_PRODUCT = "select * from product where category_id = " + showProductId;
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                products.add(new Product(
                       resultSet.getLong("id"),
                       resultSet.getString("name"),
                       resultSet.getString("composition"),
                       resultSet.getDouble("price"),
                       resultSet.getString("imageurl"),
                       resultSet.getLong("category_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product findbyCategoryId(long categoryId) {
        String SELECT_PRODUCT_ALL = "SELECT * FROM product WHERE category_id = " + categoryId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_ALL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("composition"),
                        resultSet.getDouble("price"),
                        resultSet.getString("imageurl"),
                        resultSet.getLong("category_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product findByRandom(Integer productId) {
        String SELECT_PRODUCT_ALL = "SELECT * FROM product WHERE id = " + productId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCT_ALL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("composition"),
                        resultSet.getDouble("price"),
                        resultSet.getString("imageurl"),
                        resultSet.getLong("category_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    }


