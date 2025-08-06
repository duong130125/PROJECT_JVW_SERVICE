package ra.project_jvw_service.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class AssessmentResultResponse {
    private Integer resultId;
    private BigDecimal score;
    private String comments;
    private String evaluatedBy;
    private String evaluationDate;
    private Integer assignmentId;
    private String studentCode;
    private String department;
    private String academicRank;
    private Integer roundId;
    private String roundName;
    private Integer criterionId;
    private String criterionName;
    private BigDecimal maxScore;
}