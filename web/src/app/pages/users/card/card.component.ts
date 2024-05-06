import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from '../../../interfaces/user';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatIconModule, MatMenuModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent {
  @Input() user!: User;
  @Output() delete = new EventEmitter<User>();

  onDelete() {
    this.delete.emit(this.user);
  }
  
}
