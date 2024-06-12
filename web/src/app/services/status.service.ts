import { Injectable } from '@angular/core';
import { Status } from '../models/status';

@Injectable({
  providedIn: 'root',
})
export class StatusService {
  constructor() {}

  public renderStatus(status: Status) {
    if (status === 'NOTSENT') return 'Not sent yet.';
    if (status === 'SENDING') return 'Sending...';
    if (status === 'SENT') return 'Delivered';
    if (status === 'ERROR') return 'Something went wrong sending your message';
    if (status === 'READ') return 'Read';
    if (status === 'REPLIED') return 'Replied';
    if (status === 'ANSWERED') return 'Answered';
    if (status === 'QUEUED') return 'Queued';
    if (status === 'DELIVERED') return 'Delivered';

    if (status.length > 0) {
      return status.slice(0, 1) + status.slice(1).toLowerCase();
    }

    return 'Unknown status';
  }
}
