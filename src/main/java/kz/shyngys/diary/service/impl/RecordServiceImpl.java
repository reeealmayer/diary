package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kz.shyngys.diary.util.InfoMessages.CREATED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.CREATE_RECORD;
import static kz.shyngys.diary.util.InfoMessages.DELETED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.DELETE_RECORD;
import static kz.shyngys.diary.util.InfoMessages.GET_ALL_RECORDS;
import static kz.shyngys.diary.util.InfoMessages.GET_RECORD_BY_ID;
import static kz.shyngys.diary.util.InfoMessages.GOT_ALL_RECORDS;
import static kz.shyngys.diary.util.InfoMessages.GOT_RECORD_BY_ID;
import static kz.shyngys.diary.util.InfoMessages.UPDATED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.UPDATE_RECORD;
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

    @Override
    public Record create(CreateRecordRequestDto requestDto) {
        log.info(CREATE_RECORD, requestDto);
        Record record = new Record();
        record.setText(requestDto.getText());
        Record save = recordRepository.save(record);
        log.info(CREATED_RECORD, save);
        return save;
    }

    @Override
    public Record update(Long id, UpdateRecordRequestDto requestDto) {
        log.info(UPDATE_RECORD, id, requestDto);
        Record record = getById(id);
        record.setText(requestDto.getText());
        Record save = recordRepository.save(record);
        log.info(UPDATED_RECORD, save);
        return save;
    }

    @Transactional
    @Override
    public void softDelete(Long id) {
        log.info(DELETE_RECORD, id);
        Record record = getById(id);
        record.setIsActive(false);
        Record save = recordRepository.save(record);
        log.info(DELETED_RECORD, save);
    }
}
