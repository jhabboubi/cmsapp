package org.perscholas.cmsapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Entity
@Table(name = "courses")
@Slf4j
@NoArgsConstructor
@Setter
@Getter

@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @NonNull
    int id;
    @NonNull @NotBlank( message = "can't be blank")
    String name;
    @NonNull @NotBlank(message = "can't be blank") @Size(min = 3,max = 20,message = "between 3 and 20 chars")
    String instructor;


    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    Set<Students> students = new LinkedHashSet<>();

    @Override
    public String toString() {
        return String.format(" Subject: %s Instructor: %s ", name, instructor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && name.equals(course.name) && instructor.equals(course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor);
    }
}
