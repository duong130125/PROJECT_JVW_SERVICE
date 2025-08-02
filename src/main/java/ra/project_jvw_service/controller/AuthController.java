package ra.project_jvw_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project_jvw_service.model.dto.request.LoginRequest;
import ra.project_jvw_service.model.dto.request.RegisterRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.JWTResponse;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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
