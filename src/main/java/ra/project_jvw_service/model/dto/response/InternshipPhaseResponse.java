package ra.project_jvw_service.model.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InternshipPhaseResponse {
    private Integer phaseId;
    private String phaseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
