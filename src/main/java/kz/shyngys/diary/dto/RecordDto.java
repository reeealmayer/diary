package kz.shyngys.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto {
    private long id;
    private String text;
    private Boolean isActive;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
