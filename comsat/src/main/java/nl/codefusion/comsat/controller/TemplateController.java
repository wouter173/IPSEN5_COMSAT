package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.models.TemplateModel;
import org.slf4j.Logger;
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

        return ResponseEntity.ok(templateDao.getAllTemplates());
    }

    @PutMapping()
    public ResponseEntity updateTemplate(@Validated @RequestBody TemplateModel templateModel) {
        templateDao.updateTemplate(templateModel);
        return ResponseEntity.ok().body(templateDao.getTemplateById(templateModel.getId()));
    }


}
