package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.mapper.RecordMapper;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kz.shyngys.diary.util.ErrorMessages.RECORD_NOT_FOUND;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;

    @Override
    public List<RecordDto> getAll() {
        log.info(GET_ALL_RECORDS);
        List<Record> records = recordRepository.findAll();
        List<RecordDto> result = recordMapper.toDtoList(records);
        log.info(GOT_ALL_RECORDS, result);
        return result;
    }

    @Override
    public RecordDto getById(Long id) {
        log.info(GET_RECORD_BY_ID, id);
        Optional<Record> recordOptional = recordRepository.findById(id);
        if (recordOptional.isEmpty()) {
            RecordNotFoundException exception = new RecordNotFoundException(String.format(RECORD_NOT_FOUND, id));
            log.error(exception.getMessage());
            throw exception;
        }
        RecordDto result = recordMapper.toDto(recordOptional.get());
        log.info(GOT_RECORD_BY_ID, id, result);
        return result;
    }

    @Transactional
    @Override
    public RecordDto create(CreateRecordRequestDto requestDto) {
        log.info(CREATE_RECORD, requestDto);
        Record save = recordRepository.save(recordMapper.toEntity(requestDto));
        RecordDto result = recordMapper.toDto(save);
        log.info(CREATED_RECORD, result);
        return result;
    }

    @Transactional
    @Override
    public RecordDto update(Long id, UpdateRecordRequestDto requestDto) {
        log.info(UPDATE_RECORD, id, requestDto);

        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(RECORD_NOT_FOUND, id)));

        record.setText(requestDto.getText());

        Record updated = recordRepository.save(record);
        RecordDto result = recordMapper.toDto(updated);

        log.info(UPDATED_RECORD, result);
        return result;
    }

    @Transactional
    @Override
    public void softDelete(Long id) {
        log.info(DELETE_RECORD, id);
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(RECORD_NOT_FOUND, id)));
        record.setIsActive(false);
        Record updated = recordRepository.save(record);
        log.info(DELETED_RECORD, updated);
    }
}
