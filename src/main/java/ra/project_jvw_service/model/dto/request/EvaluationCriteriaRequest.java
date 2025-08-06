package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EvaluationCriteriaRequest {
    @NotBlank(message = "Tên tiêu chí đánh giá không được để trống")
    private String criterionName;

    private String description;
    @NotNull(message = "Điểm tối đa cho tiêu chí này không được để trống")
    @Min(value = 1, message = "Điểm tối đa phải lớn hơn 0")
    private BigDecimal maxScore;
}
