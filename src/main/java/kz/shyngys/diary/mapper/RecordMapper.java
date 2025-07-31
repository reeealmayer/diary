package kz.shyngys.diary.mapper;

import kz.shyngys.diary.dto.CreateRecordRequestDto;
import kz.shyngys.diary.dto.RecordDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    RecordDto toDto(kz.shyngys.diary.model.Record record);

    List<RecordDto> toDtoList(List<kz.shyngys.diary.model.Record> records);

    kz.shyngys.diary.model.Record toEntity(CreateRecordRequestDto dto);

    kz.shyngys.diary.model.Record toEntity(RecordDto dto);
}
