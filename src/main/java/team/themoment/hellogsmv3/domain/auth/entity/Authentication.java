package team.themoment.hellogsmv3.domain.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.auth.type.Role;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerId;

    private String providerName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    private void prePersist() {
        this.role = this.role == null ? Role.UNAUTHENTICATED : this.role;
    }
}
