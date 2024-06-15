package nl.codefusion.comsat.controller;

import lombok.RequiredArgsConstructor;
import nl.codefusion.comsat.config.Permission;
import nl.codefusion.comsat.dao.TemplateDao;
import nl.codefusion.comsat.dto.TemplateDto;
import nl.codefusion.comsat.models.TemplateModel;
import nl.codefusion.comsat.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateDao templateDao;
    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<TemplateModel>> getAllTemplates() throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.READ_TEMPLATE)) {
        return ResponseEntity.ok(templateDao.getAll());
        }
        throw new NoPermissionException();
    }

    @PostMapping
    public ResponseEntity updateTemplate(@Validated @RequestBody TemplateDto templateDto) throws NoPermissionException {
        if (permissionService.hasPermission(permissionService.getPrincipalRoles(), Permission.UPDATE_TEMPLATE)) {
            TemplateModel templateModel = new TemplateModel();
            templateModel.setPlatform(templateDto.getPlatform());
            templateModel.setHeader(templateDto.getHeader());
            templateModel.setBody(templateDto.getBody());
            templateModel.setMetadata(templateDto.getMetadata());
            templateDao.create(templateModel);
            return ResponseEntity.ok().body(templateDao.getById(templateModel.getId()));
        }
        throw new NoPermissionException();
    }


}
