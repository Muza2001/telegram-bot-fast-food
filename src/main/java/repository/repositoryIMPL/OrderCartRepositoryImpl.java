package repository.repositoryIMPL;

import dto.OrderCartDto;
import enums.OrderCartStatus;
import model.OrderCart;
import repository.OrderCartRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static main.Main.connection;

public class OrderCartRepositoryImpl implements OrderCartRepository {

    @Override
    public void save(OrderCart orderCart) {
        String INSERT_INTO_ORDERCART ="Insert into order_cart (cart_id, product_id, amount, total_price" +
                ", status, deleted) values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_ORDERCART);
            statement.setLong(1,orderCart.getCartId());
            statement.setLong(2,orderCart.getProductId());
            statement.setInt(3, orderCart.getAmount());
            statement.setDouble(4, orderCart.getTotalPrice());
            statement.setString(5, orderCart.getStatus().toString());
            statement.setBoolean(6, orderCart.getDeleted());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OrderCart> findAll() {
        return null;
    }

    @Override
    public OrderCart findById(Long id) {
        String ORDER_CART_FOR_INFORMATION = "SELECT * FROM order_cart where cart_id =" + id;
        try {
            PreparedStatement statement = connection.prepareStatement(ORDER_CART_FOR_INFORMATION);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                       return new OrderCart(
                                resultSet.getLong("id"),
                                resultSet.getLong("cart_id"),
                                resultSet.getLong("product_id"),
                                resultSet.getInt("amount"),
                                resultSet.getDouble("total_price"),
                                OrderCartStatus.fromName(resultSet.getString("status")),
                                resultSet.getTimestamp("crated_at").toLocalDateTime(),
                                resultSet.getBoolean("deleted")
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderCart findByCartAndProduct(Long id, long productId) {
        return null;
    }

    @Override
    public List<OrderCartDto> findByCartId(Long cartId) {
        String SELECT_ALL_CART_ID = "select\n" +
                "    oc.id,\n" +
                "    oc.amount,\n" +
                "    oc.total_price,\n" +
                "    oc.crated_at,\n" +
                "    p.name,\n" +
                "    p.price\n" +
                "        from order_cart oc inner join product p on p.id = oc.product_id\n" +
                "        where cart_id = " + cartId;
        List<OrderCartDto> orderCartDtos = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CART_ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                orderCartDtos.add(new OrderCartDto(
                    resultSet.getLong("id"),
                    resultSet.getInt("amount"),
                    resultSet.getDouble("total_price"),
                        resultSet.getTimestamp("crated_at").toLocalDateTime(),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return orderCartDtos;
    }

    @Override
    public Double AllProductTotalPrice(Long id) {
        Double totalPrice = 0d;
        String SELECT_ORDER = "Select * from order_cart where id = " + id;
        OrderCart cart = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                cart = new OrderCart(
                        resultSet.getLong("id"),
                        resultSet.getLong("cart_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("total_price"),
                        OrderCartStatus.fromName(resultSet.getString("status")),
                        resultSet.getTimestamp("crated_at").toLocalDateTime(),
                        resultSet.getBoolean("deleted")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            if(cart != null){
                totalPrice += ( cart.getTotalPrice() * cart.getAmount());
            }


        return totalPrice;
    }
}
