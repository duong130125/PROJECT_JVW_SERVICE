package ra.project_jvw_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.LoginRequest;
import ra.project_jvw_service.model.dto.request.RegisterRequest;
import ra.project_jvw_service.model.dto.response.JWTResponse;
import ra.project_jvw_service.model.dto.response.UserResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.repository.UserRepository;
import ra.project_jvw_service.security.jwt.JWTProvider;
import ra.project_jvw_service.security.principal.CustomUserPrincipal;
import ra.project_jvw_service.service.UserService;
import ra.project_jvw_service.utils.Role;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        Role role = registerRequest.getRoles() != null ? registerRequest.getRoles() : Role.STUDENT;

        if (role == Role.ADMIN) {
            throw new IllegalArgumentException("Không thể đăng ký với quyền ADMIN!");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .passwordHash(passwordEncoder.encode(registerRequest.getPasswordHash()))
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .isActive(true)
                .role(role)
                .build();

        return userRepository.save(user);
    }


    @Override
    public JWTResponse login(LoginRequest loginRequest) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        }catch(AuthenticationException e){
            throw new IllegalArgumentException("Sai username hoặc password!");
        }

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        String token = jwtProvider.generateToken(customUserPrincipal.getUsername());

        return JWTResponse.builder()
                .username(customUserPrincipal.getUsername())
                .fullName(customUserPrincipal.getFullName())
                .isActive(customUserPrincipal.isEnabled())
                .email(customUserPrincipal.getEmail())
                .phoneNumber(customUserPrincipal.getPhoneNumber())
                .authorities(customUserPrincipal.getAuthorities())
                .token(token)
                .build();
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public List<UserResponse> getAllUsers(Optional<Role> role) {
        if (role.isPresent() && role.get() == Role.ADMIN) {
            throw new IllegalArgumentException("Không được phép lấy danh sách tài khoản ADMIN");
        }

        List<User> users = role.isPresent() ?
                userRepository.findAllByRole(role.get()) :
                userRepository.findAllByRoleNot(Role.ADMIN);
        return users.stream().map(this::toDTO).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    @Override
    public UserResponse createUser(RegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }

        Role role = dto.getRoles() != null ? dto.getRoles() : Role.STUDENT;

        if (role == Role.ADMIN) {
            throw new IllegalArgumentException("Không thể đăng ký với quyền ADMIN!");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPasswordHash()))
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .isActive(true)
                .role(role)
                .build();
        return toDTO(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, RegisterRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserStatus(Long id, Boolean isActive) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        user.setIsActive(isActive);
        userRepository.save(user);
    }

    @Override
    public void updateUserRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        user.setRole(role);
        userRepository.save(user);
    }

    private UserResponse toDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setPasswordHash(user.getPasswordHash());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setIsActive(user.getIsActive());
        dto.setRole(user.getRole());
        return dto;
    }
}
