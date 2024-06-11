package team.themoment.hellogsmv3.domain.application.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import team.themoment.hellogsmv3.domain.application.type.DesiredMajors;
import team.themoment.hellogsmv3.domain.application.type.Major;

import java.util.HashSet;
import java.util.Set;

public class DesiredMajorsValidator implements ConstraintValidator<ValidDesiredMajors, DesiredMajors> {
    @Override
    public boolean isValid(DesiredMajors value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        boolean hasNullMajor = value.getFirstDesiredMajor() == null || value.getSecondDesiredMajor() == null || value.getThirdDesiredMajor() == null;
        if (hasNullMajor) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("모든 필드는 NULL일 수 없습니다.").addConstraintViolation();
            return false;
        }

        Set<Major> majorsSet = new HashSet<>();
        majorsSet.add(value.getFirstDesiredMajor());
        majorsSet.add(value.getSecondDesiredMajor());
        majorsSet.add(value.getThirdDesiredMajor());

        if (majorsSet.size() != 3) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("중복된 전공이 입력되었습니다.").addConstraintViolation();
            return false;
        }

        System.out.println("==============================================");

        return true;
    }
}
