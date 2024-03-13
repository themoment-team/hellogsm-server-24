package team.themoment.hellogsmv3.domain.applicant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.applicant.type.Gender;

import java.time.LocalDate;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotNull
    @Column(name = "birth")
    private LocalDate birth;

    @NotNull
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "authentication_id", unique = true)
    private Long authenticationId;
}
