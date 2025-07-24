package kz.shyngys.diary.service;

import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecordServiceTest {

    private RecordService recordService;
    private RecordRepository recordRepository;

    @BeforeEach
    void setUp() {
        recordRepository = mock(RecordRepository.class);
        recordService = new RecordServiceImpl(recordRepository);
    }

    @Test
    void testGetAll_ShouldReturnAll() {
        //given
        Record record1 = new Record(1L, "record 1");
        Record record2 = new Record(2L, "record 2");

        when(recordRepository.findAll()).thenReturn(List.of(record1, record2));

        //when
        List<Record> result = recordService.getAll();

        //then
        assertEquals(2, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals(2L, result.get(1).getId());

        verify(recordRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        //given
        when(recordRepository.findAll()).thenReturn(emptyList());

        //when
        List<Record> result = recordService.getAll();

        //then
        assertEquals(0, result.size());

        verify(recordRepository, times(1)).findAll();
    }

}