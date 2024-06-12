import { Component, computed, inject, Input, Signal, signal } from '@angular/core';
import { BatchListItemComponent } from '../batch-list-item/batch-list-item.component';
import { LucideAngularModule } from 'lucide-angular';
import { ChipsFilterComponent } from '../chips-filter/chips-filter.component';
import { BatchCreateDialogComponent } from '../batch-create-dialog/batch-create-dialog.component';
import { Batch } from '../../models/batch';

@Component({
  selector: 'app-batch-list',
  standalone: true,
  templateUrl: './batch-list.component.html',
  imports: [BatchListItemComponent, LucideAngularModule, ChipsFilterComponent, BatchCreateDialogComponent],
})
export class BatchListComponent {
  chipsFilter = inject(ChipsFilterComponent);
  @Input() batches!: Signal<Batch[]>;
  @Input() onSelectBatch!: (id: string) => void;
  @Input() selectedBatch!: Signal<string | null>;
  @Input() createBatch!: boolean;

  filters: string[] = [];
  filteredBatches: Signal<Batch[]> = computed(() => {
    return this.batches().filter((batch) => this.filters.length === 0 || batch.contacts.some((contact) => this.filters.includes(contact.platform) || this.filters.includes(contact.status)));
  });

  clickItem(id: string) {
    this.onSelectBatch(id);
  }
}
