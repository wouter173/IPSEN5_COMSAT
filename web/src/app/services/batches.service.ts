import { Injectable, signal, effect, computed } from '@angular/core';
import { Batch } from '../models/batch';
import {HttpClient} from "@angular/common/http";
import { v4 as uuidv4 } from 'uuid';
import {catchError, tap, throwError} from "rxjs";

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

    if (batch.contacts) {
      batch.contacts = batch.contacts.map(contact => ({ ...contact, id: uuidv4() }));
    }
    this._batches.set([...this._batches(), batch]);
  }

  public updateBatch(batchId: string, batch: Partial<Batch>) {
    this._batches.set(this._batches().map((b) => (b.id === batchId ? { ...b, ...batch } : b)));
  }

public sendBatchData(batch: Batch) {
  const url = 'http://localhost:8080/batch';
  return this.http.post(url, batch).pipe(
    tap(() => {
      // Update the batch status to 'SENT' after the POST request has been made
      this.updateBatch(batch.id, { state: 'SENT' });
    }),
    catchError((error) => {
      // Update the batch status to 'ERROR' if the POST request fails
      return throwError(error);
    })
  );
}

  getAllBatches() {
    return this.http.get<Batch[]>("http://localhost:8080/batches");
  }

  public wipeAllBatches() {
    this._batches.set([]);
    localStorage.setItem('batches', JSON.stringify(this._batches()));
  }

  constructor(private http: HttpClient) {
    this.wipeAllBatches()
    this.getAllBatches().subscribe((batches) => { this._batches.set(batches) } );
  }


}
