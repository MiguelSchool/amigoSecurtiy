package com.example.amigosecurtiy.security.roles;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum UserRole {
    STUDENT(Sets.newHashSet(
            UserPermission.COURSE_READ,
            UserPermission.STUDENT_READ,
            UserPermission.STUDENT_WRITE)
    ),
    ADMIN(Sets.newHashSet(
            UserPermission.STUDENT_WRITE,
            UserPermission.STUDENT_READ,
            UserPermission.COURSE_READ,
            UserPermission.COURSE_WRITE)
    ),
    ADMINTRAINEE(Sets.newHashSet(
            UserPermission.STUDENT_READ,
            UserPermission.COURSE_READ
    ));

    private final Set<UserPermission>permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return simpleGrantedAuthorities;
    }
}
