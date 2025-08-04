package ra.project_jvw_service.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EvaluationCriteriaResponse {
    private Integer criterionId;
    private String criterionName;
    private String description;
    private BigDecimal maxScore;
}
