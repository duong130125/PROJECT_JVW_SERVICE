package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AssessmentRoundRequest {

    @NotNull(message = "ID của giai đoạn thực tập không được để trống")
    private Integer phaseId;

    @NotBlank(message = "Tên đợt đánh giá không được để trống")
    private String roundName;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String description;
    private Boolean isActive;
}
