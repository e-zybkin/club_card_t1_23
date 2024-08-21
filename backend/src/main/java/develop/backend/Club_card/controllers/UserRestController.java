package develop.backend.Club_card.controllers;

import develop.backend.Club_card.controllers.payload.UserUpdatePayload;
import develop.backend.Club_card.models.User;
import develop.backend.Club_card.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/club-card/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getCurrentUser(userDetails);
    }

    @PatchMapping
    public ResponseEntity<?> updateCurrentUserData(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody UserUpdatePayload userUpdatePayload
    ) {
        userService.updateCurrentUserData(userDetails, userUpdatePayload);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteCurrentUser(userDetails);
        return ResponseEntity.noContent().build();
    }
}
