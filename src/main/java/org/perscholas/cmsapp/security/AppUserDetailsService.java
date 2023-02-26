package org.perscholas.cmsapp.security;

import org.perscholas.cmsapp.dao.AuthGroupRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.AuthGroup;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.StudentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {
    StudentsRepoI studentsRepoI;
    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public AppUserDetailsService(StudentsRepoI studentsRepoI, AuthGroupRepoI authGroupRepoI) {
        this.studentsRepoI = studentsRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AuthGroup> authGroupList = authGroupRepoI.findByEmail(username);


        return new AppUserPrincipal(studentsRepoI.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("did not find email"))
                ,authGroupList);
    }
}
