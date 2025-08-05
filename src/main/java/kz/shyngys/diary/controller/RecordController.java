package kz.shyngys.diary.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
@Validated
        //TODO добавить авторизацию для методов
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<Page<RecordDto>> getAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                  @RequestParam(defaultValue = "10") @Positive @Min(1) int size) {
        log.info(GET_ALL_RECORDS_API);
        Page<RecordDto> result = recordService.getAll(page - 1, size);
        log.info(GOT_ALL_RECORDS_API);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getById(@PathVariable Long id) {
        log.info(GET_RECORD_BY_ID_API, id);
        RecordDto recordDto = recordService.getById(id);
        log.info(GOT_RECORD_BY_ID_API, id);
        return ResponseEntity.ok(recordDto);
    }

    @PostMapping
    public ResponseEntity<RecordDto> create(@Valid @RequestBody CreateRecordRequestDto request) {
        log.info(POST_CREATE_RECORD_API, request);
        RecordDto recordDto = recordService.create(request);
        log.info(POST_CREATED_RECORD_API);
        return ResponseEntity.created(URI.create(RECORDS_API_URL + "/" + recordDto.getId())).body(recordDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordDto> update(@PathVariable Long id,
                                            @Valid @RequestBody UpdateRecordRequestDto requestDto) {
        log.info(PUT_BEGIN_RECORD_API, id, requestDto);
        RecordDto recordDto = recordService.update(id, requestDto);
        log.info(PUT_END_RECORD_API, id);
        return ResponseEntity.ok(recordDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info(DELETE_RECORD_API, id);
        recordService.deactivate(id);
        log.info(DELETED_RECORD_API, id);
        return ResponseEntity.ok().build();
    }
}
