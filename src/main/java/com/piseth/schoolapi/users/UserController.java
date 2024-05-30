package com.piseth.schoolapi.users;

import com.piseth.schoolapi.exception.ApiResponse;
import com.piseth.schoolapi.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
//    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody UserDTO dto) {

//        user = userService.create(user);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("create user successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

//    @PutMapping("{id}")
//    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UserDTO dto) {
//        User category = UserMapper.INSTANCE.toUser(dto);
//        category = categoryService.update(id, category);
//
//        ApiResponse response = ApiResponse.builder()
//                .data(UserMapper.INSTANCE.toUserDTO(category))
//                .message("update category successful")
//                .httpStatus(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
//        categoryService.delete(id);
//
//        ApiResponse response = ApiResponse.builder()
//                .data(null)
//                .message("delete category successful")
//                .httpStatus(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
//       User byId = categoryService.getById(id);
//
//        ApiResponse response = ApiResponse.builder()
//                .data(UserMapper.INSTANCE.toCategoryDTO(byId))
//                .message("get category successful")
//                .httpStatus(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse> getAll() {
//        List<Category> categories = categoryService.getCategories();
//
//        ApiResponse response = ApiResponse.builder()
//                .data(categories)
//                .message("get categories successful")
//                .httpStatus(HttpStatus.OK.value())
//                .build();
//
//        return ResponseEntity
//                .ok()
//                .body(response);
//    }
}
