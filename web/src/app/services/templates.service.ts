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
    console.log(data);
    return data;
  }

  public async updateTemplate(template: Template) {
    template.body = JSON.stringify(template.translations);
    console.log(template.body);
    template.translations = undefined;
    await this.api.put(`/templates/${template.id}`, {
      body: template,
    });
  }
}
