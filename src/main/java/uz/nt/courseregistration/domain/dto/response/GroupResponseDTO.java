package uz.nt.courseregistration.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDTO {

    private Long id;

    private Long courseId;

    private String name;

    private int capacity;

    private boolean isEmpty;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
