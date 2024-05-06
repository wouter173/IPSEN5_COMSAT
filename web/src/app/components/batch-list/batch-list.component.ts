import { Component, inject, Input, Signal, signal } from '@angular/core';
import { BatchListItemComponent } from '../batch-list-item/batch-list-item.component';
import { LucideAngularModule } from 'lucide-angular';
import { ChipsFilterComponent } from '../chips-filter/chips-filter.component';
import { BatchCreateDialogComponent } from '../batch-create-dialog/batch-create-dialog.component';
import { TestChartComponent } from '../charts/test-chart/test-chart.component';

import { Batch } from '../../models/batch';

@Component({
  selector: 'app-batch-list',
  standalone: true,
  templateUrl: './batch-list.component.html',
  imports: [BatchListItemComponent, LucideAngularModule, ChipsFilterComponent, BatchCreateDialogComponent, TestChartComponent],
})
export class BatchListComponent {
  @Input() batches!: Signal<Batch[]>;
  @Input() onSelectBatch!: (id: string) => void;
  @Input() selectedBatch!: Signal<string | null>;
  @Input() createBatch!: boolean;

  clickItem(id: string) {
    this.onSelectBatch(id);
  }
}
