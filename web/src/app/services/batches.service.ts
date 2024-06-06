import { computed, inject, Injectable, signal } from '@angular/core';
import { from } from 'rxjs';
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
    this.getAllBatches().subscribe((batches) => {
      this._batches.set(batches.data);
    });
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

  public updateBatch(batchId: string, batch: Partial<Batch>) {
    this._batches.set(this._batches().map((b) => (b.id === batchId ? { ...b, ...batch } : b)));
  }

  public sendBatchData(batch: Batch) {
    return from(this.api.post('/batches', { body: batch }));
  }

  public getAllBatches() {
    return from(this.api.get('/batches', { schema: z.array(batchSchema) }));
  }

  public updateBatchName(id: string, value: string) {
    console.log('Updating batch name to:', value);
    return from(this.api.put('/batches/' + id, { body: { name: value } }));
  }
}
