import { HttpClient } from '@angular/common/http';
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
    const results = await this.api.get('/templates');
    const data = z.array(templateSchema).parse(await results.json());
    data.forEach((template) => {
      template.translations = JSON.parse(template.body);
    });
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
