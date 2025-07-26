package kz.shyngys.diary.service;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.model.Record;

import java.util.List;

public interface RecordService {
    /**
     * Получение всех существующих записей
     * @return список записей {@link Record}
     */
    List<Record> getAll();

    /**
     * Получение записи по ид
     * @param id - ид записи
     * @return запись {@link Record}
     */
    Record getById(Long id);

    /**
     * Создание записи
     * @param requestDto - запрос с моделью
     * @return созданная запись с полученным ид {@link Record}
     */
    Record create(CreateRecordRequestDto requestDto);
}
