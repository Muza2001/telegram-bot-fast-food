package repository;

import model.User;
import org.telegram.telegrambots.meta.api.objects.Location;

public interface UserRepasitory {

    boolean exitsByChatId(Long id);

    Long save(User user);

    User findByChatId(Long chatId);

    void update( User user);

    void saveNewContact(String phoneNumber, Long id);

    void updateLocation(Long chatId, Location location);
}
