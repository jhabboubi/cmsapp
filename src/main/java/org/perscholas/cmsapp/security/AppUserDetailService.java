package org.perscholas.cmsapp.security;

import org.perscholas.cmsapp.dao.AuthGroupRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.AuthGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailService implements UserDetailsService {
    StudentsRepoI studentsRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailService(StudentsRepoI studentsRepoI, AuthGroupRepoI authGroupRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new AppUserPrincipal(
                studentsRepoI.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Email Not Found"))
                , authGroupRepoI.findByEmail(username));
    }
}
