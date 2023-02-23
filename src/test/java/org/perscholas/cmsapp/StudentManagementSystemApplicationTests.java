package org.perscholas.cmsapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.perscholas.cmsapp.dao.CoursesRepoI;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.models.Course;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.StudentAndCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class StudentManagementSystemApplicationTests {

	StudentsRepoI studentsRepoI;
	CoursesRepoI coursesRepoI;
	StudentAndCoursesService studentAndCoursesService;

	@Autowired
	public StudentManagementSystemApplicationTests(StudentsRepoI studentsRepoI, CoursesRepoI coursesRepoI, StudentAndCoursesService studentAndCoursesService) {
		this.studentsRepoI = studentsRepoI;
		this.coursesRepoI = coursesRepoI;
		this.studentAndCoursesService = studentAndCoursesService;
	}
	Students student = new Students(123, "Jafer", "Jafer@gmail.com",  "password");
	Students student2 = new Students(444, "Mohammed", "Mohammed@gmail.com",  "password");
	Students student3 = new Students(555, "Anjana", "Anjana@gmail.com",  "password");
	Course course = new Course(1, "java", "Jafer");
	Course course2 = new Course(2, "spring", "Kevin");
	Course course3 = new Course(3, "sql", "Tyron");


	@Test
	void exceptionTesting() {
		assertThatThrownBy(() -> {
			studentAndCoursesService.getAllStudents();
		}).isInstanceOf(Exception.class);

	}

	@Test
	void contextLoads() {
		if(studentsRepoI.findById(123).isPresent()){
			Students s = studentsRepoI.findById(123).get();
			assertThat(s).isEqualTo(student);
		}







	}

}
