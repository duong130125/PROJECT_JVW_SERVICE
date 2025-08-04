package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorRequest {
    private Integer userId;

    @NotBlank(message = "Bộ môn không được để trống")
    private String department;

    @NotBlank(message = "Học hàm không được để trống")
    private String academicRank;
}
