import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { Template } from '../../models/templates';

@Component({
  selector: 'app-template-list-item',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './template-list-item.component.html',
})
export class TemplateListItemComponent {
  @Input() template!: Template;
  @Output() templateEmitter = new EventEmitter<Template>();
  displayCurrentTemplate(template: Template) {
    this.templateEmitter.emit(template);
  }
}
