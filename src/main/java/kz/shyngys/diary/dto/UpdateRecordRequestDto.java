package kz.shyngys.diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRecordRequestDto {
    @NotBlank
    private String text;
}
