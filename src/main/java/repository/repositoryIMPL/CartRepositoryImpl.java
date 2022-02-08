package repository.repositoryIMPL;

import model.Cart;
import repository.CartRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static main.Main.connection;

public class CartRepositoryImpl implements CartRepository {

    public void save(Cart cart) {
        String INSERT_NEW_CART = "INSERT INTO cart (user_id) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CART);
            statement.setLong(1,cart.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        String SELECT_USER_FOR_ID = "select * from cart where user_id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_FOR_ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Cart(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean existsByUserId(Long lastSavedId) {
        return false;
    }

    @Override
    public Cart findByUser(Long id) {
        String SELECT_USER_FOR_ID = "select * from cart where user_id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_FOR_ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Cart(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void removeAll(Long id) {
        String DELETE_CART_ID = "Delete from order_cart where cart_id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CART_ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
