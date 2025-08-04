package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class InternshipPhaseRequest {
    private Integer phaseId;
    @NotBlank(message = "Tên giai đoạn không được để trống")
    private String phaseName;
    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // Format: "yyyy-MM-dd"
    @NotNull(message = "Ngày kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;   // Format: "yyyy-MM-dd"
    private String description;
}
