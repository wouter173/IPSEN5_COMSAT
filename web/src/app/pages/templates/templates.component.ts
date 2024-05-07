import { Component } from '@angular/core';
import { TemplateListComponent } from '../../components/template-list/template-list.component';
import { Template } from '../../interfaces/template';
import {TextFieldModule} from '@angular/cdk/text-field'; 

@Component({
  selector: 'app-templates',
  standalone: true,
  imports: [TemplateListComponent, TextFieldModule],
  templateUrl: './templates.component.html',
  styleUrl: './templates.component.scss'
})
export class TemplatesComponent {
  selectedTemplate: Template | undefined;
  sendedData: Template | undefined;

  ngOnInit() {
    this.selectedTemplate = {
      id: 0,
      name: 'Template 1',
      text: 'This is template 1\n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.\n Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Nam aliquam sem et tortor consequat.'
    
    }
  }
  
  receiveTemplate(template: Template) {
    this.selectedTemplate = template;
  }

  new() {
    this.selectedTemplate = {
      id: 0,
      name: 'new template',
      text: ''
    }
  }

}
