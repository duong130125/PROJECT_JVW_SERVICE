package ra.project_jvw_service.model.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponse {
    private Integer studentId;
    private String studentCode;
    private String major;
    private LocalDate dateOfBirth;
    private String address;

}
