package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ra.project_jvw_service.model.entity.User;

import java.math.BigDecimal;

@Data
public class AssessmentResultRequest {
    @NotNull(message = "Assignment ID không được để trống")
    private Integer assignmentId;

    @NotNull(message = "Round ID không được để trống")
    private Integer roundId;

    @NotNull(message = "Criterion ID không được để trống")
    private Integer criterionId;

    @DecimalMin(value = "0.0")
    private BigDecimal score;

    private String comments;

    @NotNull
    private User evaluatedBy;
}