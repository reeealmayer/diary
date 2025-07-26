package kz.shyngys.diary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRecordRequestDto {
    @NotBlank
    private String text;
}
