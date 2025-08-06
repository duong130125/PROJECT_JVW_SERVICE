package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.InternshipAssignmentRequest;
import ra.project_jvw_service.model.dto.request.InternshipAssignmentStatusUpdateRequest;
import ra.project_jvw_service.model.dto.response.InternshipAssignmentResponse;
import ra.project_jvw_service.model.entity.*;
import ra.project_jvw_service.repository.*;
import ra.project_jvw_service.service.InternshipAssignmentService;
import ra.project_jvw_service.utils.AssignmentStatus;
import ra.project_jvw_service.utils.Role;

import java.util.List;

@Service
public class InternshipAssignmentServiceImpl implements InternshipAssignmentService {
    @Autowired
    private InternshipAssignmentRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private InternshipPhaseRepository phaseRepository;

    @Override
    public List<InternshipAssignmentResponse> getAllInternshipAssignment(Long phaseId, User currentUser) {
        List<InternshipAssignment> list;

        if (currentUser.getRole() == Role.ADMIN) {
            list = (phaseId != null)
                    ? repository.findAllByPhase_PhaseId(phaseId)
                    : repository.findAll();
        } else if (currentUser.getRole() == Role.MENTOR) {
            list = (phaseId != null)
                    ? repository.findAllByMentor_UserIdAndPhase_PhaseId(Long.valueOf(currentUser.getUserId()), phaseId)
                    : repository.findAllByMentor_UserId(Long.valueOf(currentUser.getUserId()));
        } else if (currentUser.getRole() == Role.STUDENT) {
            list = (phaseId != null)
                    ? repository.findAllByStudent_UserIdAndPhase_PhaseId(Long.valueOf(currentUser.getUserId()), phaseId)
                    : repository.findAllByStudent_UserId(Long.valueOf(currentUser.getUserId()));
        } else {
            throw new RuntimeException("Không có quyền truy cập");
        }

        return list.stream().map(this::toDTO).toList();
    }

    @Override
    public InternshipAssignmentResponse getByIdInternshipAssignment(Long id, User currentUser) {
        InternshipAssignment assignment;

        if (currentUser.getRole() == Role.ADMIN) {
            assignment = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công thực tập với ID: " + id));
        } else if (currentUser.getRole() == Role.MENTOR) {
            assignment = repository.findByIdAndMentor_UserId(id, Long.valueOf(currentUser.getUserId()))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công thực tập cho người hướng dẫn"));
        } else if (currentUser.getRole() == Role.STUDENT) {
            assignment = repository.findByIdAndStudent_UserId(id, Long.valueOf(currentUser.getUserId()))
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công thực tập cho sinh viên"));
        } else {
            throw new RuntimeException("Không có quyền truy cập");
        }
        return toDTO(assignment);
    }

    @Override
    public InternshipAssignmentResponse createInternshipAssignment(InternshipAssignmentRequest request) {
        Student student = studentRepository.findById(Long.valueOf(request.getStudentId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + request.getStudentId()));
        Mentor mentor = mentorRepository.findById(request.getMentorId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người hướng dẫn với ID: " + request.getMentorId()));
        InternshipPhase phase = phaseRepository.findById(request.getPhaseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giai đoạn thực tập với ID: " + request.getPhaseId()));

        //Kiểm tra trùng assignment (theo student + phase)
        boolean exists = repository.existsByStudent_StudentIdAndPhase_PhaseId(
                student.getStudentId(),
                phase.getPhaseId()
        );
        if (exists) {
            throw new RuntimeException("Sinh viên này đã được phân công trong giai đoạn này!");
        }

        InternshipAssignment assignment = InternshipAssignment.builder()
                .student(student)
                .mentor(mentor)
                .phase(phase)
                .status(AssignmentStatus.PENDING)
                .build();
        return toDTO(repository.save(assignment));
    }

    @Override
    public InternshipAssignmentResponse updateStatusInternshipAssignment(Long id, InternshipAssignmentStatusUpdateRequest request) {
        InternshipAssignment assignment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công thực tập với ID: " + id));

        AssignmentStatus currentStatus = assignment.getStatus();
        AssignmentStatus newStatus = request.getStatus();

        // Kiểm tra chuyển trạng thái hợp lệ
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new IllegalArgumentException("Chuyển trạng thái từ " + currentStatus + " sang " + newStatus + " không hợp lệ.");
        }

        assignment.setStatus(newStatus);
        return toDTO(repository.save(assignment));
    }

    private boolean isValidStatusTransition(AssignmentStatus current, AssignmentStatus next) {
        return switch (current) {
            case PENDING -> next == AssignmentStatus.IN_PROGRESS || next == AssignmentStatus.CANCELLED;
            case IN_PROGRESS -> next == AssignmentStatus.COMPLETED || next == AssignmentStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
    }

    private InternshipAssignmentResponse toDTO(InternshipAssignment iA) {
        return InternshipAssignmentResponse.builder()
                .assignmentId(iA.getAssignmentId())
                .studentId(iA.getStudent().getStudentId())
                .studentCode(iA.getStudent().getStudentCode())
                .mentorId(iA.getMentor().getMentorId())
                .department(iA.getMentor().getDepartment())
                .academicRank(iA.getMentor().getAcademicRank())
                .phaseId(iA.getPhase().getPhaseId())
                .phaseName(iA.getPhase().getPhaseName())
                .status(iA.getStatus())
                .build();
    }
}
