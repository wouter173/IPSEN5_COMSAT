import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {BatchCreateDialogComponent} from '../../components/batch-create-dialog/batch-create-dialog.component';
import {BatchListItemComponent} from '../../components/batch-list-item/batch-list-item.component';
import {ChipsFilterComponent} from '../../components/chips-filter/chips-filter.component';
import {BatchListComponent} from '../../components/batch-list/batch-list.component';
import {LucideAngularModule} from 'lucide-angular';
import {BatchesService} from '../../services/batches.service';
import {Batch} from '../../models/batch';
import {ChartReportComponent} from "../../components/charts/chart-report/chart-report.component";
import {ChartService} from "../../services/chart.service";

@Component({
    selector: 'app-reports',
    standalone: true,
    imports: [
        BatchCreateDialogComponent,
        BatchListItemComponent,
        ChipsFilterComponent,
        BatchListComponent,
        ChartReportComponent,
        LucideAngularModule,
    ],
    templateUrl: './reports.component.html',
})
export class ReportsComponent implements OnInit {
    public batchesService = inject(BatchesService);
    public selectedBatch = signal<string | null>(null);
    platformData: any;
    regionData: any;
    genderData: any;


    constructor(private chartService: ChartService) {
    }

    onSelectBatch(id: string) {
        console.log('selectedBatch', id);
        this.selectedBatch.set(id);

        this.chartService.getGenderData(id).subscribe((data) => {
            this.genderData = this.transformData(data);
        });

        this.chartService.getPlatformData(id).subscribe((data) => {
            this.platformData = this.transformData(data);
        });

        this.chartService.getRegionData(id).subscribe((data) => {
            this.regionData = this.transformData(data);
        });
    }

    ngOnInit(): void {
        this.chartService.getGenderData().subscribe((data) => {
            this.genderData = this.transformData(data);
        });

        this.chartService.getPlatformData().subscribe((data) => {
            this.platformData = this.transformData(data);
        });

        this.chartService.getRegionData().subscribe((data) => {
            this.regionData = this.transformData(data);
        });
    }

    transformData(response: any[]): any {
        const labels = response.map(item => item[0]);
        const data = response.map(item => item[1]);

        return {
            labels: labels,
            datasets: [
                {
                    data: data,
                    backgroundColor: [
                        '#FF6384',
                        '#36A2EB',
                        '#FFCE56',
                    ],
                }
            ]
        };
    }

    public generalBatch: Batch = {
        id: 'general',
        name: 'General',
        contacts: [],
        state: 'SENT',
        createdAt: new Date(),
        lastModified: new Date(),
    };
    public batchesWithGeneral = computed(() => [this.generalBatch, ...this.batchesService.batches()]);
}
