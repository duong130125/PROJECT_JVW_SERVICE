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
@Table(name = "RoundCriteria", uniqueConstraints = @UniqueConstraint(columnNames = {"RoundID", "CriterionID"}))
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoundCriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundCriterionId;

    @ManyToOne
    @JoinColumn(name = "RoundID", nullable = false)
    private AssessmentRound round;

    @ManyToOne
    @JoinColumn(name = "CriterionID", nullable = false)
    private EvaluationCriterion criterion;

    @Column(nullable = false)
    @DecimalMin("0.0")
    private BigDecimal weight;

    @Column(updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
