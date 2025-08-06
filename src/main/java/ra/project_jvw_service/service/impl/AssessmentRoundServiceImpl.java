package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.AssessmentRoundRequest;
import ra.project_jvw_service.model.dto.response.AssessmentRoundResponse;
import ra.project_jvw_service.model.entity.AssessmentRound;
import ra.project_jvw_service.model.entity.InternshipPhase;
import ra.project_jvw_service.repository.AssessmentRoundRepository;
import ra.project_jvw_service.repository.InternshipPhaseRepository;
import ra.project_jvw_service.service.AssessmentRoundService;

import java.util.List;

@Service
public class AssessmentRoundServiceImpl implements AssessmentRoundService {
    @Autowired
    private AssessmentRoundRepository assessmentRoundRepository;

    @Autowired
    private InternshipPhaseRepository internshipPhaseRepository;

    @Override
    public List<AssessmentRoundResponse> getAll(Long phaseId) {
        List<AssessmentRound> list = (phaseId != null) ? assessmentRoundRepository.findAllByPhase_PhaseId(phaseId) : assessmentRoundRepository.findAll();
        return list.stream().map(this::toDTO).toList();
    }

    @Override
    public AssessmentRoundResponse getById(Long id) {
        AssessmentRound assessmentRound = assessmentRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vòng đánh giá với ID: " + id));
        return toDTO(assessmentRound);
    }

    @Override
    public AssessmentRoundResponse create(AssessmentRoundRequest dto) {
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
        }

        AssessmentRound assessmentRound = AssessmentRound.builder()
                .roundName(dto.getRoundName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .isActive(true)
                .phase(internshipPhaseRepository.findById(Long.valueOf(dto.getPhaseId()))
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy giai đoạn thực tập với ID: " + dto.getPhaseId())))
                .build();

        return toDTO(assessmentRoundRepository.save(assessmentRound));
    }

    @Override
    public AssessmentRoundResponse update(Long id, AssessmentRoundRequest dto) {
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
        }
        AssessmentRound assessmentRound = assessmentRoundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vòng đánh giá với ID: " + id));

        InternshipPhase phase = internshipPhaseRepository.findById(Long.valueOf(dto.getPhaseId())).orElseThrow(
                () -> new RuntimeException("Không tìm thấy giai đoạn thực tập với ID: " + dto.getPhaseId())
        );
        assessmentRound.setPhase(phase);
        assessmentRound.setRoundName(dto.getRoundName());
        assessmentRound.setStartDate(dto.getStartDate());
        assessmentRound.setEndDate(dto.getEndDate());
        assessmentRound.setDescription(dto.getDescription());
        return toDTO(assessmentRoundRepository.save(assessmentRound));

    }

    @Override
    public void delete(Long id) {
        assessmentRoundRepository.deleteById(id);
    }

    private AssessmentRoundResponse toDTO(AssessmentRound assessmentRound) {
        return AssessmentRoundResponse.builder()
                .roundId(assessmentRound.getRoundId())
                .roundName(assessmentRound.getRoundName())
                .startDate(assessmentRound.getStartDate())
                .endDate(assessmentRound.getEndDate())
                .description(assessmentRound.getDescription())
                .isActive(assessmentRound.getIsActive())
                .phaseId(assessmentRound.getPhase().getPhaseId())
                .phaseName(assessmentRound.getPhase().getPhaseName())
                .build();
    }
}
