package kz.shyngys.diary.controller;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@ConfigurationProperties("test")
public class HelloController {

    @Setter
    private String property;

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok(property);
    }

}
