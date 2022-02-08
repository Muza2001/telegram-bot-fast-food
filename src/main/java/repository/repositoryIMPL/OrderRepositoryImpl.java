package repository.repositoryIMPL;

import enums.OrderStatus;
import repository.OrderRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.Main.connection;

public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public void saveOrder(Double totalPrice, Long id) {
        String SAVE_ORDER = "INSERT INTO order_for_user (total_pruce, status, user_id) values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_ORDER);
            statement.setDouble(1, totalPrice);
            statement.setString(2, OrderStatus.ON_THE_WAY.toString());
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
