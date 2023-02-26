package org.perscholas.cmsapp.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.perscholas.cmsapp.models.AuthGroup;
import org.perscholas.cmsapp.models.Students;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUserPrincipal implements UserDetails {

    Students students;
    List<AuthGroup> authGroup;

    public AppUserPrincipal(Students students, List<AuthGroup> authGroup) {
        this.students = students;
        this.authGroup = authGroup;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authGroup.stream().map(auth -> new SimpleGrantedAuthority(auth.getRole())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return students.getPassword();
    }

    @Override
    public String getUsername() {
        return students.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
