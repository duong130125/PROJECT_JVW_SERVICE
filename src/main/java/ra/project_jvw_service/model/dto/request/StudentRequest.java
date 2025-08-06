package ra.project_jvw_service.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRequest {
    @NotNull(message = "User ID không được để trống")
    private Integer userId;

    @NotBlank(message = "Mã sinh viên không được để trống")
    private String studentCode;

    private String major;

    private LocalDate dateOfBirth;

    private String address;
}
