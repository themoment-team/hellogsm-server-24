package team.themoment.hellogsmv3.domain.oneseo.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.oneseo.dto.response.SearchOneseoResDto;
import team.themoment.hellogsmv3.domain.oneseo.dto.type.SearchTag;
import team.themoment.hellogsmv3.domain.oneseo.entity.Oneseo;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;

@Service
@RequiredArgsConstructor
public class SearchOneseoService {

    private final OneseoRepository oneseoRepository;

    public SearchOneseoResDto execute(
            Integer page,
            Integer size,
            @Nullable SearchTag tag,
            @Nullable String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size); // TODO 정렬 기능 필요 검토
        Page<Oneseo> oneseoPage = findOneseoByTagAndKeyword(tag, keyword, pageable);

    }

    private Page<Oneseo> findOneseoByTagAndKeyword(
            SearchTag tag,
            String keyword,
            Pageable pageable
    ) {
//        if (tag == null) {
//            return oneseoRepository.findAllByFinalSubmitted(pageable);    // TODO queryDSL로 구현
//        }
//        return switch (tag) {
//            case NAME -> oneseoRepository.findAllByFinalSubmittedAndMemberNameContaining(keyword, pageable);
//            case SCHOOL -> oneseoRepository.findAllByFinalSubmittedAndSchoolNameContaining(keyword, pageable);
//            case PHONE_NUMBER -> oneseoRepository.findAllByFinalSubmittedAndPhoneNumberContaining(keyword, pageable);
//        };
        return null;
    }
}
