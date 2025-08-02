package ra.project_jvw_service.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse <T>{
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus status;
    private Object errors;
    private LocalDateTime timestamp;
}