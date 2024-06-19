package team.themoment.hellogsmv3.global.thirdParty.aws.s3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.themoment.hellogsmv3.global.thirdParty.aws.s3.service.UploadImageService;

import java.util.Map;

@RestController
@RequestMapping("/application/v3")
@RequiredArgsConstructor
public class S3Controller {

    private final UploadImageService uploadImageService;

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String fileUrl = uploadImageService.execute(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("fileUrl", fileUrl));
    }
}
