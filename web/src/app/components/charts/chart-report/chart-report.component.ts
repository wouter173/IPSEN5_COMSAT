import {Component, Input} from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-chart-report',
  standalone: true,
  imports: [ChartModule],
  templateUrl: './chart-report.component.html',
  styleUrl: './chart-report.component.scss',
})
export class ChartReportComponent {
  @Input() data: any;
  options: any;


}
