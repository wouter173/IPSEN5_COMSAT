import { Component, inject, signal } from '@angular/core';
import { BatchListComponent } from '../../components/batch-list/batch-list.component';
import { BatchDetailComponent } from '../../components/batch-detail/batch-detail.component';
import { BatchesService } from '../../services/batches.service';

@Component({
  selector: 'app-batches',
  standalone: true,
  templateUrl: './batches.component.html',
  imports: [BatchListComponent, BatchDetailComponent],
})
export class BatchesComponent {
  public selectedBatch = signal<string | null>(null);
  public batchesService = inject(BatchesService);

  onSelectBatch(id: string) {
    this.selectedBatch.set(id);
  }
}
