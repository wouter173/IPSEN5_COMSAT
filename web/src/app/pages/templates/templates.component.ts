import { Component, inject } from '@angular/core';
import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { Editor, NgxEditorModule } from 'ngx-editor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { TemplateListItemComponent } from '../../components/template-list-item/template-list-item.component';
import { CommonModule } from '@angular/common';
import { TemplatesService } from '../../services/templates.service';
import { Template } from '../../models/templates';

@Component({
  selector: 'app-templates',
  standalone: true,
  imports: [
    TemplateListItemComponent,
    TextFieldModule,
    HttpClientModule,
    NgxEditorModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    CommonModule,
  ],
  templateUrl: './templates.component.html',
  styleUrl: './templates.component.scss',
})
export class TemplatesComponent {
  editor: Editor = new Editor();
  templateService = inject(TemplatesService);

  templateBody = '';
  templateHeader = '';

  selectedLanguage = 'english';

  sendedData: Template | undefined;
  templates: Template[] = [];
  selectedTemplate: Template | undefined;

  constructor() {}

  async ngOnInit() {
    this.templates = await this.templateService.getTemplates();
    this.selectedTemplate = this.templates[0];
    this.onDisplay();
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  onDisplay() {
    this.templateHeader = this.selectedTemplate!.header;
    const translation = this.selectedTemplate!.translations!.find((t) => t.language === this.selectedLanguage);
    this.templateBody = translation!.body;
  }

  receiveTemplate(template: Template) {
    this.selectedTemplate = template;
    this.onDisplay();
  }

  onSave() {
    if (this.selectedTemplate) {
      this.templateService
        .updateTemplate(this.selectedTemplate)
        .then(() => {
          console.log('Template saved successfully');
        })
        .catch((error) => {
          console.error('Error saving template:', error);
        });
    }
  }

  new() {}
}
