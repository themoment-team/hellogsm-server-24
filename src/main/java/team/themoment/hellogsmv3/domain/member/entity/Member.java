package team.themoment.hellogsmv3.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import team.themoment.hellogsmv3.domain.member.entity.type.Gender;
import team.themoment.hellogsmv3.domain.member.entity.type.Role;
import team.themoment.hellogsmv3.domain.member.entity.type.AuthReferrerType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "tb_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_referrer_type", nullable = false)
    private AuthReferrerType authReferrerType;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @CreatedDate
    @Column(name = "created_time", updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    public static Member buildNewMember(String email, AuthReferrerType authRefType) {
        return Member.builder()
                .email(email)
                .authReferrerType(authRefType)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now()).build();
    }

    @PrePersist
    private void prePersist() {
        this.role = this.role == null ? Role.UNAUTHENTICATED : this.role;
    }
}
