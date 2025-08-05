package ra.project_jvw_service.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RoundCriterionResponse {
    private Integer roundCriterionId;
    private Integer roundId;
    private String roundName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer criterionId;
    private String criterionName;
    private BigDecimal maxScore;
    private BigDecimal weight;
}
