package kz.shyngys.diary.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Data
@ConfigurationProperties(prefix = "test")
public class TestConfig {
    private String property;

    @Max(value = 25, message = "must be between 5 and 25")
    @Min(value = 5, message = "must be between 5 and 25")
    private int size;
}
