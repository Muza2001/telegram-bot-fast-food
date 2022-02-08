package service;

import model.User;
import org.telegram.telegrambots.meta.api.objects.Location;

public interface UserService {

    Long save(User user);

    boolean exitsByChatId(Long chatId);

    User findByChatId(Long chatId);

    void update(User user);

    void saveNewContact(String phoneNumber, Long id);

    void updateLocation(Long chatId, Location location);
}
