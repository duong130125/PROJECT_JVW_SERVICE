package ra.project_jvw_service.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "AssessmentResults",
        uniqueConstraints = @UniqueConstraint(columnNames = {"AssignmentID", "RoundID", "CriterionID"})
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @ManyToOne
    @JoinColumn(name = "AssignmentID", nullable = false)
    private InternshipAssignment assignment;

    @ManyToOne
    @JoinColumn(name = "RoundID", nullable = false)
    private AssessmentRound round;

    @ManyToOne
    @JoinColumn(name = "CriterionID", nullable = false)
    private EvaluationCriterion criterion;

    @Column(nullable = false)
    @DecimalMin("0.0")
    private BigDecimal score;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "EvaluatedBy", referencedColumnName = "UserID", nullable = false)
    private User evaluatedBy;

    @Column(name = "EvaluationDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime evaluationDate;

    @Column(name = "CreatedAt", updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
