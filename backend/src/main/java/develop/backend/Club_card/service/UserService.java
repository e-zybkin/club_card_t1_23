package develop.backend.Club_card.service;

import develop.backend.Club_card.controller.payload.user.UserUpdatePayload;
import develop.backend.Club_card.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User getCurrentUser(UserDetails userDetails);
    User updateCurrentUserData(UserDetails userDetails, UserUpdatePayload userUpdatePayload);
    void makeDeletionRequest(UserDetails userDetails);
}