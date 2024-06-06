package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.dto.TemplateDto;
import nl.codefusion.comsat.models.TemplateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateDao templateDao;

    @GetMapping
    public ResponseEntity<List<TemplateModel>> getAllTemplates() {

        return ResponseEntity.ok(templateDao.getAll());
    }

    @PostMapping
    public ResponseEntity updateTemplate(@Validated @RequestBody TemplateDto templateDto) {
        TemplateModel templateModel = new TemplateModel();
        templateModel.setId(templateDto.getId());
        templateModel.setPlatform(templateDto.getPlatform());
        templateModel.setHeader(templateDto.getHeader());
        templateModel.setBody(templateDto.getBody());
        templateModel.setMetadata(templateDto.getMetadata());
        templateDao.update(templateDto.getId(), templateModel);
        return ResponseEntity.ok().body(templateDao.getById(templateModel.getId()));
    }


}
