package kz.shyngys.diary.service;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.mapper.RecordMapper;
import kz.shyngys.diary.model.Role;
import kz.shyngys.diary.model.domain.Record;
import kz.shyngys.diary.model.domain.User;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecordServiceTest {

    private RecordService recordService;
    private RecordRepository recordRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        recordRepository = mock(RecordRepository.class);
        userService = mock(UserService.class);
        recordService = new RecordServiceImpl(recordRepository, Mappers.getMapper(RecordMapper.class), userService);
    }

    @Test
    void testGetAll_ShouldReturnAll() {
        int page = 0;
        int size = 10;
        User user = new User(1L, "email@mail.ru", "name", "password", Role.ROLE_USER);

        Record record1 = new Record(1L, "record 1", true, user);
        Record record2 = new Record(2L, "record 2", true, user);
        List<Record> recordList = List.of(record1, record2);
        Page<Record> recordPage = new PageImpl<>(recordList);


        when(recordRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(recordPage);

        Page<RecordDto> result = recordService.getAll(user.getId(), page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());

        verify(recordRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        int page = 0;
        int size = 10;

        Page<Record> emptyPage = new PageImpl<>(Collections.emptyList());
        when(recordRepository.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(emptyPage);

        Page<RecordDto> result = recordService.getAll(1L, page, size);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());

        verify(recordRepository, times(1)).findAllByUserId(anyLong(), any(Pageable.class));
    }

    @Test
    void testGetById_ShouldReturnRecord() {
        long id = 1L;
        Record record = new Record(id, "record", true, null);
        LocalDateTime date = LocalDateTime.now();
        record.setCreateDate(date);
        RecordDto expectedDto = new RecordDto(id, "record", true, date, null);

        when(recordRepository.findById(id)).thenReturn(Optional.of(record));

        RecordDto result = recordService.getById(id);

        assertEquals(expectedDto, result);
        verify(recordRepository, times(1)).findById(id);
    }

    @Test
    void testGetById_ShouldThrowRecordNotFoundException() {
        when(recordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> recordService.getById(1L));
    }

    @Test
    void testCreate_ShouldReturnRecordDto() {
        // given
        CreateRecordRequestDto dto = new CreateRecordRequestDto();
        dto.setText("Test record");

        Record entityToSave = new Record();
        entityToSave.setText("Test record");

        Record savedRecord = new Record();
        savedRecord.setId(1L);
        savedRecord.setText("Test record");

        RecordDto expectedDto = new RecordDto();
        expectedDto.setId(1L);
        expectedDto.setText("Test record");

        User user = new User();
        user.setUsername("test");
        user.setRole(Role.ROLE_USER);
        user.setEmail("test@mail.ru");
        user.setPassword("test");
        user.setId(1L);

        when(recordRepository.save(entityToSave)).thenReturn(savedRecord);
        when(userService.getByUsername(anyString())).thenReturn(user);

        // then
        RecordDto result = recordService.create(user.getUsername(), dto);

        // assertions
        assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Test record", result.getText());

        verify(recordRepository).save(entityToSave);
    }

    @Test
    void testUpdate_ShouldReturnRecordDto() {
        // given
        Long id = 1L;

        UpdateRecordRequestDto requestDto = new UpdateRecordRequestDto();
        requestDto.setText("Updated text");

        User testUser = createTestUser();

        Record existingRecord = new Record();
        existingRecord.setId(id);
        existingRecord.setText("Old text");
        existingRecord.setUser(testUser);

        Record savedRecord = new Record();
        savedRecord.setId(id);
        savedRecord.setText("Updated text");
        savedRecord.setUser(testUser);

        RecordDto expectedDto = new RecordDto();
        expectedDto.setId(id);
        expectedDto.setText("Updated text");


        when(recordRepository.findById(id)).thenReturn(Optional.of(existingRecord));
        when(recordRepository.save(existingRecord)).thenReturn(savedRecord);
        when(userService.getById(anyLong())).thenReturn(testUser);

        // when
        RecordDto result = recordService.update(testUser.getId(), id, requestDto);

        // then
        assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("Updated text", result.getText());

        verify(recordRepository).findById(id);
        verify(recordRepository).save(existingRecord);
    }

    @Test
    void deactivate_shouldSetIsActiveFalse_andSaveRecord() {
        // given
        Long id = 1L;
        Record record = new Record();
        record.setId(id);
        record.setIsActive(true);

        when(recordRepository.findById(id)).thenReturn(Optional.of(record));

        // when
        recordService.deactivate(id);

        // then
        assertFalse(record.getIsActive());
        verify(recordRepository).save(record);
    }

    @Test
    void deactivate_shouldThrowException_whenRecordNotFound() {
        // given
        Long id = 42L;
        when(recordRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThrows(RecordNotFoundException.class, () -> recordService.deactivate(id));
        verify(recordRepository, never()).save(any());
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("test");
        user.setRole(Role.ROLE_USER);
        user.setEmail("test@mail.ru");
        user.setPassword("test");
        user.setId(1L);
        return user;
    }
}