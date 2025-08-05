package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.RoundCriterionRequest;
import ra.project_jvw_service.model.dto.response.RoundCriterionResponse;
import ra.project_jvw_service.model.entity.AssessmentRound;
import ra.project_jvw_service.model.entity.EvaluationCriterion;
import ra.project_jvw_service.model.entity.RoundCriterion;
import ra.project_jvw_service.repository.AssessmentRoundRepository;
import ra.project_jvw_service.repository.EvaluationCriteriaRepository;
import ra.project_jvw_service.repository.RoundCriterionRepository;
import ra.project_jvw_service.service.RoundCriterionService;

import java.util.List;

@Service
public class RoundCriterionServiceImpl implements RoundCriterionService {

    @Autowired
    private RoundCriterionRepository repository;

    @Autowired
    private AssessmentRoundRepository roundRepository;

    @Autowired
    private EvaluationCriteriaRepository criterionRepository;

    @Override
    public List<RoundCriterionResponse> getAllRoundCriterion(Integer roundId) {
        return repository.findAllByRound_RoundId(roundId).stream().map(this::toDTO).toList();
    }

    @Override
    public RoundCriterionResponse getByIdRoundCriterion(Integer id) {
        RoundCriterion roundCriterion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + id));
        return toDTO(roundCriterion);
    }

    @Override
    public RoundCriterionResponse createRoundCriterion(RoundCriterionRequest dto) {
        AssessmentRound round = roundRepository.findById(Long.valueOf(dto.getRoundId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt đánh giá với ID: " + dto.getRoundId()));
        EvaluationCriterion criterion = criterionRepository.findById(Long.valueOf(dto.getCriterionId()))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + dto.getCriterionId()));

        RoundCriterion rc = RoundCriterion.builder()
                .round(round)
                .criterion(criterion)
                .weight(dto.getWeight())
                .build();
        return toDTO(repository.save(rc));
    }

    @Override
    public RoundCriterionResponse updateRoundCriterion(Integer id, RoundCriterionRequest dto) {
        RoundCriterion roundCriterion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + id));

//        AssessmentRound round = roundRepository.findById(Long.valueOf(dto.getRoundId()))
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy đợt đánh giá với ID: " + dto.getRoundId()));
//        EvaluationCriterion criterion = criterionRepository.findById(Long.valueOf(dto.getCriterionId()))
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + dto.getCriterionId()));
//
//        roundCriterion.setRound(round);
//        roundCriterion.setCriterion(criterion);
        roundCriterion.setWeight(dto.getWeight());

        return toDTO(repository.save(roundCriterion));
    }

    @Override
    public void deleteRoundCriterion(Integer id) {
        repository.deleteById(id);
    }

    private RoundCriterionResponse toDTO(RoundCriterion roundCriterion) {
        return RoundCriterionResponse.builder()
                .roundCriterionId(roundCriterion.getRoundCriterionId())
                .roundId(roundCriterion.getRound().getRoundId())
                .roundName(roundCriterion.getRound().getRoundName())
                .startDate(roundCriterion.getRound().getStartDate())
                .endDate(roundCriterion.getRound().getEndDate())
                .criterionId(roundCriterion.getCriterion().getCriterionId())
                .criterionName(roundCriterion.getCriterion().getCriterionName())
                .maxScore(roundCriterion.getCriterion().getMaxScore())
                .weight(roundCriterion.getWeight())
                .build();
    }
}
