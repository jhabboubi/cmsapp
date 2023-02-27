package org.perscholas.cmsapp.dao;

import org.perscholas.cmsapp.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {

    List<AuthGroup> findByEmail(String email);

}
