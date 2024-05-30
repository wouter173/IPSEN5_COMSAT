package nl.codefusion.comsat.dao;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.models.TemplateModel;
import nl.codefusion.comsat.repository.TemplateRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateDao {
    private final TemplateRepository templateRepository;

    public TemplateModel create(TemplateModel templateModel) {
        return templateRepository.save(templateModel);
    }

    public List<TemplateModel> getAllTemplates() {
        return templateRepository.findAll();
    }
}
