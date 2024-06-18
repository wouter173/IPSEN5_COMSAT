import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EnginesService } from '../../services/engines.service';

@Component({
  selector: 'app-engine-callback',
  standalone: true,
  imports: [],
  templateUrl: './engine-callback.component.html',
})
export class EngineCallbackComponent {
  private activatedRoute = inject(ActivatedRoute);
  private engineService = inject(EnginesService);
  private router = inject(Router);

  constructor() {
    this.onLoad();
  }

  private async onLoad() {
    const token = this.activatedRoute.snapshot.queryParamMap.get('response');
    if (!token) throw new Error('token is required');
    const response = await this.engineService.solveCaptcha(token);
    if (response.ok) {
      this.router.navigate(['/engines']);
    }
  }
}
