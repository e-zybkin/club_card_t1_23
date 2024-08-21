package develop.backend.Club_card.services;

import develop.backend.Club_card.models.User;

import java.util.AbstractMap;
import java.util.Date;

public interface UserAuthService {
    String login(String username, String password);
    AbstractMap.SimpleEntry<User, String> signup(String username, String password, String email);
}
