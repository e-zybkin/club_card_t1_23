package develop.backend.Club_card.service;

import develop.backend.Club_card.entity.User;

public interface UserAuthService {
    String login(String username, String password);
    User signup(String username, String password, String email);
}
