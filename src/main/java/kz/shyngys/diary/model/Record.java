package kz.shyngys.diary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Record {
    @Id
    private Long id;

    private String text;
}
