package org.perscholas.cmsapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
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
    @NonNull
    String name;
    @NonNull
    String instructor;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Students> students = new LinkedHashSet<>();

    @Override
    public String toString() {
        return String.format(" Subject: %s Instructor: %s ", name, instructor);
    }
}
