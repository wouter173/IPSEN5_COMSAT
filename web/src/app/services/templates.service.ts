import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';

@Injectable({
  providedIn: 'root'
})
export class TemplatesService {
  api = inject(ApiService)

  constructor() { }

  public async getTemplates() {
    const results = await this.api.get('/api/v1/templates');
    return z.object({ templates: z.array(z.object({ id: z.string(), name: z.string() })) }).parse(await results.json());
  }
}
