package ra.project_jvw_service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentRequest {
    private Integer userId;
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String studentCode;

    private String major;

    private LocalDate dateOfBirth;

    private String address;
}
