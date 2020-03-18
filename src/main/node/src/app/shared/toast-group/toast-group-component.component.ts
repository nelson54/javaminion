import { Component } from '@angular/core';
import { MessageService } from '@app/shared/message.service';

interface Message {
  time: string;
  addedAt: number;
  message: string;
}

@Component({
  selector: 'toast-group',
  templateUrl: './toast-group-component.component.html'
})
export class ToastGroupComponentComponent {
  messages: Message[];

  constructor(private messageService: MessageService) {
    this.messages = [];

    messageService.subscribe((message: any) => {
      this.messages.unshift(message);
    });

    setInterval(() => {
      const currentTime = Date.now();
      this.messages = this.messages.filter(message => {
        return (currentTime - message.addedAt) / 1000 < 5;
      });
    }, 100);
  }
}
