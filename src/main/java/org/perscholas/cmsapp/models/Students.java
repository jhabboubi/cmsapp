package org.perscholas.cmsapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Entity
@Table(name = "stu")
@Slf4j
@NoArgsConstructor

@Getter
@ToString

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Students {


    @Setter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Setter
    @NonNull @NotBlank( message = "can't be blank")
    String name;
    @Setter
    @NonNull @Email(message = "not a valid email")
    @Column(unique = true)
    String email;
    @NonNull @NotBlank(message = "can't be blank") @Size(min=3, message = "not less than 3 characters")
    String password;

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    public Students(@NonNull String name, @NonNull String email, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

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

    public Students(Integer id, @NonNull String name, @NonNull String email, @NonNull String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
