package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.EvaluationCriteriaRequest;
import ra.project_jvw_service.model.dto.response.EvaluationCriteriaResponse;
import ra.project_jvw_service.model.entity.EvaluationCriterion;
import ra.project_jvw_service.repository.EvaluationCriteriaRepository;
import ra.project_jvw_service.service.EvaluationCriteriaService;

import java.util.List;

@Service
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    @Autowired
    private EvaluationCriteriaRepository evaluationCriteriaRepository;


    @Override
    public List<EvaluationCriteriaResponse> getAllCriteria() {
        return evaluationCriteriaRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    @Override
    public EvaluationCriteriaResponse getCriterionById(Long id) {
        EvaluationCriterion evaluationCriterion = evaluationCriteriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + id));
        return toDTO(evaluationCriterion);
    }

    @Override
    public EvaluationCriteriaResponse createCriterion(EvaluationCriteriaRequest dto) {
        if (evaluationCriteriaRepository.existsByCriterionNameIgnoreCase(dto.getCriterionName())) {
            throw new IllegalArgumentException("Tiêu chí đánh giá với tên '" + dto.getCriterionName() + "' đã tồn tại.");
        }

        EvaluationCriterion evaluationCriterion = EvaluationCriterion.builder()
            .criterionName(dto.getCriterionName())
            .description(dto.getDescription())
            .maxScore(dto.getMaxScore())
            .build();

        return toDTO(evaluationCriteriaRepository.save(evaluationCriterion));
    }

    @Override
    public EvaluationCriteriaResponse updateCriterion(Long id, EvaluationCriteriaRequest dto) {
        EvaluationCriterion evaluationCriterion = evaluationCriteriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tiêu chí đánh giá với ID: " + id));

        if (evaluationCriteriaRepository.existsByCriterionNameIgnoreCase(dto.getCriterionName())) {
            throw new IllegalArgumentException("Tiêu chí đánh giá với tên '" + dto.getCriterionName() + "' đã tồn tại.");
        }

        evaluationCriterion.setCriterionName(dto.getCriterionName());
        evaluationCriterion.setDescription(dto.getDescription());
        evaluationCriterion.setMaxScore(dto.getMaxScore());

        return toDTO(evaluationCriteriaRepository.save(evaluationCriterion));
    }

    @Override
    public void deleteCriterion(Long id) {
        evaluationCriteriaRepository.deleteById(id);
    }

    private EvaluationCriteriaResponse toDTO(EvaluationCriterion evaluationCriteria) {
        return EvaluationCriteriaResponse.builder()
            .criterionId(evaluationCriteria.getCriterionId())
            .criterionName(evaluationCriteria.getCriterionName())
            .description(evaluationCriteria.getDescription())
            .maxScore(evaluationCriteria.getMaxScore())
            .build();
    }
}
