package repository.repositoryIMPL;

import model.OrderCart;
import repository.OrderDeteilRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static main.Main.connection;

public class OrderDeteilRepositoryimpl implements OrderDeteilRepository {

    @Override
    public void saveDetailForProduct(OrderCart cart) {
        String INSERT_INTO_FOR_ORDER_DETEIL = "INSERT INTO order_deteil (order_id, product_id, amount, total_price) values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_FOR_ORDER_DETEIL);
                    statement.setLong(1, cart.getCartId());
                    statement.setLong(2, cart.getProductId());
                    statement.setInt(3, cart.getAmount());
                    statement.setDouble(4, cart.getTotalPrice());
                    statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
