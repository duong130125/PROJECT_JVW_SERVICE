package ra.project_jvw_service.service;

import org.springframework.security.core.Authentication;
import ra.project_jvw_service.model.dto.request.LoginRequest;
import ra.project_jvw_service.model.dto.request.RegisterRequest;
import ra.project_jvw_service.model.dto.response.JWTResponse;
import ra.project_jvw_service.model.dto.response.UserResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.utils.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(RegisterRequest registerRequest);

    JWTResponse login(LoginRequest loginRequest);

    void logout(String token);

    UserResponse getCurrentUserProfile(Authentication authentication);

    List<UserResponse> getAllUsers(Optional<Role> role);
    UserResponse getUserById(Long id);
    UserResponse createUser(RegisterRequest dto);
    UserResponse updateUser(Long id, RegisterRequest dto);
    void deleteUser(Long id);
    void updateUserStatus(Long id, Boolean isActive);
    void updateUserRole(Long id, Role role);
}
