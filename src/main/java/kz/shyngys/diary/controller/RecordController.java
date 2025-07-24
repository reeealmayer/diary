package kz.shyngys.diary.controller;

import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kz.shyngys.diary.util.Constants.GET_ALL_RECORDS_API;
import static kz.shyngys.diary.util.Constants.GOT_ALL_RECORDS_API;
import static kz.shyngys.diary.util.Constants.RECORDS_API_URL;

@RestController
@RequestMapping(RECORDS_API_URL)
@RequiredArgsConstructor
@Slf4j
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordDto>> getAll() {
        log.info(GET_ALL_RECORDS_API);
        List<Record> all = recordService.getAll();
        List<RecordDto> result = all.stream().map(a -> new RecordDto(a.getId(), a.getText())).toList();
        log.info(GOT_ALL_RECORDS_API);
        return ResponseEntity.ok(result);
    }
}
