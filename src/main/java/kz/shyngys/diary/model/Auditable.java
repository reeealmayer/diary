package kz.shyngys.diary.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Auditable {
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    public void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
