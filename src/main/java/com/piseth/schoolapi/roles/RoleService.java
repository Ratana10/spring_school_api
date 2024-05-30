package com.piseth.schoolapi.roles;

import java.util.List;

public interface RoleService {
    Role create(Role role);

    Role update(Long id, Role role);

    void delete(Long id);
    Role getById(Long id);
    List<Role> getAllRoles();
    List<Role> findRoleByIds(List<Long> roleIds);

}
