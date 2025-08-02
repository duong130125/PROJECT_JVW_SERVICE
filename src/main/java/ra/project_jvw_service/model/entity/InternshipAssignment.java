package ra.project_jvw_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.project_jvw_service.utils.AssignmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "InternshipAssignments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"StudentID", "PhaseID"})
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InternshipAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assignmentId;

    @ManyToOne
    @JoinColumn(name = "StudentID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "MentorID", nullable = false)
    private Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "PhaseID", nullable = false)
    private InternshipPhase phase;

    @Column(name = "AssignedDate", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assignedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, columnDefinition = "ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING'")
    private AssignmentStatus status = AssignmentStatus.PENDING;

    @Column(name = "CreatedAt", updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
