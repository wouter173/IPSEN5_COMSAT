import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject, Signal, signal, ViewChild, WritableSignal } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';
import { minDelay } from '../../utils/mindelay';
import { SpinnerComponent } from '../spinner/spinner.component';
import { z } from 'zod';
import { Contact, contactSchema } from '../../models/contact';
import { FormsModule } from '@angular/forms';
import { BatchesService } from '../../services/batches.service';
import { nanoid } from 'nanoid';

@Component({
  selector: 'app-batch-create-dialog',
  standalone: true,
  templateUrl: './batch-create-dialog.component.html',
  styleUrl: './batch-create-dialog.component.scss',
  imports: [CommonModule, LucideAngularModule, SpinnerComponent, FormsModule],
})
export class BatchCreateDialogComponent {
  @ViewChild('createModal') createModal!: ElementRef<HTMLDialogElement>;
  @ViewChild('createForm') createForm!: ElementRef<HTMLFormElement>;
  public batchesService = inject(BatchesService);

  public fileState: WritableSignal<FileState> = signal({ state: 'initial' });
  public name = signal('');

  openDialog() {
    this.createModal.nativeElement.showModal();
    this.name.set('Batch-' + (this.batchesService.batches().length + 1));
    this.fileState.set({ state: 'initial' });
  }

  closeDialog() {
    this.createModal.nativeElement.close();
  }

  async readFileContent(file: File) {
    const buffer = await file.arrayBuffer();
    const decoder = new TextDecoder();
    const output = decoder.decode(buffer);
    return output;
  }

  async onFileDrop(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;
    this.fileState.set({ state: 'loading' });

    const output = await minDelay(this.readFileContent(file));

    try {
      const json = JSON.parse(output);
      const data = z.array(contactSchema).parse(json);
      this.fileState.set({ state: 'success', value: file, contacts: data });
    } catch (e) {
      this.fileState.set({ state: 'error', error: 'Please upload a correctly formatted document' });
      return;
    }
  }

  onSubmit() {
    const contacts = this.fileState().contacts ?? [];
    const name = this.name();

    if (this.fileState().state === 'success') {
      this.batchesService.createBatch({
        id: nanoid(),
        state: 'NOTSENT',
        createdAt: new Date(),
        lastModified: new Date(),
        name,
        contacts: contacts.map((contact) => ({ ...contact, status: 'NOTSENT' })),
      });

      this.createForm.nativeElement.reset();
      this.closeDialog();
    }
  }
}

type FileState = { state: 'initial' | 'loading' | 'success' | 'error'; error?: string; value?: File; contacts?: Contact[] };
