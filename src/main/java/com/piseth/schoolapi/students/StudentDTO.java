package com.piseth.schoolapi.students;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class StudentDTO {

    private String name;

    private String email;

    private String password;

    private String type;

    private String gender;
}
