package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoundCriterionRequest {
    @NotNull(message = "ID của đợt đánh giá không được để trống")
    private Integer roundId;

    @NotNull(message = "ID của tiêu chí không được để trống")
    private Integer criterionId;

    @NotNull(message = "Trọng số của tiêu chí trong đợt đánh giá này không được để trống")
    @DecimalMin(value = "0.0", message = "Trọng số của tiêu chí trong đợt đánh giá này phải lớn hơn 0.0")
    private BigDecimal weight;
}