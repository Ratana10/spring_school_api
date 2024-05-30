package com.piseth.schoolapi.roles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "name", source = "name")
    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);
}
