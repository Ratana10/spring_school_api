package com.piseth.schoolapi.roles;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {
    ADMIN(Set.of(
            PermissionEnum.CATEGORY_READ,
            PermissionEnum.CATEGORY_WRITE,
            PermissionEnum.COURSE_WRITE,
            PermissionEnum.COURSE_READ
    )),
    USER(Set.of(
            PermissionEnum.COURSE_READ
    ));
    private Set<PermissionEnum> permissions;
    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
                .collect(Collectors.toSet());

        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+this.name());
        grantedAuthorities.add(role);
        return grantedAuthorities;
    }
}