package main;

import bot.Fast_Food_Uz_Bot;
import config.DbConfig;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.serviceIMPL.StoreDataToFromJson;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static Connection connection;

    static {
        try {
            connection = DbConfig.connect("fast_food_uz_bot");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TelegramApiException, SQLException {
        StoreDataToFromJson.store();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new Fast_Food_Uz_Bot());
    }
}
