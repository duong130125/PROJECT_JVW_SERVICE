package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.LoginRequest;
import ra.project_jvw_service.model.dto.request.RegisterRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.JWTResponse;
import ra.project_jvw_service.model.dto.response.UserResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Lấy thông tin hồ sơ của người dùng hiện tại
    @GetMapping("/me")
    public ResponseEntity<APIResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        return new ResponseEntity<>(
                new APIResponse<>
                    (true, "Lấy thông tin hồ sơ của người dùng hiện tại",userService.getCurrentUserProfile(authentication), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse<User>> registerCustomer(@RequestBody @Valid RegisterRequest customerRegister) {
        return new ResponseEntity<>(
                new APIResponse<>
                    (true, "Đăng ký tài khoản thành công!", userService.register(customerRegister), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<JWTResponse>> login(@RequestBody @Valid LoginRequest customerLogin) {
        return new ResponseEntity<>(
                new APIResponse<>
                    (true, "Đăng nhập tài khoản thành công!", userService.login(customerLogin), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

}
