package team.themoment.hellogsmv3.domain.oneseo.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.hellogsmv3.domain.oneseo.entity.*;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.Screening;
import team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo;
import team.themoment.hellogsmv3.domain.oneseo.repository.EntranceTestResultRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoPrivacyDetailRepository;
import team.themoment.hellogsmv3.domain.oneseo.repository.OneseoRepository;
import team.themoment.hellogsmv3.global.exception.error.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.NO;
import static team.themoment.hellogsmv3.domain.oneseo.entity.type.YesNo.YES;

@Service
@RequiredArgsConstructor
public class DownloadExcelService {

    private final OneseoRepository oneseoRepository;
    private final EntranceTestResultRepository entranceTestResultRepository;
    private final OneseoPrivacyDetailRepository oneseoPrivacyDetailRepository;

    private static final List<String> HEADER_NAMES = List.of(
            "순번", "접수번호", "성명", "1지망", "2지망", "3지망", "생년월일", "성별", "상세주소", "출신학교",
            "학력", "합격전형", "일반교과점수", "예체능점수", "비교과점수", "출석점수", "봉사점수", "전형총점",
            "인적성평가점수", "면접점수", "최종점수", "최종학과", "지원자연락처", "부모연락처", "담임연락처"
    );

    public Workbook execute() {
        Workbook workbook = new SXSSFWorkbook();
        List<Sheet> sheets = createSheets(workbook);
        List<List<List<String>>> allSheetData = getAllSheetData();

        for (int i = 0; i < sheets.size(); i++) {
            Sheet sheet = sheets.get(i);
            List<List<String>> sheetData = allSheetData.get(i);
            populateSheet(sheet, sheetData);
        }

        return workbook;
    }

    private List<Sheet> createSheets(Workbook workbook) {
        return List.of(
                workbook.createSheet("일반전형"),
                workbook.createSheet("사회전형"),
                workbook.createSheet("특별전형"),
                workbook.createSheet("불합격")
        );
    }

    private void populateSheet(Sheet sheet, List<List<String>> data) {
        int rowCount = 0;
        Row headerRow = sheet.createRow(rowCount++);
        addDataToRow(headerRow, HEADER_NAMES);

        for (List<String> rowData : data) {
            Row row = sheet.createRow(rowCount++);
            addDataToRow(row, rowData);
        }
    }

    private void addDataToRow(Row row, List<String> dataList) {
        int cellCount = 0;
        for (String data : dataList) {
            Cell cell = row.createCell(cellCount++);
            cell.setCellValue(data);
        }
    }

    private List<List<List<String>>> getAllSheetData() {
        return List.of(
                getOneseoData(Screening.GENERAL),
                getOneseoData(Screening.SPECIAL),
                getCombinedExtraScreeningData(),
                getFallenData()
        );
    }

    private List<List<String>> getOneseoData(Screening screening) {
        List<Oneseo> oneseoList = oneseoRepository.findAllByAppliedScreeningAndFinalSubmittedYn(screening, YesNo.YES);
        return oneseoToExcelDataList(oneseoList);
    }

    private List<List<String>> getCombinedExtraScreeningData() {
        List<Oneseo> extraOneseoList = Stream.concat(
                oneseoRepository.findAllByAppliedScreeningAndFinalSubmittedYn(Screening.EXTRA_VETERANS, YES).stream(),
                oneseoRepository.findAllByAppliedScreeningAndFinalSubmittedYn(Screening.EXTRA_ADMISSION, YES).stream()
        ).collect(Collectors.toList());

        return oneseoToExcelDataList(extraOneseoList);
    }

    private List<List<String>> getFallenData() {
        List<Oneseo> fallenOneseo = entranceTestResultRepository
                .findAllByFirstTestPassYnOrSecondTestPassYnAndOneseoFinalSubmittedYn(NO, NO, YES).stream()
                .map(EntranceTestResult::getOneseo)
                .collect(Collectors.toList());

        return oneseoToExcelDataList(fallenOneseo);
    }

    private List<List<String>> oneseoToExcelDataList(List<Oneseo> oneseoList) {
        List<List<String>> sheetData = new ArrayList<>();
        int index = 1;

        for (Oneseo oneseo : oneseoList) {
            EntranceTestResult entranceTestResult = entranceTestResultRepository.findEntranceTestResultByOneseo(oneseo)
                    .orElseThrow(() -> new ExpectedException("해당 원서로 입학 시험 결과를 찾을 수 없습니다. oneseo ID: " + oneseo.getId(), HttpStatus.NOT_FOUND));

            OneseoPrivacyDetail oneseoPrivacyDetail = oneseoPrivacyDetailRepository.findByOneseo(oneseo);

            BigDecimal finalScore = calculateFinalScore(entranceTestResult);

            List<String> rowData = List.of(
                    String.valueOf(index++),
                    String.valueOf(oneseo.getOneseoSubmitCode()),
                    String.valueOf(oneseo.getMember().getName()),
                    String.valueOf(oneseo.getDesiredMajors().getFirstDesiredMajor()),
                    String.valueOf(oneseo.getDesiredMajors().getSecondDesiredMajor()),
                    String.valueOf(oneseo.getDesiredMajors().getThirdDesiredMajor()),
                    String.valueOf(oneseo.getMember().getBirth()),
                    String.valueOf(oneseo.getMember().getSex()),
                    (oneseoPrivacyDetail.getAddress() + oneseoPrivacyDetail.getDetailAddress()),
                    String.valueOf(oneseoPrivacyDetail.getSchoolName()),
                    String.valueOf(oneseoPrivacyDetail.getGraduationType()),
                    String.valueOf(oneseo.getAppliedScreening()),
                    String.valueOf(entranceTestResult.getEntranceTestFactorsDetail().getGeneralSubjectsScore()),
                    String.valueOf(entranceTestResult.getEntranceTestFactorsDetail().getArtsPhysicalSubjectsScore()),
                    String.valueOf(entranceTestResult.getEntranceTestFactorsDetail().getTotalNonSubjectsScore()),
                    String.valueOf(entranceTestResult.getEntranceTestFactorsDetail().getAttendanceScore()),
                    String.valueOf(entranceTestResult.getEntranceTestFactorsDetail().getVolunteerScore()),
                    String.valueOf(entranceTestResult.getDocumentEvaluationScore()),
                    String.valueOf(entranceTestResult.getAptitudeEvaluationScore()),
                    String.valueOf(entranceTestResult.getInterviewScore()),
                    String.valueOf(finalScore),
                    String.valueOf(entranceTestResult.getDecidedMajor()),
                    oneseo.getMember().getPhoneNumber(),
                    oneseoPrivacyDetail.getGuardianPhoneNumber(),
                    oneseoPrivacyDetail.getSchoolTeacherPhoneNumber()
            );


            sheetData.add(rowData.stream().map(data -> data.equals("null")? "" : data).collect(Collectors.toList()));
        }

        return sheetData;
    }

    private BigDecimal calculateFinalScore(EntranceTestResult entranceTestResult) {
        if (entranceTestResult.getSecondTestPassYn() == null) {
            return null;
        }

        return entranceTestResult.getDocumentEvaluationScore()
                .add(entranceTestResult.getAptitudeEvaluationScore())
                .add(entranceTestResult.getInterviewScore());
    }
}
