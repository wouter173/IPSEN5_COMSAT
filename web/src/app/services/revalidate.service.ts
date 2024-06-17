import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RevalidateService {
  public revalidator = new EventEmitter();

  constructor() {}

  revalidate() {
    this.revalidator.emit();
  }
}
