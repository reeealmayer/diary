package kz.shyngys.diary.controller;

import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordDto>> getAll() {
        List<Record> all = recordService.getAll();
        List<RecordDto> result = all.stream().map(a -> new RecordDto(a.getId(), a.getText())).toList();
        return ResponseEntity.ok(result);
    }
}
