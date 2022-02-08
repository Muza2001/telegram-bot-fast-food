package repository.repositoryIMPL;

import enums.BootState;
import model.User;
import org.telegram.telegrambots.meta.api.objects.Location;
import repository.UserRepasitory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static main.Main.connection;

public class UserRepasitoryImpl implements UserRepasitory {

    @Override
    public boolean exitsByChatId(Long chat_id) {
        String SELECT_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id =" + chat_id;
        try {
           PreparedStatement statement = connection.prepareStatement(SELECT_BY_CHAT_ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Long save(User user) {
        String INSERT_INTO = "INSERT INTO users(chat_id, first_name," +
                " last_name, user_name, phone_number, bot_state) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO);
            statement.setLong(1,user.getChatId());
            statement.setString(2,user.getFirstname());
            statement.setString(3,user.getLastname());
            statement.setString(4,user.getUsername());
            statement.setString(5,user.getPhone());
            statement.setString(6,user.getBootState().name());
            statement.executeUpdate();

            String SELECT_LAST_SAVED = "select * from users order by id desc limit 1";
            statement = connection.prepareStatement(SELECT_LAST_SAVED);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByChatId(Long chatId) {
        String SELECT_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id =" + chatId;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CHAT_ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new User(
                       resultSet.getLong("id"),
                       resultSet.getLong("chat_id"),
                       resultSet.getString("first_name") ,
                       resultSet.getString("last_name") ,
                       resultSet.getString("user_name") ,
                       resultSet.getString("phone_number"),
                       BootState.fromString(resultSet.getString("bot_state"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update( User user) {
        String UPDATE_USER_DATA = "UPDATA users SET first_name = ?, last_name = ?," +
                " username = ?, bot_state = ? WHERE id = " + user.getId();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DATA);
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getBootState().name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveNewContact(String phoneNumber, Long id) {
        String SAVE_CONTACT = "UPDATE users SET phone_number = '" + phoneNumber +
                "' where id = " + id;
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_CONTACT);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLocation(Long chatId, Location location) {
        String SAVE_LOCATION_LAT_LONG = "UPDATE users SET current_latitude = +" + location.getLatitude() +" where chat_id =" + chatId + ";"
                + "UPDATE users SET current_longitude = + " + location.getLongitude() + " where chat_id = + " + chatId +";";
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_LOCATION_LAT_LONG);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
