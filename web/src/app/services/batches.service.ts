import { Injectable, signal, effect, computed, inject } from '@angular/core';
import { Batch, batchSchema } from '../models/batch';
import { HttpClient } from '@angular/common/http';
import { v4 as uuidv4 } from 'uuid';
import { catchError, defer, from, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
import { ApiService } from './api.service';
import { z } from 'zod';

@Injectable({
  providedIn: 'root',
})
export class BatchesService {
  private api = inject(ApiService);
  private _batches = signal<Batch[]>([]);

  constructor() {
    this.getAllBatches().subscribe((batches) => {
      this._batches.set(batches.data!);
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

  public updateBatch(batchId: string, batch: Partial<Batch>) {
    this._batches.set(this._batches().map((b) => (b.id === batchId ? { ...b, ...batch } : b)));
  }

  public sendBatchData(batch: Batch) {
    return from(this.api.post('/batches', { body: batch }));
  }

  public getAllBatches() {
    return from(this.api.get('/batches', { schema: z.array(batchSchema) }));
  }
}
