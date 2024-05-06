import { Component } from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-chart-gender',
  standalone: true,
  imports: [ChartModule],
  templateUrl: './chart-gender.component.html',
  styleUrl: './chart-gender.component.scss',
})
export class ChartGenderComponent {
  data: any;
  options: any;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    this.data = {
      labels: ['Man', 'Vrouw', 'Non-binair'],
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
