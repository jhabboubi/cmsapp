package org.perscholas.cmsapp.dao;

import org.perscholas.cmsapp.models.Course;
import org.perscholas.cmsapp.models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepoI extends JpaRepository<Course,Integer> {
}
