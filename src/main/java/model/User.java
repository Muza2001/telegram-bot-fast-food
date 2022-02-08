package model;

import enums.BootState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private Long chatId;
    private String firstname;
    private String lastname;
    private String username;
    private String phone;
    private BootState bootState;

    private Double currentLatitude;
    private Double currentLongitude;

    public User(Long id, Long chatId, String firstname, String lastname,
                String username, String phone, BootState bootState) {
        this.id = id;
        this.chatId = chatId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phone = phone;
        this.bootState = bootState;
    }

    public User(Long chatId, String firstname, String lastname, String username,
                String phone, BootState bootState) {
        this.chatId = chatId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phone = phone;
        this.bootState = bootState;
    }
}
