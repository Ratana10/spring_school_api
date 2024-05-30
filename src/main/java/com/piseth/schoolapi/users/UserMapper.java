package com.piseth.schoolapi.users;

import com.piseth.schoolapi.roles.Role;
import com.piseth.schoolapi.roles.RoleService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "roleIds", target = "roles", qualifiedByName = "roleIdsToRoles")
    })
    User toUser(UserDTO userDTO, @Context RoleService roleService);

    @Named("roleIdsToRoles")
    default List<Role> roleIdsToRoles(List<Long> roleIds, @Context RoleService roleService) {
        return roleService.findRoleByIds(roleIds);
    }
}
