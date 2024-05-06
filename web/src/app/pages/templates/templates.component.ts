import { Component } from '@angular/core';
import { TemplateListComponent } from '../../components/template-list/template-list.component';
import { Template } from '../../interfaces/template';
import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { Editor, NgxEditorModule } from 'ngx-editor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-templates',
  standalone: true,
  imports: [TemplateListComponent, TextFieldModule, HttpClientModule, NgxEditorModule, FormsModule, ReactiveFormsModule],
  templateUrl: './templates.component.html',
  styleUrl: './templates.component.scss',
})
export class TemplatesComponent {
  editor: Editor = new Editor();
  html = '';
  selectedLanguage = 'en';
  selectedTemplate: Template | undefined;
  sendedData: Template | undefined;

  ngOnInit() {
    this.selectedTemplate = {
      id: 0,
      name: 'Template 1',
      text: {
        dutch: 'Dutch text',
        english: 'English text',
        spanish: 'Spanish text',
      },
    };
  }

  onDisplay() {
    if (this.selectedLanguage === 'en') this.html = this.selectedTemplate!.text.english;
    else if (this.selectedLanguage === 'es') this.html = this.selectedTemplate!.text.spanish;
    else this.html = this.selectedTemplate!.text.dutch;
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  receiveTemplate(template: Template) {
    this.selectedTemplate = template;
  }

  new() {
    this.selectedTemplate = {
      id: 0,
      name: 'new template',
      text: {
        dutch: '',
        english: '',
        spanish: '',
      },
    };
  }
}
