package ra.project_jvw_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_jvw_service.model.dto.request.RegisterRequest;
import ra.project_jvw_service.model.dto.response.APIResponse;
import ra.project_jvw_service.model.dto.response.UserResponse;
import ra.project_jvw_service.service.UserService;
import ra.project_jvw_service.utils.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Lấy danh sách tất cả người dùng (có thể lọc theo vai trò)
    @GetMapping
    public ResponseEntity<APIResponse<List<UserResponse>>> getAllUsers(@RequestParam(required = false) Role role) {
        return new ResponseEntity<>(
                new APIResponse<>(true, "Lấy danh sách tất cả người dùng theo vai trò", userService.getAllUsers(Optional.ofNullable(role)), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Lấy thông tin chi tiết một người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new APIResponse<>(true, "Lấy thông tin chi tiết một người dùng theo ID", userService.getUserById(id), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Tạo tài khoản người dùng mới (chỉ ADMIN mới có quyền tạo)
    @PostMapping
    public ResponseEntity<APIResponse<UserResponse>> createUser(@RequestBody RegisterRequest dto) {
        return new ResponseEntity<>(
                new APIResponse<>(true, "Tạo tài khoản người dùng mới", userService.createUser(dto), HttpStatus.CREATED, null, LocalDateTime.now()), HttpStatus.CREATED);
    }

    //Cập nhật thông tin cơ bản của người dùng
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody RegisterRequest dto) {
        return new ResponseEntity<>(
                new APIResponse<>(true, "Cập nhật thông tin cơ bản của người dùng", userService.updateUser(id, dto), HttpStatus.OK, null, LocalDateTime.now()), HttpStatus.OK);
    }

    //Cập nhật trạng thái hoạt động của người dùng (kích hoạt/tạm khóa)
    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<Void>> updateUserStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        userService.updateUserStatus(id, isActive);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Void> updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        userService.updateUserRole(id, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
