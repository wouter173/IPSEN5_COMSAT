import { Injectable, signal, effect, computed } from '@angular/core';
import { Batch } from '../models/batch';
import {HttpClient} from "@angular/common/http";

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

  public sendBatchData(batch: Batch) {
    const url = 'http://localhost:8080/batch';
    return this.http.post(url, batch);
  }

  constructor(private http: HttpClient) {
    effect(() => {
      localStorage.setItem('batches', JSON.stringify(this._batches()));
    });
  }
}
