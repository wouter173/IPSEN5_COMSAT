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
      text:'This is template 1\n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.',

    },
    {
      id: 2,
      name: 'Template 2',
      text: 'This is template 2\n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.',

    },
    {
      id: 3,
      name: 'Template 3',
      text: 'This is template 3\n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.',

    },
    {
      id: 4,
      name: 'Template 4',
      text: 'This is template 4\n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.',

    }
  ]

  receiveTemplate(template: Template) {
    this.templateEmitter.emit(template);
  }
}
