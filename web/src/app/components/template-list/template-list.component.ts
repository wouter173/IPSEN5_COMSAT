import { Component, EventEmitter, Input, Output, OnChanges } from '@angular/core';
import { TemplateListItemComponent } from '../template-list-item/template-list-item.component';
import { Template } from '../../interfaces/template';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-template-list',
  standalone: true,
  imports: [TemplateListItemComponent, CommonModule],
  templateUrl: './template-list.component.html',
})
export class TemplateListComponent {
  @Output() templateEmitter = new EventEmitter<Template>();
  @Input() newStoredTemplate: Template | undefined;

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

  receiveTemplate(template: Template) {
    this.templateEmitter.emit(template);
  }
}
