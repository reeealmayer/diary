package kz.shyngys.diary.mapper;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import kz.shyngys.diary.model.domain.Record;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordDto toDto(Record record);

    List<RecordDto> toDtoList(List<Record> records);

    Record toEntity(CreateRecordRequestDto dto);

    Record toEntity(RecordDto dto);
}
