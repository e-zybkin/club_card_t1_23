package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.UserLogInPayload;
import develop.backend.Club_card.controller.payload.UserSignUpPayload;
import develop.backend.Club_card.entity.User;

public interface UserAuthService {
    String login(UserLogInPayload userLogInPayload);
    User signup(UserSignUpPayload userSignUpPayload);
}
