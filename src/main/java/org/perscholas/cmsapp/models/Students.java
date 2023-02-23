package org.perscholas.cmsapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Entity
@Table(name = "stu")
@Slf4j
@NoArgsConstructor
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Students {

    @Id @NonNull@NotNull
    Integer id;

    @NonNull
    @NotBlank(message = "Please provide a name.")
    @Size(min = 2, max = 20, message = "Please provide a name of length 2 to 20.")

    String name;
    @NonNull
    @Email(message = "Provide a valid email address.", regexp = ".+@.+\\..+")
    String email;

    @NonNull
    String password;



    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    Set<Course> courses = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return id.equals(students.id) && name.equals(students.name) && email.equals(students.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    public void addCourse(Course course){
        courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course){
        courses.remove(course);
        course.getStudents().remove(this);
    }
}
