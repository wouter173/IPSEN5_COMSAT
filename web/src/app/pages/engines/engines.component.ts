import { Component, inject } from '@angular/core';
import { EnginesService } from '../../services/engines.service';

@Component({
  selector: 'app-engines',
  standalone: true,
  imports: [],
  templateUrl: './engines.component.html',
})
export class EnginesComponent {
  public enginesService = inject(EnginesService);
}
