package team.themoment.hellogsmv3.global.thirdParty.aws.s3.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.themoment.hellogsmv3.global.common.response.CommonApiResponse;
import team.themoment.hellogsmv3.global.thirdParty.aws.s3.service.UploadImageService;

@Tag(name = "ThirdParty API", description = "증명사진 등록 관련 API입니다.")
@RestController
@RequestMapping("/oneseo/v3")
@RequiredArgsConstructor
public class S3Controller {

    private final UploadImageService uploadImageService;

    @PostMapping("/image")
    public CommonApiResponse uploadImage(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String fileUrl = uploadImageService.execute(multipartFile);
        return CommonApiResponse.success(fileUrl);
    }
}
