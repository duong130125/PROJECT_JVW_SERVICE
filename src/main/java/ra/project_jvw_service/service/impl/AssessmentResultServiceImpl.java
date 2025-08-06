package ra.project_jvw_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.project_jvw_service.model.dto.request.AssessmentResultRequest;
import ra.project_jvw_service.model.dto.request.AssessmentResultUpdateRequest;
import ra.project_jvw_service.model.dto.response.AssessmentResultResponse;
import ra.project_jvw_service.model.entity.*;
import ra.project_jvw_service.repository.*;
import ra.project_jvw_service.service.AssessmentResultService;
import ra.project_jvw_service.utils.Role;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentResultServiceImpl implements AssessmentResultService {

    private final AssessmentResultRepository resultRepo;
    private final InternshipAssignmentRepository assignmentRepo;
    private final AssessmentRoundRepository roundRepo;
    private final EvaluationCriteriaRepository criterionRepo;

    @Override
    public List<AssessmentResultResponse> getAllAssessmentResult(Long assignmentId, User currentUser) {
        List<AssessmentResult> results;

        switch (currentUser.getRole()) {
            case ADMIN:
                results = (assignmentId != null)
                        ? resultRepo.findAllByAssignment_AssignmentId(assignmentId)
                        : resultRepo.findAll();
                break;

            case MENTOR:
                results = resultRepo.findAllByAssignment_Mentor_UserId(Long.valueOf(currentUser.getUserId()));
                break;

            case STUDENT:
                results = resultRepo.findAllByAssignment_Student_UserId(Long.valueOf(currentUser.getUserId()));
                break;

            default:
                throw new SecurityException("Không có quyền truy cập");
        }

        return results.stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional
    public AssessmentResultResponse createAssessmentResult(AssessmentResultRequest request, User currentUser) {
        if (currentUser.getRole() != Role.MENTOR) {
            throw new SecurityException("Chỉ giáo viên hướng dẫn mới được đánh giá");
        }

        InternshipAssignment assignment = assignmentRepo.findById(Long.valueOf(request.getAssignmentId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phân công thực tập"));

        if (!assignment.getMentor().getMentorId().equals(currentUser.getUserId())) {
            throw new SecurityException("Chỉ mentor được phân công mới được đánh giá");
        }

        // Kiểm tra trùng kết quả
        resultRepo.findByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(
                request.getAssignmentId(), request.getRoundId(), request.getCriterionId()
        ).ifPresent(r -> {
            throw new RuntimeException("Kết quả đánh giá đã tồn tại");
        });

        AssessmentRound round = roundRepo.findById(Long.valueOf(request.getRoundId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt đánh giá"));

        EvaluationCriterion criterion = criterionRepo.findById(Long.valueOf(request.getCriterionId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá"));

        AssessmentResult result = AssessmentResult.builder()
                .assignment(assignment)
                .round(round)
                .criterion(criterion)
                .score(request.getScore())
                .comments(request.getComments())
                .evaluatedBy(currentUser)
                .evaluationDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return toDTO(resultRepo.save(result));
    }

    @Override
    @Transactional
    public AssessmentResultResponse updateAssessmentResult(Long id, AssessmentResultUpdateRequest request, User currentUser) {
        if (currentUser.getRole() != Role.MENTOR) {
            throw new SecurityException("Chỉ giáo viên hướng dẫn được phép cập nhật đánh giá");
        }

        AssessmentResult result = resultRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả đánh giá"));

        if (!result.getEvaluatedBy().getUserId().equals(currentUser.getUserId())) {
            throw new SecurityException("Bạn không thể chỉnh sửa kết quả đánh giá của người khác");
        }

        result.setScore(request.getScore());
        result.setComments(request.getComments());
        result.setUpdatedAt(LocalDateTime.now());

        return toDTO(resultRepo.save(result));
    }

    private AssessmentResultResponse toDTO(AssessmentResult result) {
        return AssessmentResultResponse.builder()
                .resultId(result.getResultId())
                .score(result.getScore())
                .comments(result.getComments())
                .evaluatedBy(result.getEvaluatedBy().getFullName())
                .evaluationDate(result.getEvaluationDate().toString())
                .assignmentId(result.getAssignment().getAssignmentId())
                .studentCode(result.getAssignment().getStudent().getStudentCode())
                .department(result.getAssignment().getMentor().getDepartment())
                .academicRank(result.getAssignment().getMentor().getAcademicRank())
                .roundId(result.getRound().getRoundId())
                .roundName(result.getRound().getRoundName())
                .criterionId(result.getCriterion().getCriterionId())
                .criterionName(result.getCriterion().getCriterionName())
                .maxScore(result.getCriterion().getMaxScore())
                .build();
    }
}
