package team.themoment.hellogsmv3.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerId;

    private String providerName;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Authentication roleUpdatedAuthentication() {
        this.role = Role.APPLICANT;
        return this;
    }

    @PrePersist
    private void prePersist() {
        this.role = this.role == null ? Role.UNAUTHENTICATED : this.role;
    }
}
