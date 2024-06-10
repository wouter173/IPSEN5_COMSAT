import { Injectable, inject, signal } from '@angular/core';
import { ApiService } from './api.service';
import { z } from 'zod';
import { Template, templateSchema } from '../models/templates';

@Injectable({
  providedIn: 'root',
})
export class TemplatesService {
  private _templates = signal<Template[]>([]);
  public templates = this._templates.asReadonly();

  private api = inject(ApiService);

  constructor() {
    this.fetchTemplates();
  }

  public async fetchTemplates() {
    const { data } = await this.api.get('/templates', { schema: z.array(templateSchema) });
    const templates = data.map((template) => ({ ...template, translations: JSON.parse(template.body) }));

    this._templates.set(templates);
  }

  public async updateTemplate(template: Template) {
    template = {
      id: template.id,
      platform: template.platform,
      header: template.header,
      body: JSON.stringify(template.translations),
      metadata: template.metadata,
      lastModified: template.lastModified,
      createdAt: template.createdAt,
    };

    await this.api.post(`/templates`, { body: template });
    await this.fetchTemplates();
  }
}
