import { computed, inject, Injectable, signal } from '@angular/core';
import { from, tap } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';
import { z } from 'zod';
import { Batch, batchSchema } from '../models/batch';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class BatchesService {
  private api = inject(ApiService);
  private _batches = signal<Batch[]>([]);

  constructor() {
    this.getAllBatches().subscribe();
  }

  public batches = computed(() => {
    return this._batches().sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
  });

  public createBatch(batch: Batch) {
    if (batch.contacts) {
      batch.contacts = batch.contacts.map((contact) => ({ ...contact, id: uuidv4() }));
    }

    this._batches.set([...this._batches(), batch]);
  }

  public hideBatchEntry(batchId: string, contactId: string, hide: boolean) {
    this.api.put(`/batches/${batchId}/contacts/${contactId}`, { body: { hidden: hide } });

    this._batches.set(
      this._batches().map((b) => {
        if (b.id === batchId) {
          b.contacts = b.contacts.map((c) => (c.id === contactId ? { ...c, hidden: hide } : c));
        }

        return b;
      }),
    );
  }

  public sendBatchData(batch: Batch) {
    return from(this.api.post('/batches', { body: batch }));
  }

  public getAllBatches() {
    return from(this.api.get('/batches', { schema: z.array(batchSchema) })).pipe(tap(({ data }) => this._batches.set(data)));
  }

  public updateBatch(id: string, batch: Partial<{ state: string; name: string; templates: string[] }>) {
    return from(this.api.put('/batches/' + id, { body: { name: batch.name, templates: batch.templates }, schema: batchSchema })).pipe(
      tap((batch) => this._batches.set([...this._batches().filter((x) => x.id != batch.data.id), batch.data])),
    );
  }
}
