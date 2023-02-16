package org.perscholas.cmsapp.models;

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
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id @NonNull
    int id;
    @NonNull
    String name;
    @NonNull
    String instructor;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Students> students = new LinkedHashSet<>();

}
