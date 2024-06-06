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
import { v4 as uuidv4 } from 'uuid';
import { LanguageDialogComponent } from '../../components/language-dialog/language-dialog.component';
import { MatDialog } from '@angular/material/dialog';

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

  constructor(public dialog: MatDialog) {}

  async ngOnInit() {
    this.loadTemplates();
  }



  ngOnDestroy() {
    this.editor.destroy();
  }

  async loadTemplates() {
    this.templates = await this.templateService.getTemplates();
    this.selectedTemplate = this.templates[0];
    this.onDisplay();
  }


  onTextChanged() {
    this.selectedTemplate!.translations!.find((t) => t.language === this.selectedLanguage)!.body = this.templateBody;
  }

  onDisplay() {
    this.templateHeader = this.selectedTemplate!.header;
    const translation = this.selectedTemplate!.translations!.find((t) => t.language === this.selectedLanguage);
    this.templateBody = translation!.body;
  }

  onSave() {
    if (this.selectedTemplate) {
      this.selectedTemplate.header = this.templateHeader;
      this.selectedTemplate.updatedAt = new Date().toISOString();
      this.templateService.updateTemplate(this.selectedTemplate);
    }
  }

  receiveTemplate(template: Template) {
    this.selectedTemplate = template;
    this.onDisplay();
  }

  newLanguage() {
    console.log('new language');
    const dialogRef = this.dialog.open(LanguageDialogComponent, {
      data: '',
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result === undefined) {
        this.selectedLanguage = 'english';
        return;
      }
      this.selectedTemplate!.translations!.push({
        language: result,
        body: '',
      });
      this.selectedLanguage = result;
    });
  }

  onNewTemplate() {
    this.selectedTemplate = {
      id: uuidv4(),
      platform: 'kik' as 'snapchat' | 'kik' | 'whatsapp' | 'instagram' | 'telegram',
      header: '',
      body: '',
      translations: [
        {
          language: 'english',
          body: '',
        },
      ],
      metadata: '',
      updatedAt: new Date().toISOString(),
      createdAt: new Date().toISOString(),
    };
    this.onDisplay();
  }
}
