package uz.nt.courseregistration.domain.entity.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.courseregistration.domain.entity.BaseEntity;
import uz.nt.courseregistration.domain.entity.group.GroupEntity;
import uz.nt.courseregistration.domain.entity.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class CourseEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private Double price;

    @ManyToMany(targetEntity = UserEntity.class)
    private List<UserEntity> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<GroupEntity> groups = new ArrayList<>();

}
