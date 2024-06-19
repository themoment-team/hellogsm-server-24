package team.themoment.hellogsmv3.domain.application.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import team.themoment.hellogsmv3.domain.applicant.entity.Applicant;
import team.themoment.hellogsmv3.domain.application.entity.abs.AbstractApplication;
import team.themoment.hellogsmv3.domain.application.repo.custom.CustomApplicationRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<AbstractApplication, UUID>, CustomApplicationRepository {

    boolean existsByApplicant(Applicant applicant);
    Optional<AbstractApplication> findByApplicant(Applicant applicant);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM AbstractApplication WHERE id = :uuid")
    void deleteQueryApplicationById(UUID uuid);

    @Query(value = "SELECT a.* FROM abstract_application a " +
            "JOIN abstract_personal_information p ON a.personal_information_id = p.id " +
            "WHERE p.school_name LIKE %:keyword% " +
            "AND a.final_submitted = TRUE " +
            "ORDER BY " +
            "    CASE " +
            "        WHEN a.competency_evaluation_result_status = 'PASS' THEN 30 " +
            "        WHEN a.competency_evaluation_result_status = 'FALL' THEN 20 " +
            "        ELSE 10 " +
            "    END DESC, " +
            "    CASE " +
            "        WHEN a.subject_evaluation_result_status = 'PASS' THEN 3 " +
            "        WHEN a.subject_evaluation_result_status = 'FALL' THEN 2 " +
            "        ELSE 1 " +
            "    END DESC",
            countQuery = "SELECT COUNT(*) FROM abstract_application a " +
                    "JOIN abstract_personal_information p ON a.personal_information_id = p.id " +
                    "WHERE p.school_name LIKE %:keyword% " +
                    "AND a.final_submitted = TRUE",
            nativeQuery = true)
    Page<AbstractApplication> findAllByFinalSubmittedAndSchoolNameContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT a.* FROM abstract_application a " +
            "JOIN abstract_personal_information p ON a.personal_information_id = p.id " +
            "WHERE p.phone_number LIKE %:keyword% " +
            "OR p.guardian_phone_number LIKE %:keyword% " +
            "OR p.teacher_phone_number LIKE %:keyword% " +
            "AND a.final_submitted = TRUE " +
            "ORDER BY " +
            "    CASE " +
            "        WHEN a.competency_evaluation_result_status = 'PASS' THEN 30 " +
            "        WHEN a.competency_evaluation_result_status = 'FALL' THEN 20 " +
            "        ELSE 10 " +
            "    END DESC, " +
            "    CASE " +
            "        WHEN a.subject_evaluation_result_status = 'PASS' THEN 3 " +
            "        WHEN a.subject_evaluation_result_status = 'FALL' THEN 2 " +
            "        ELSE 1 " +
            "    END DESC",
            countQuery = "SELECT COUNT(*) FROM abstract_application a " +
                    "JOIN abstract_personal_information p ON a.personal_information_id = p.id " +
                    "WHERE p.phone_number LIKE %:keyword% " +
                    "OR p.guardian_phone_number LIKE %:keyword% " +
                    "OR p.teacher_phone_number LIKE %:keyword% " +
                    "AND a.final_submitted = TRUE",
            nativeQuery = true)
    Page<AbstractApplication> findAllByFinalSubmittedAndPhoneNumberContaining(@Param("keyword") String keyword, Pageable pageable);
}
