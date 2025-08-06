package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssessmentResultUpdateRequest {
    @DecimalMin(value = "0.0")
    private BigDecimal score;

    private String comments;
}