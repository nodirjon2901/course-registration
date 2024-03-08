package uz.nt.courseregistration.domain.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.courseregistration.domain.entity.BaseEntity;
import uz.nt.courseregistration.domain.entity.course.CourseEntity;
import uz.nt.courseregistration.domain.entity.group.GroupEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Double balance;

    @ManyToMany(targetEntity = CourseEntity.class)
    private List<CourseEntity> courses;

    @ManyToOne(cascade = CascadeType.MERGE)
    private GroupEntity group;
}
