package kz.shyngys.diary.controller;

import jakarta.validation.Valid;
import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static kz.shyngys.diary.util.ApiPaths.RECORDS_API_URL;
import static kz.shyngys.diary.util.InfoMessages.DELETED_RECORD_API;
import static kz.shyngys.diary.util.InfoMessages.DELETE_RECORD_API;
import static kz.shyngys.diary.util.InfoMessages.GET_ALL_RECORDS_API;
import static kz.shyngys.diary.util.InfoMessages.GET_RECORD_BY_ID_API;
import static kz.shyngys.diary.util.InfoMessages.GOT_ALL_RECORDS_API;
import static kz.shyngys.diary.util.InfoMessages.GOT_RECORD_BY_ID_API;
import static kz.shyngys.diary.util.InfoMessages.POST_CREATED_RECORD_API;
import static kz.shyngys.diary.util.InfoMessages.POST_CREATE_RECORD_API;
import static kz.shyngys.diary.util.InfoMessages.PUT_BEGIN_RECORD_API;
import static kz.shyngys.diary.util.InfoMessages.PUT_END_RECORD_API;

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
        List<RecordDto> result = all.stream().map(a -> new RecordDto(a.getId(), a.getText(), a.getIsActive())).toList();
        log.info(GOT_ALL_RECORDS_API);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getById(@PathVariable Long id) {
        log.info(GET_RECORD_BY_ID_API, id);
        Record record = recordService.getById(id);
        RecordDto result = new RecordDto(record.getId(), record.getText(), record.getIsActive());
        log.info(GOT_RECORD_BY_ID_API, id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<RecordDto> create(@Valid @RequestBody CreateRecordRequestDto request) {
        log.info(POST_CREATE_RECORD_API, request);
        Record record = recordService.create(request);
        RecordDto result = new RecordDto(record.getId(), record.getText(), record.getIsActive());
        log.info(POST_CREATED_RECORD_API);
        return ResponseEntity.created(URI.create(RECORDS_API_URL + "/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordDto> update(@PathVariable Long id,
                                       @Valid @RequestBody UpdateRecordRequestDto requestDto) {
        log.info(PUT_BEGIN_RECORD_API, id, requestDto);
        Record record = recordService.update(id, requestDto);
        RecordDto result = new RecordDto(record.getId(), record.getText(), record.getIsActive());
        log.info(PUT_END_RECORD_API, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(DELETE_RECORD_API, id);
        recordService.softDelete(id);
        log.info(DELETED_RECORD_API, id);
        return ResponseEntity.noContent().build();
    }
}
