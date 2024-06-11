package team.themoment.hellogsmv3.domain.application.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import team.themoment.hellogsmv3.domain.application.dto.request.ApplicationReqDto;
import team.themoment.hellogsmv3.domain.application.type.Major;

import java.util.HashSet;
import java.util.Set;

public class DesiredMajorsValidator implements ConstraintValidator<ValidDesiredMajors, ApplicationReqDto> {
    @Override
    public boolean isValid(ApplicationReqDto dto, ConstraintValidatorContext context) {

        boolean hasNullMajor = dto.firstDesiredMajor() == null || dto.secondDesiredMajor() == null || dto.thirdDesiredMajor() == null;
        if (hasNullMajor) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("모든 필드는 NULL일 수 없습니다.").addConstraintViolation();
            return false;
        }

        Set<Major> majorsSet = new HashSet<>();
        majorsSet.add(dto.firstDesiredMajor());
        majorsSet.add(dto.secondDesiredMajor());
        majorsSet.add(dto.thirdDesiredMajor());

        if (majorsSet.size() != 3) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("중복된 전공이 입력되었습니다.").addConstraintViolation();
            return false;
        }

        return true;
    }
}
