package develop.backend.Club_card.services;

import develop.backend.Club_card.controllers.payload.UserUpdatePayload;
import develop.backend.Club_card.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User getCurrentUser(UserDetails userDetails);
    void deleteCurrentUser(UserDetails userDetails);
    void updateCurrentUserData(UserDetails userDetails, UserUpdatePayload userUpdatePayload);
}