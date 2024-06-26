package team.themoment.hellogsmv3.domain.oneseo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.hellogsmv3.domain.application.type.GraduationStatus;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneseoPrivacyDetail {

    @Id
    @Column(name = "oneseo_id")
    private Long id;

//    @MapsId
//    @OneToOne
//    @JoinColumn(name = "id")  TODO 원서 테이블 생성 후 주석 풀기
//    private Oneseo oneseo;

    @Column(name = "education_type", nullable = false)
    private GraduationStatus educationType;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @Column(name = "guardian_name", nullable = false)
    private String guardianName;

    @Column(name = "guardian_phone_number", nullable = false)
    private String guardianPhoneNumber;

    @Column(name = "relationship_with_guardian", nullable = false)
    private String relationshipWithGuardian;

    @Column(name = "school_address")
    private String schoolAddress;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_teacher_name")
    private String schoolTeacherName;

    @Column(name = "school_teacher_phone_number")
    private String schoolTeacherPhoneNumber;
}
