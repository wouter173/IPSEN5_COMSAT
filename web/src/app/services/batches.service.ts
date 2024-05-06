import { Injectable, signal, effect, computed } from '@angular/core';
import { Batch } from '../models/batch';

@Injectable({
  providedIn: 'root',
})
export class BatchesService {
  private _batches = signal<Batch[]>(JSON.parse(localStorage.getItem('batches')!) ?? []);
  public batches = computed(() => {
    this._batches().map(console.log);
    return this._batches().sort((a, b) => new Date(b.lastModified).getTime() - new Date(a.lastModified).getTime());
  });

  public createBatch(batch: Batch) {
    this._batches.set([...this._batches(), batch]);
  }

  public updateBatch(batchId: string, batch: Partial<Batch>) {
    this._batches.set(this._batches().map((b) => (b.id === batchId ? { ...b, ...batch } : b)));
  }

  constructor() {
    effect(() => {
      localStorage.setItem('batches', JSON.stringify(this._batches()));
    });
  }
}
