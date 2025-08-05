package kz.shyngys.diary.controller;

import kz.shyngys.diary.config.TestConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final TestConfig testConfig;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok(testConfig.getProperty());
    }

}
