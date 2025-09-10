package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.exception.UserHasNoThisRecordException;
import kz.shyngys.diary.mapper.RecordMapper;
import kz.shyngys.diary.model.domain.Record;
import kz.shyngys.diary.model.domain.User;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RabbitService;
import kz.shyngys.diary.service.RecordService;
import kz.shyngys.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static kz.shyngys.diary.util.ErrorMessages.RECORD_NOT_FOUND;
import static kz.shyngys.diary.util.ErrorMessages.USER_HAS_NO_THIS_RECORD;
import static kz.shyngys.diary.util.InfoMessages.CREATED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.CREATE_RECORD;
import static kz.shyngys.diary.util.InfoMessages.DELETED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.DELETE_RECORD;
import static kz.shyngys.diary.util.InfoMessages.GET_RECORDS;
import static kz.shyngys.diary.util.InfoMessages.GET_RECORD_BY_ID;
import static kz.shyngys.diary.util.InfoMessages.GOT_RECORDS;
import static kz.shyngys.diary.util.InfoMessages.GOT_RECORD_BY_ID;
import static kz.shyngys.diary.util.InfoMessages.UPDATED_RECORD;
import static kz.shyngys.diary.util.InfoMessages.UPDATE_RECORD;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final UserService userService;
    private final RabbitService rabbitService;

    @Override
    public Page<RecordDto> getAll(Long userId, int page, int size) {
        log.info(GET_RECORDS, userId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Record> records = recordRepository.findAllByUserId(userId, pageable);
        Page<RecordDto> result = records.map(recordMapper::toDto);
        log.info(GOT_RECORDS, result);
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
    public RecordDto create(String username, CreateRecordRequestDto requestDto) {
        log.info(CREATE_RECORD, requestDto);
        Record recordToSave = recordMapper.toEntity(requestDto);
        User byUsername = userService.getByUsername(username);
        recordToSave.setUser(byUsername);
        Record save = recordRepository.save(recordToSave);
        RecordDto result = recordMapper.toDto(save);
        rabbitService.sendRecordCreatedEvent(result.getId(), "diary.exchange", "record.created");
        log.info(CREATED_RECORD, result);
        return result;
    }

    @Transactional
    @Override
    public RecordDto update(Long userId, Long recordId, UpdateRecordRequestDto requestDto) {
        log.info(UPDATE_RECORD, recordId, requestDto);

        Record recordToUpdate = recordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException(String.format(RECORD_NOT_FOUND, recordId)));

        User user = userService.getById(userId);
        if (!user.getId().equals(recordToUpdate.getUser().getId())) {
            throw new UserHasNoThisRecordException(String.format(USER_HAS_NO_THIS_RECORD, userId, recordId));
        }

        recordToUpdate.setText(requestDto.getText());

        Record updated = recordRepository.save(recordToUpdate);
        RecordDto result = recordMapper.toDto(updated);

        log.info(UPDATED_RECORD, result);
        return result;
    }

    @Transactional
    @Override
    public void deactivate(Long id) {
        log.info(DELETE_RECORD, id);
        Record recordToDeactivate = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(RECORD_NOT_FOUND, id)));
        recordToDeactivate.setIsActive(false);
        Record updated = recordRepository.save(recordToDeactivate);
        log.info(DELETED_RECORD, updated);
    }
}
