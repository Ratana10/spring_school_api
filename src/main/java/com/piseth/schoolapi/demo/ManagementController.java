package com.piseth.schoolapi.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

    @GetMapping
    public String get() {
        return "GET:: management controller";
    }

    @GetMapping("{id}")
    public String getById() {
        return "GET ID:: management controller";
    }

    @PostMapping
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: management controller";
    }
}