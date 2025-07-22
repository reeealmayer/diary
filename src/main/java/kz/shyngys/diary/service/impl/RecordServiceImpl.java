package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.model.Record;
import kz.shyngys.diary.repository.RecordRepository;
import kz.shyngys.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }
}
