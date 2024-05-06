import { Component } from '@angular/core';
import { TemplateListComponent } from '../../components/template-list/template-list.component';
import { Template } from '../../interfaces/template';
import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { Editor, NgxEditorModule } from 'ngx-editor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-templates',
  standalone: true,
  imports: [TemplateListComponent, TextFieldModule, HttpClientModule, NgxEditorModule, FormsModule, ReactiveFormsModule, MatSelectModule],
  templateUrl: './templates.component.html',
  styleUrl: './templates.component.scss',
})
export class TemplatesComponent {
  editor: Editor = new Editor();
  html = '';
  htmlTitle = '';
  selectedLanguage = 'en';
  selectedTemplate: Template | undefined;
  sendedData: Template | undefined;

  ngOnInit() {
    this.selectedTemplate = {
      id: 0,
      name: 'Template 1',
      text: {
        dutch:
          'Dit is sjabloon 1\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template 1\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla 1\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    };
    this.onDisplay();
  }

  onDisplay() {
    this.htmlTitle = this.selectedTemplate!.name;
    if (this.selectedLanguage === 'en') this.html = this.selectedTemplate!.text.english;
    else if (this.selectedLanguage === 'es') this.html = this.selectedTemplate!.text.spanish;
    else this.html = this.selectedTemplate!.text.dutch;
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  receiveTemplate(template: Template) {
    this.selectedTemplate = template;
    this.onDisplay();
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
    this.onDisplay();
  }
}
