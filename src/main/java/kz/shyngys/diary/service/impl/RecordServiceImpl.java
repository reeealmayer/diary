package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kz.shyngys.diary.util.Constants.GET_ALL_RECORDS;
import static kz.shyngys.diary.util.Constants.GOT_ALL_RECORDS;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public List<Record> getAll() {
        log.info(GET_ALL_RECORDS);
        List<Record> result = recordRepository.findAll();
        log.info(GOT_ALL_RECORDS, result);
        return result;
    }
}
