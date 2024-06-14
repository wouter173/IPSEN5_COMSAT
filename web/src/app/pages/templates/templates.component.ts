import { Component, computed, inject, signal } from '@angular/core';
import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { Editor, NgxEditorModule, schema, Toolbar, ToolbarItem } from 'ngx-editor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { TemplateListItemComponent } from '../../components/template-list-item/template-list-item.component';
import { CommonModule } from '@angular/common';
import { TemplatesService } from '../../services/templates.service';
import { Template } from '../../models/templates';
import { v4 as uuidv4 } from 'uuid';
import { LanguageDialogComponent } from '../../components/language-dialog/language-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { LucideAngularModule } from 'lucide-angular';
import { platforms } from '../../models/platform';
import customSchema from './custom-schem-extension';
import { insertPlaceholder } from './custom-commands';

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
    LucideAngularModule,
  ],
  templateUrl: './templates.component.html',
})
export class TemplatesComponent {
  editor = new Editor({
    schema: customSchema,
  });
  templateService = inject(TemplatesService);
  dialog = inject(MatDialog);

  templateBody = '';
  templateHeader = '';

  selectedLanguage = 'english';

  sendedData: Template | undefined;

  selectedTemplateId = signal<string | null>(null);
  templates = this.templateService.templates;
  selectedTemplate = computed(() => (this.templates() ? this.templates().find((t) => t.id === this.selectedTemplateId()) : undefined));

  platforms = platforms;

  async ngOnInit() {
    this.onDisplay();
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  insertIntoBody(type: 'username' | 'fullname'): void {
    const { schema, view } = this.editor;
    const command = insertPlaceholder(schema.nodes['placeholder'], type);
    command(view.state, view.dispatch, view);
  }

  onTextChanged() {
    this.selectedTemplate()!.translations!.find((t) => t.language === this.selectedLanguage)!.body = this.templateBody;
  }

  onDisplay() {
    this.templateHeader = this.selectedTemplate()!.header;
    const translation = this.selectedTemplate()!.translations!.find((t) => t.language === this.selectedLanguage);
    this.templateBody = translation!.body;
  }

  onSave() {
    if (this.selectedTemplate) {
      this.selectedTemplate()!.header = this.templateHeader;
      this.selectedTemplate()!.lastModified = new Date().toISOString();

      this.templateService.updateTemplate(this.selectedTemplate()!);
    }
  }

  receiveTemplate(template: Template) {
    this.selectedTemplateId.set(template.id);
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
      this.selectedTemplate()!.translations!.push({
        language: result,
        body: '',
      });
      this.selectedLanguage = result;
    });
  }

  onNewTemplate() {
    const id = uuidv4();
    this.templates().push({
      id,
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
      lastModified: new Date().toISOString(),
      createdAt: new Date().toISOString(),
    });
    this.selectedTemplateId.set(id);
    this.onDisplay();
  }
}
