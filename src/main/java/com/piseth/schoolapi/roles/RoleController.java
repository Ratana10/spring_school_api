package com.piseth.schoolapi.roles;

import com.piseth.schoolapi.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody RoleDTO dto) {
        Role role = RoleMapper.INSTANCE.toRole(dto);
        role = roleService.create(role);

        ApiResponse response = ApiResponse.builder()
                .data(RoleMapper.INSTANCE.toRoleDTO(role))
                .message("create role successful")
                .httpStatus(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody RoleDTO dto) {
        Role role = RoleMapper.INSTANCE.toRole(dto);
        role = roleService.update(id, role);

        ApiResponse response = ApiResponse.builder()
                .data(RoleMapper.INSTANCE.toRoleDTO(role))
                .message("update role successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        roleService.delete(id);

        ApiResponse response = ApiResponse.builder()
                .data(null)
                .message("delete role successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
       Role byId = roleService.getById(id);

        ApiResponse response = ApiResponse.builder()
                .data(RoleMapper.INSTANCE.toRoleDTO(byId))
                .message("get role successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<Role> roles = roleService.getAllRoles();

        ApiResponse response = ApiResponse.builder()
                .data(roles)
                .message("get all roles successful")
                .httpStatus(HttpStatus.OK.value())
                .build();

        return ResponseEntity
                .ok()
                .body(response);
    }
}
