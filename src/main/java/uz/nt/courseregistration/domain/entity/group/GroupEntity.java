package uz.nt.courseregistration.domain.entity.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.courseregistration.domain.entity.BaseEntity;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class GroupEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private int capacity;

    private boolean isEmpty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<UserEntity> users = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private CourseEntity course;

}
