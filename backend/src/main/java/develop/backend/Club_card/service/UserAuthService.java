package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.user.UserLogInPayload;
import develop.backend.Club_card.controller.payload.user.UserSignUpPayload;
import develop.backend.Club_card.entity.User;

public interface UserAuthService {
    String login(UserLogInPayload userLogInPayload);
    User signup(UserSignUpPayload userSignUpPayload);
}
