package service.serviceIMPL;

import model.User;
import org.telegram.telegrambots.meta.api.objects.Location;
import repository.UserRepasitory;
import repository.repositoryIMPL.UserRepasitoryImpl;
import service.UserService;

public class UserServiceImpl implements UserService {

    public static UserRepasitory userRepasitory = new UserRepasitoryImpl();
    @Override
    public Long save(User user) {
      return userRepasitory.save(user);
    }

    @Override
    public boolean exitsByChatId(Long chatId) {
        return userRepasitory.exitsByChatId(chatId);
    }

    @Override
    public User findByChatId(Long chatId) {
        return userRepasitory.findByChatId(chatId);
    }

    @Override
    public void update(User user) {
        userRepasitory.update(user);
    }

    @Override
    public void saveNewContact(String phoneNumber, Long id) {
        userRepasitory.saveNewContact(phoneNumber, id);
    }

    @Override
    public void updateLocation(Long chatId, Location location) {
        userRepasitory.updateLocation(chatId, location);
    }
}
