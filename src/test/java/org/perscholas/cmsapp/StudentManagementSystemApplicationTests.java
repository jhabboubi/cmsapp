package org.perscholas.cmsapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.perscholas.cmsapp.dao.StudentsRepoI;
import org.perscholas.cmsapp.exceptions.MyExceptions;
import org.perscholas.cmsapp.models.Course;
import org.perscholas.cmsapp.models.Students;
import org.perscholas.cmsapp.service.StudentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StudentManagementSystemApplicationTests {

	@Autowired
	StudentCoursesService studentCoursesService;
	@Autowired
	private StudentsRepoI studentsRepoI;



	static List<Students> expected() {
		Students student = new Students(1, "Jafer", "Jafer@gmail.com", "password");
		Students student2 = new Students(2, "Mohammed", "Mohammed@gmail.com", "password");
		Students student3 = new Students(3, "Anjana", "Anjana@gmail.com", "password");
		Course course = new Course(1412, "java", "Jafer");
		Course course2 = new Course(2312, "spring", "Kevin");
		Course course3 = new Course(3232, "sql", "Tyron");
		student.addCourse(course);
		student.addCourse(course2);
		student2.addCourse(course3);

		List<Students> expected = new ArrayList<>();
		expected.add(student);
		expected.add(student2);
		expected.add(student3);
		return expected;

	}

	@Test @Order(1)
	void getAllStudents() throws MyExceptions {
		assertThat(studentCoursesService.getAllStudents()).isEqualTo(expected());
	}

	@Test @Order(2)
	void getAllStudentsExc() {
		studentsRepoI.deleteAll();
		assertThatThrownBy(() -> studentCoursesService.getAllStudents()).isInstanceOf(MyExceptions.class);

	}
}
