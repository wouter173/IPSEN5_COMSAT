import { Injectable, signal, effect, computed } from '@angular/core';
import { Batch } from '../models/batch';
import {HttpClient} from "@angular/common/http";
import { v4 as uuidv4 } from 'uuid';
import {catchError, tap, throwError} from "rxjs";
import {environment} from "../../environments/environment";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root',
})
export class BatchesService {

  constructor(private http: HttpClient, private authService: AuthService) {
    this.wipeAllBatches()
    this.getAllBatches().subscribe((batches) => { this._batches.set(batches) } );
  }

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
  const url = environment.apiUrl + '/api/v1/batch';
  return this.http.post(url, batch, {headers: {
      "Authorization": "Bearer " + this.authService.getToken()
    }}).pipe(
    tap(() => {
      this.updateBatch( batch.id, { state: 'SENT' });
    }),
    catchError((error) => {
      return throwError(error);
    })
  );
}

  getAllBatches() {
    const url = environment.apiUrl + '/api/v1/batches';
    return this.http.get<Batch[]>(url, {headers: {
       "Authorization": "Bearer " + this.authService.getToken(),
      }})
  }

  public wipeAllBatches() {
    this._batches.set([]);
    localStorage.setItem('batches', JSON.stringify(this._batches()));
  }
}
