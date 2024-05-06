import { Component, } from '@angular/core';
import { User } from '../../interfaces/user';
import { CardComponent } from './card/card.component';
import { CommonModule } from '@angular/common';
import { MatGridListModule } from '@angular/material/grid-list';


@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CardComponent, CommonModule, MatGridListModule],
  templateUrl: './users.component.html',
})
export class UsersComponent {
  users: User[] = [
    {
      id: 1,
      name: 'John Doe',
      email: 'jdoe@example.org'
    },
    {
      id: 2,
      name: 'Jane Doe',
      email: 'janedoe@example.org'
    },
    {
      id: 3,
      name: 'Alice',
      email: 'alice@example.org'
    },
    {
      id: 4,
      name: 'Bob',
      email: 'bob@example.org'
    },
    {
      id: 5,
      name: 'Charlie',
      email: 'charlie@example.org'
    },
    {
      id: 6,
      name: 'David',
      email: 'david@example.org'
    },
    {
      id: 7,
      name: 'Eve',
      email: 'eve@example.org'
    },
    {
      id: 8,
      name: 'Frank',
      email: 'frank@example.org'
    },
    {
      id: 9,
      name: 'Grace',
      email: 'grace@example.org'
    },
    {
      id: 10,
      name: 'Helen',
      email: 'helen@example.org'
    },
    {
      id: 11,
      name: 'Ivan',
      email: 'ivan@example.org'
    },
    {
      id: 12,
      name: 'Judy',
      email: 'judy@example.org'
    },
    {
      id: 13,
      name: 'Kevin',
      email: 'kevin@example.org'
    },
    {
      id: 14,
      name: 'Laura',
      email: 'laura@example.org'
    },
    {
      id: 15,
      name: 'Mike',
      email: 'mike@example.org'
    }
  ];
  
  handleDelete(deletedUser: User) {
    this.users = this.users.filter(user => user !== deletedUser);
  }
}

