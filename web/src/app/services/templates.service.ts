import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';
import { Template, templateSchema } from '../models/templates';

@Injectable({
  providedIn: 'root',
})
export class TemplatesService {
  api = inject(ApiService);

  constructor() {}

  public async getTemplates() {
    const { data } = await this.api.get('/templates', { schema: z.array(templateSchema) });

    return data;
  }

  public async updateTemplate(template: Template) {
    template = {
      id: template.id,
      platform: template.platform,
      header: template.header,
      body: JSON.stringify(template.translations),
      metadata: template.metadata,
      updatedAt: template.updatedAt,
      createdAt: template.createdAt,
    };

    await this.api.post(`/templates`, { body: template });
  }
}
