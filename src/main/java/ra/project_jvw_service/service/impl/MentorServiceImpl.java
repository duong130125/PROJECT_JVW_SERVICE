package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.MentorRequest;
import ra.project_jvw_service.model.dto.response.MentorResponse;
import ra.project_jvw_service.model.entity.Mentor;
import ra.project_jvw_service.model.entity.User;
import ra.project_jvw_service.repository.MentorRepository;
import ra.project_jvw_service.repository.UserRepository;
import ra.project_jvw_service.service.MentorService;
import ra.project_jvw_service.utils.Role;

import java.util.List;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public List<MentorResponse> getAllMentors(Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.STUDENT) {
            throw new AccessDeniedException("Bạn không có quyền truy cập danh sách mentor");
        }

        List<Mentor> mentors = mentorRepository.findAll();
        return mentors.stream().map(this::toDTO).toList();
    }

    @Override
    public MentorResponse getMentorById(Long id, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mentor"));

        if (currentUser.getRole() == Role.MENTOR && !mentor.getMentorId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Bạn chỉ có thể xem thông tin của chính mình");
        }

        return toDTO(mentor);
    }

    @Override
    public MentorResponse createMentor(MentorRequest request) {
        User user = userRepository.findById(Long.valueOf(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (user.getRole() != Role.MENTOR) {
            throw new RuntimeException("User không có role MENTOR");
        }

        Mentor mentor = Mentor.builder()
                .mentorId(user.getUserId())
                .department(request.getDepartment())
                .academicRank(request.getAcademicRank())
                .build();

        return toDTO(mentorRepository.save(mentor));
    }

    @Override
    public MentorResponse updateMentor(Long id, MentorRequest request, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        Mentor mentor = mentorRepository.findById(id).orElseThrow();

        if (user.getRole() == Role.MENTOR && !user.getUserId().equals(mentor.getMentorId())) {
            throw new RuntimeException("Bạn chỉ được chỉnh sửa thông tin của chính mình");
        }

        mentor.setDepartment(request.getDepartment());
        mentor.setAcademicRank(request.getAcademicRank());
        return toDTO(mentorRepository.save(mentor));
    }

    private MentorResponse toDTO(Mentor mentor) {
        User user = mentor.getUser();
        return MentorResponse.builder()
                .mentorId(mentor.getMentorId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .department(mentor.getDepartment())
                .academicRank(mentor.getAcademicRank())
                .build();
    }
}
