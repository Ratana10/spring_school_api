package com.piseth.schoolapi.users;

import com.piseth.schoolapi.exception.ApiResponse;
import com.piseth.schoolapi.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody UserDTO dto) {

        User user = userMapper.toUser(dto);
        user = userService.create(user);

        ApiResponse response = ApiResponse.builder()
                .data(userMapper.toUserDTO(user))
                .message("create user successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = userMapper.toUser(dto);
        user = userService.update(id, user);

        ApiResponse response = ApiResponse.builder()
                .data(userMapper.toUserDTO(user))
                .message("update user successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
//        userService.delete(id);
//
//        ApiResponse response = ApiResponse.builder()
//                .data(null)
//                .message("delete user successful")
//                .httpStatus(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
       User byId = userService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(userMapper.toUserDTO(byId))
                .message("get user successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<User> users = userService.getAllUsers();

        ApiResponse response = ApiResponse.builder()
                .data(users.stream().map(userMapper::toUserDTO))
                .message("get users successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
