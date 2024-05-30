package com.piseth.schoolapi.users;

import com.piseth.schoolapi.roles.Role;
import com.piseth.schoolapi.roles.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {RoleService.class, UserService.class}
)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "roleIds", target = "roles")
    })
    User toUser(UserDTO userDTO);

    @Mappings({
            @Mapping(source = "roles", target = "roleIds", qualifiedByName = "rolesToRoleIds")
    })
    UserDTO toUserDTO(User user);

    @Named("rolesToRoleIds")
    default List<Long> rolesToRoleIds(List<Role> roles) {
        return roles.stream().map(Role::getId).toList();

    }

}
