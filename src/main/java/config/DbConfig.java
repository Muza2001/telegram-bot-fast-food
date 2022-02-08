package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

    public static Connection connect(String dbname) throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/"+dbname;
        String username = "postgres";
        String password = "0075336";
        return DriverManager.getConnection(url,username,password);
    }
}
