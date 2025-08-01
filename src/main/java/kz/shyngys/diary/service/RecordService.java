package kz.shyngys.diary.service;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.dto.UpdateRecordRequestDto;
import org.springframework.data.domain.Page;

public interface RecordService {
    /**
     * Получение всех существующих записей
     *
     * @return список записей {@link RecordDto}
     */
    Page<RecordDto> getAll(int page, int size);

    /**
     * Получение записи по ид
     *
     * @param id - ид записи
     * @return запись {@link RecordDto}
     */
    RecordDto getById(Long id);

    /**
     * Создание записи
     *
     * @param requestDto - запрос с моделью
     * @return созданная запись с полученным ид {@link RecordDto}
     */
    RecordDto create(CreateRecordRequestDto requestDto);

    /**
     * Обновление записи
     *
     * @param id         - ид записи
     * @param requestDto - модель с записью
     * @return обновленная запись {@link RecordDto}
     */
    RecordDto update(Long id, UpdateRecordRequestDto requestDto);

    /**
     * Удаление записи. Установка флага is_active = true
     *
     * @param id - ид записи для удаления
     */
    void deactivate(Long id);
}
