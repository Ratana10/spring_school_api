package com.piseth.schoolapi.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.piseth.schoolapi.users.Permission.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {
  ADMIN(Set.of(
          STUDY_TYPE_READ,
          STUDY_TYPE_WRITE,
          CATEGORY_READ,
          CATEGORY_WRITE,
          COURSE_READ,
          COURSE_WRITE,
          STUDENT_READ,
          STUDENT_WRITE
  )),
  STUDENT(Set.of(
          COURSE_READ,
          STUDENT_WRITE,
          CATEGORY_READ
  ))

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}