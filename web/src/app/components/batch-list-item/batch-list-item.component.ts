import { Component, Input } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { Batch } from '../../models/batch';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-batch-list-item',
  standalone: true,
  imports: [LucideAngularModule, CommonModule],
  templateUrl: './batch-list-item.component.html',
})
export class BatchListItemComponent {
  @Input() public batch!: Batch;
  @Input() public selected!: boolean;
}
