package ra.project_jvw_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_jvw_service.model.dto.request.InternshipPhaseRequest;
import ra.project_jvw_service.model.dto.response.InternshipPhaseResponse;
import ra.project_jvw_service.model.entity.InternshipPhase;
import ra.project_jvw_service.repository.InternshipPhaseRepository;
import ra.project_jvw_service.service.InternshipPhaseService;

import java.util.List;

@Service
public class InternshipPhaseServiceImpl implements InternshipPhaseService {

    @Autowired
    private InternshipPhaseRepository internshipPhaseRepository;

    @Override
    public List<InternshipPhaseResponse> getAllPhases() {
        return internshipPhaseRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public InternshipPhaseResponse getPhaseById(Long id) {
        InternshipPhase internshipPhase = internshipPhaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giai đoạn thực tập với ID: " + id));
        return toDTO(internshipPhase);
    }

    @Override
    public InternshipPhaseResponse createPhase(InternshipPhaseRequest dto) {
        validateDateRange(dto);

        InternshipPhase internshipPhase = InternshipPhase.builder()
                .phaseName(dto.getPhaseName())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        return toDTO(internshipPhaseRepository.save(internshipPhase));
    }

    @Override
    public InternshipPhaseResponse updatePhase(Long id, InternshipPhaseRequest dto) {
        validateDateRange(dto);
        InternshipPhase internshipPhase = internshipPhaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giai đoạn thực tập với ID: " + id));

        internshipPhase.setPhaseName(dto.getPhaseName());
        internshipPhase.setStartDate(dto.getStartDate());
        internshipPhase.setEndDate(dto.getEndDate());
        internshipPhase.setDescription(dto.getDescription());

        return toDTO(internshipPhaseRepository.save(internshipPhase));
    }

    @Override
    public void deletePhase(Long id) {
        internshipPhaseRepository.deleteById(id);
    }

    private InternshipPhaseResponse toDTO(InternshipPhase internshipPhase) {
        InternshipPhaseResponse response = new InternshipPhaseResponse();
        response.setPhaseId(internshipPhase.getPhaseId());
        response.setPhaseName(internshipPhase.getPhaseName());
        response.setDescription(internshipPhase.getDescription());
        response.setStartDate(internshipPhase.getStartDate());
        response.setEndDate(internshipPhase.getEndDate());
        return response;
    }

    private void validateDateRange(InternshipPhaseRequest dto) {
        if (dto.getStartDate() != null && dto.getEndDate() != null &&
                dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
        }
    }

}
