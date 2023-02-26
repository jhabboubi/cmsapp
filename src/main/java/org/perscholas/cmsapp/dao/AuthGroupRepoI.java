package org.perscholas.cmsapp.dao;

import org.perscholas.cmsapp.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {

    List<AuthGroup> findByEmail(String email);

}
