package uz.nt.courseregistration.domain.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupCreateDTO {

    private Long courseId;

    private String name;

    private int capacity;

}
