package develop.backend.Club_card.entity;

import develop.backend.Club_card.entity.enums.user.UserPrivilegesEnum;
import develop.backend.Club_card.entity.enums.user.UserRolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "is_pending_deletion", nullable = false)
    private Boolean isPendingDeletion;

    @Column(name = "is_full_name_updated", nullable = false)
    private Boolean isFullNameUpdated;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRolesEnum role;

    @Column(name = "privilege", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserPrivilegesEnum privilege;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deletion_request_id", referencedColumnName = "id")
    private DeletionRequest deletionRequest;
}
