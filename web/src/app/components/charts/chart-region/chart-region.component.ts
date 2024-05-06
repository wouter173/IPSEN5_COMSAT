import { Component } from '@angular/core';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-chart-region',
  standalone: true,
  imports: [ChartModule],
  templateUrl: './chart-region.component.html',
  styleUrl: './chart-region.component.scss',
})
export class ChartRegionComponent {
  data: any;
  options: any;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    this.data = {
      labels: ['Friesland', 'Groningen', 'Drenthe', 'Overijssel', 'Flevoland', 'Gelderland', 'Utrecht', 'Noord-Holland', 'Zuid-Holland', 'Zeeland', 'Noord-Brabant', 'Limburg'],
      datasets: [
        {
          data: [540, 325, 302, 302, 302, 302, 302, 302, 302, 302, 302, 302],
          backgroundColor: ['#F4A090', '#4B5E84', '#b95d4c',  '#F4A090', '#4B5E84', '#b95d4c', '#F4A090', '#4B5E84', '#b95d4c', '#F4A090', '#4B5E84', '#b95d4c'],
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
