package uz.nt.courseregistration.domain.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseCreateDTO {

    private String name;

    private Double price;

}
