package kz.shyngys.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordDto {
    private long id;
    private String text;
}
