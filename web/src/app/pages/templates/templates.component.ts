import { Component } from '@angular/core';
import { Template } from '../../interfaces/template';
import { TextFieldModule } from '@angular/cdk/text-field';
import { HttpClientModule } from '@angular/common/http';
import { Editor, NgxEditorModule } from 'ngx-editor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { TemplateListItemComponent } from '../../components/template-list-item/template-list-item.component';
import { CommonModule } from '@angular/common';

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
  html = '';
  htmlTitle = '';
  selectedLanguage = 'en';
  selectedTemplate: Template | undefined;
  sendedData: Template | undefined;

  ngOnInit() {
    this.selectedTemplate = this.templates[0];
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

  onSave() {
    if (this.selectedTemplate) {
      if (this.selectedLanguage === 'en') this.selectedTemplate.text.english = this.html;
      else if (this.selectedLanguage === 'es') this.selectedTemplate.text.spanish = this.html;
      else this.selectedTemplate.text.dutch = this.html;
      this.selectedTemplate.name = this.htmlTitle;
    }
    for (let i = 0; i < this.templates.length; i++) {
      if (this.templates[i].id === this.selectedTemplate!.id) {
        this.templates[i] = this.selectedTemplate!;
        break;
      }
    }
  }

  new() {
    this.selectedTemplate = {
      id: this.templates.length + 1,
      name: 'Template ' + (this.templates.length + 1).toString(),
      text: {
        dutch:
          'Dit is sjabloon ' +
          (this.templates.length + 1).toString() +
          ' \n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template ' +
          (this.templates.length + 1).toString() +
          '\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla ' +
          (this.templates.length + 1).toString() +
          '\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    };
    this.templates.push(this.selectedTemplate);
    this.onDisplay();
  }

  templates: Template[] = [
    {
      id: 1,
      name: 'Template 1',
      text: {
        dutch:
          'Dit is sjabloon 1\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template 1\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla 1\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    },
    {
      id: 2,
      name: 'Template 2',
      text: {
        dutch:
          'Dit is sjabloon 2\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template 2\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla 2\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    },
    {
      id: 3,
      name: 'Template 3',
      text: {
        dutch:
          'Dit is sjabloon 3\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template 3\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla 3\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    },
    {
      id: 4,
      name: 'Template 4',
      text: {
        dutch:
          'Dit is sjabloon 4\n\n Het is heel belangrijk dat de klant geduld heeft en het coachingproces volgt, maar dit is het moment waarop hij met veel werk en pijn te maken krijgt. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een tomatenrecept. \nDe pijn zelf is heel belangrijk, deze wordt door de arts gevolgd, maar het gebeurt op zon moment dat er veel werk en pijn is. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. In feite zijn er veel problemen en veel problemen. Of de elitaire selecrisque mauris pellentesque pulvinus pellentesque bewoners. Voor wat salade en een hete saus.',
        english:
          'This is template 4\n\n It is very important for the customer to be patient, to follow the coaching process, but this is the time that they have to deal with a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a tomato recipe. \n The pain itself is very important, it will be followed by the doctor, but it happens at such a time that there is a lot of work and pain. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. In fact, there is a lot of trouble and a lot of trouble. Or the elit selecrisque mauris pellentesque pulvinus pellentesque dwellers. For some salad and a hot sauce.',
        spanish:
          'Esta es la plantilla 4\n\n Es muy importante que el cliente sea paciente y siga el proceso de coaching, pero este es el momento en el que tiene que lidiar con mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para una receta de ensalada y tomate. \n El dolor en sí es muy importante, será seguido por el médico, pero sucede en un momento en el que hay mucho trabajo y dolor. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. De hecho, hay muchos problemas y muchos problemas. O los habitantes de elit selecrisque mauris pellentesque pulvinus pellentesque. Para un poco de ensalada y salsa picante.',
      },
    },
  ];
}
