import { Component, inject } from '@angular/core';
import { EnginesService } from '../../services/engines.service';
import { RevalidateService } from '../../services/revalidate.service';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-engines',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './engines.component.html',
})
export class EnginesComponent {
  public enginesService = inject(EnginesService);
  private revalidateService = inject(RevalidateService);

  public onRefreshClick() {
    this.revalidateService.revalidate();
  }
}
