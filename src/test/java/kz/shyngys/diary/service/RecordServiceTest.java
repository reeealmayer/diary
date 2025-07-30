package kz.shyngys.diary.service;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import kz.shyngys.diary.exception.RecordNotFoundException;
import kz.shyngys.diary.mapper.RecordMapper;
import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecordServiceTest {

    private RecordService recordService;
    private RecordRepository recordRepository;
    private RecordMapper recordMapper;

    @BeforeEach
    void setUp() {
        recordRepository = mock(RecordRepository.class);
        recordMapper = Mappers.getMapper(RecordMapper.class);
        recordService = new RecordServiceImpl(recordRepository, recordMapper);
    }

    @Test
    void testGetAll_ShouldReturnAll() {
        Record record1 = new Record(1L, "record 1", true);
        Record record2 = new Record(2L, "record 2", true);

        when(recordRepository.findAll()).thenReturn(List.of(record1, record2));

        List<Record> result = recordService.getAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals(2L, result.get(1).getId());

        verify(recordRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        when(recordRepository.findAll()).thenReturn(emptyList());

        List<Record> result = recordService.getAll();

        assertEquals(0, result.size());

        verify(recordRepository, times(1)).findAll();
    }

    @Test
    void testGetById_ShouldReturnRecord() {
        long id = 1L;
        Record record = new Record(id, "record", true);
        when(recordRepository.findById(anyLong())).thenReturn(Optional.of(record));

        Record result = recordService.getById(1L);

        assertEquals(record, result);

        verify(recordRepository, times(1)).findById(id);
    }

    @Test
    void testGetById_ShouldThrowRecordNotFoundException() {
        when(recordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> recordService.getById(1L));
    }

    @Test
    void testCreate_ShouldReturnRecord() {
        // given
        CreateRecordRequestDto dto = new CreateRecordRequestDto();
        dto.setText("Test record");

        Record savedRecord = new Record();
        savedRecord.setId(1L);
        savedRecord.setText("Test record");

        // when
        when(recordRepository.save(any(Record.class))).thenReturn(savedRecord);

        // then
        Record result = recordService.create(dto);

        // assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Test record", result.getText());

        // verify that repository.save was called with expected Record
        ArgumentCaptor<Record> captor = ArgumentCaptor.forClass(Record.class);
        verify(recordRepository).save(captor.capture());
        Assertions.assertEquals("Test record", captor.getValue().getText());
    }

    @Test
    void testUpdate_ShouldReturnRecord() {
        // given
        Long id = 1L;

        UpdateRecordRequestDto requestDto = new UpdateRecordRequestDto();
        requestDto.setText("Updated text");

        Record existingRecord = new Record();
        existingRecord.setId(id);
        existingRecord.setText("Old text");

        Record updatedRecord = new Record();
        updatedRecord.setId(id);
        updatedRecord.setText("Updated text");

        when(recordRepository.findById(id)).thenReturn(Optional.of(existingRecord));
        when(recordRepository.save(any(Record.class))).thenReturn(updatedRecord);

        // when
        Record result = recordService.update(id, requestDto);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated text", result.getText());
        Assertions.assertEquals(id, result.getId());

        verify(recordRepository).save(argThat(record ->
                record.getText().equals("Updated text") && record.getId().equals(id)
        ));
    }

    @Test
    void softDelete_shouldSetIsActiveFalse_andSaveRecord() {
        // given
        Long id = 1L;
        Record record = new Record();
        record.setId(id);
        record.setIsActive(true);

        when(recordRepository.findById(id)).thenReturn(Optional.of(record));

        // when
        recordService.softDelete(id);

        // then
        assertFalse(record.getIsActive());
        verify(recordRepository).save(record);
    }

    @Test
    void softDelete_shouldThrowException_whenRecordNotFound() {
        // given
        Long id = 42L;
        when(recordRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThrows(RecordNotFoundException.class, () -> recordService.softDelete(id));
        verify(recordRepository, never()).save(any());
    }
}