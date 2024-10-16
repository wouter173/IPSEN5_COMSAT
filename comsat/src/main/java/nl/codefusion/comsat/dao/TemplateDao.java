package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.TemplateModel;
import nl.codefusion.comsat.repository.TemplateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TemplateDao {
    private final TemplateRepository templateRepository;

    public TemplateModel create(TemplateModel templateModel) {
        return templateRepository.save(templateModel);
    }

    public List<TemplateModel> getAll() {
        return templateRepository.findAll();
    }

    public void update(UUID id, TemplateModel template) {
        Optional<TemplateModel> existingTemplateOpt = templateRepository.findById(template.getId());
        if (existingTemplateOpt.isPresent()) {
            TemplateModel existingTemplate = existingTemplateOpt.get();
            existingTemplate.setPlatform(template.getPlatform());
            existingTemplate.setHeader(template.getHeader());
            existingTemplate.setBody(template.getBody());
            existingTemplate.setMetadata(template.getMetadata());
            templateRepository.save(existingTemplate);
        } else {
            templateRepository.save(template);
        }
    }

    public TemplateModel getById(UUID id) {
        Optional<TemplateModel> template = templateRepository.findById(id);
        return template.orElse(null);
    }
}
