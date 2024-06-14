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
  providers: [ ChipsFilterComponent]
})
export class BatchListComponent {
  filterChips = ['snapchat', 'kik', 'whatsapp', 'instagram', 'telegram'];
  chipsFilter = inject(ChipsFilterComponent);
  @Input() batches!: Signal<Batch[]>;
  @Input() onSelectBatch!: (id: string) => void;
  @Input() selectedBatch!: Signal<string | null>;
  @Input() createBatch!: boolean;

  filters = signal<string[]>([]);

  filteredBatches: Signal<Batch[]> = computed(() => {
    return this.batches()
    .filter((batch) => {
      return this.filters().every((filter) => {
        return this.filters().length === 0 || batch.contacts.some((contact) => {
          return this.filters().includes(contact.platform);
        });
      });
    });
  });

  clickItem(id: string) {
    this.onSelectBatch(id);
  }

  addFilter(value: string) {
    console.log(value);
    this.filters.set([...this.filters(), value]);
  }

  removeFilter(value: string) {
    console.log(value);
    this.filters.set(this.filters().filter((filter) => filter !== value));
  }
}
