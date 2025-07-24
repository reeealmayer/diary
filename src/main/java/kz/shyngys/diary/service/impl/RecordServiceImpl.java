package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RecordService;
import kz.shyngys.diary.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static kz.shyngys.diary.util.Constants.GET_ALL_RECORDS;
import static kz.shyngys.diary.util.Constants.GET_RECORD_BY_ID;
import static kz.shyngys.diary.util.Constants.GOT_ALL_RECORDS;
import static kz.shyngys.diary.util.Constants.GOT_RECORD_BY_ID;
import static kz.shyngys.diary.util.ErrorMessages.RECORD_NOT_FOUND;

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

    @Override
    public Record getById(Long id) {
        log.info(GET_RECORD_BY_ID, id);
        Optional<Record> result = recordRepository.findById(id);
        if (result.isEmpty()) {
            RecordNotFoundException exception = new RecordNotFoundException(String.format(RECORD_NOT_FOUND, id));
            log.error(exception.getMessage());
            throw exception;
        }
        log.info(GOT_RECORD_BY_ID, id, result.get());
        return result.get();
    }
}
