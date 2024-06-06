import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss',
})
export class ModalComponent {
  @Input() title: string = 'Placeholder Title';
  @Input() subTitle: string = 'Subtitle';
  @Output() private onClose = new EventEmitter<void>();
  @ViewChild('modal') private modalElement!: ElementRef<HTMLDialogElement>;

  open(): void {
    this.modalElement.nativeElement.showModal();
    this.modalElement.nativeElement.addEventListener('click', this.dialogClickHandler.bind(this));
  }

  close(): void {
    this.modalElement.nativeElement.close();
    this.modalElement.nativeElement.removeEventListener('click', this.dialogClickHandler);
    this.onClose.emit();
  }

  dialogClickHandler(e: MouseEvent) {
    const target = e.target as HTMLDialogElement;

    const rect = target.getBoundingClientRect();
    const clickedInDialog =
      rect.top <= e.clientY && e.clientY <= rect.top + rect.height && rect.left <= e.clientX && e.clientX <= rect.left + rect.width;

    if (clickedInDialog) return;

    this.close();
  }
}
