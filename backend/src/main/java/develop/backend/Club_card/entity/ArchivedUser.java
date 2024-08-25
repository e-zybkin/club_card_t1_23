package develop.backend.Club_card.entity;

import develop.backend.Club_card.entity.enums.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.UserRolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archived_user")
public class ArchivedUser {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email",  nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRolesEnum role;

    @Column(name = "privilege", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserPrivilegesEnum privilege;
}
