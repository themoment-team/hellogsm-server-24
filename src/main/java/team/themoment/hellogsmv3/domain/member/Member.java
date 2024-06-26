package team.themoment.hellogsmv3.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;
import team.themoment.hellogsmv3.domain.auth.type.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "auth_referrer_type")
    private String authReferrerType;

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

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
