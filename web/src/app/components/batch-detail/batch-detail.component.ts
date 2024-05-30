import { Component, computed, effect, inject, Inject, Input, signal, Signal } from '@angular/core';
import { BatchesService } from '../../services/batches.service';
import { Batch } from '../../models/batch';
import { LucideAngularModule } from 'lucide-angular';
import { platforms } from '../../models/platform';
import { SpinnerComponent } from '../spinner/spinner.component';
import { minDelay, sleep } from '../../utils/mindelay';
import {Contact} from "../../models/contact";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-batch-detail',
  standalone: true,
  templateUrl: './batch-detail.component.html',
  imports: [LucideAngularModule, SpinnerComponent, FormsModule, CommonModule],
})
export class BatchDetailComponent {
  @Input() selectedBatchId!: Signal<string | null>;
  public batchesService = inject(BatchesService);
  public selectedBatch = computed(() => this.batchesService.batches().find((batch) => batch.id === this.selectedBatchId()));
  public editingContact: Contact | null = null;
  public platforms = platforms;

  async onSendClick() {
    const id = this.selectedBatchId();
    if (!id) return;
    this.batchesService.updateBatch(id, { state: 'SENDING' });
    await sleep(1000);

    this.batchesService.updateBatch(id, {
      contacts: this.selectedBatch()?.contacts.map((contact) => ({ ...contact, status: 'SENDING' })) ?? [],
    });

    await sleep(1000);

    const contactStatus = ['SENT', 'ERROR', 'READ', 'REPLIED'];
    this.batchesService.updateBatch(id, {
      contacts:
        this.selectedBatch()?.contacts.map((contact) => ({
          ...contact,
          status: contactStatus[Math.round(Math.random() * contactStatus.length)] as 'NOTSENT',
        })) ?? [],
    });
    await sleep(2000);
    this.batchesService.updateBatch(id, { state: 'SENT' });

    const batch = this.selectedBatch();
    if (!batch) {
      console.error('Batch not found');
      return;
    }
  } catch (error: any) {
    console.error('Error sending batch', error);
  }

  onDeleteClick(contact: Contact) { //To delete the contact
    console.log(contact.nickname + ' deleted');
  }

  onEditClick(contact: Contact) { //To edit the contact
    this.editingContact = contact;
  }

  onSaveClick() { //Click the save button in editing mode
    console.log(this.editingContact?.nickname);
    this.editingContact = null;
  }
}
