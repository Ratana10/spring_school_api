package com.piseth.schoolapi.users;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Long> roleIds;
}
