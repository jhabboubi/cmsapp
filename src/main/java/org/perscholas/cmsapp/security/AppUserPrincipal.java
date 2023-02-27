package org.perscholas.cmsapp.security;

import org.perscholas.cmsapp.models.AuthGroup;
import org.perscholas.cmsapp.models.Students;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUserPrincipal implements UserDetails {

    private Students student;
    private List<AuthGroup> authGroup;

    public AppUserPrincipal(Students student, List<AuthGroup> authGroup) {
        this.student = student;
        this.authGroup = authGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<SimpleGrantedAuthority> theList = new ArrayList<>();
//        for(AuthGroup auth: authGroup){
//            theList.add(new SimpleGrantedAuthority(auth.getRole()));
//        }

        return authGroup.stream().map(auth -> new SimpleGrantedAuthority(auth.getRole())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getEmail();
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
