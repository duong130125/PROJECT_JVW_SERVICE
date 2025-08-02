package ra.project_jvw_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mentors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Mentor {
    @Id
    private Integer mentorId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "MentorID")
    private User user;

    private String department;

    private String academicRank;

    @Column(updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
