package kz.shyngys.diary.service;

import kz.shyngys.diary.model.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAll();

    Record getById(Long id);
}
