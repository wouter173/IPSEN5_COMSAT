package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.models.TemplateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
