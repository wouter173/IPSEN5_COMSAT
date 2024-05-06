import { Component, computed, inject, signal } from '@angular/core';
import { BatchCreateDialogComponent } from '../../components/batch-create-dialog/batch-create-dialog.component';
import { BatchListItemComponent } from '../../components/batch-list-item/batch-list-item.component';
import { ChipsFilterComponent } from '../../components/chips-filter/chips-filter.component';
import { BatchListComponent } from '../../components/batch-list/batch-list.component';
import { ChartPlatformComponent } from '../../components/charts/chart-platforms/chart-platform.component';
import { ChartRegionComponent } from '../../components/charts/chart-region/chart-region.component';
import { ChartGenderComponent } from '../../components/charts/chart-gender/chart-gender.component';
import { LucideAngularModule } from 'lucide-angular';
import { BatchesService } from '../../services/batches.service';
import { Batch } from '../../models/batch';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [
    BatchCreateDialogComponent,
    BatchListItemComponent,
    ChipsFilterComponent,
    BatchListComponent,
    ChartPlatformComponent,
    ChartRegionComponent,
    ChartGenderComponent,
    LucideAngularModule,
  ],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css',
})
export class ReportsComponent {
  public batchesService = inject(BatchesService);
  public selectedBatch = signal<string | null>(null);

  public generalBatch: Batch = {
    id: 'general',
    name: 'General',
    contacts: [],
    state: 'SENT',
    createdAt: new Date(),
    lastModified: new Date(),
  };
  public batchesWithGeneral = computed(() => [this.generalBatch, ...this.batchesService.batches()]);

  onSelectBatch(id: string) {
    console.log('selectedBatch', id);
  }
}
