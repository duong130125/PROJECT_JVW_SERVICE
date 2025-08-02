package ra.project_jvw_service.model.dto.response;

import lombok.Data;
import ra.project_jvw_service.utils.Role;

@Data
public class UserResponse {
    private Integer userId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private Role role;
}
