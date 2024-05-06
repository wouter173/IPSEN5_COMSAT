import { Component } from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-test-chart',
  standalone: true,
  imports: [ChartModule],
  templateUrl: './test-chart.component.html',
  styleUrl: './test-chart.component.scss',
})
export class TestChartComponent {
  data: any;
  options: any;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    this.data = {
      labels: ['Facebook', 'WhatsApp', 'Instagram'],
      datasets: [
        {
          data: [540, 325, 302],
          backgroundColor: ['#F4A090', '#4B5E84', '#b95d4c'],
        },
      ],
    };

    this.options = {
      plugins: {
        legend: {
          labels: {
            usePointStyle: true,
            color: textColor,
          },
        },
      },
    };
  }
}
