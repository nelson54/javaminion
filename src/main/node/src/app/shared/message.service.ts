import { Injectable } from '@angular/core';

type SubscribeFunction = (message: any) => any;

@Injectable()
export class MessageService {
  subscribers: SubscribeFunction[] = [];

  constructor() {}

  publish(message: any) {
    return this.subscribers.forEach(func => func(message));
  }

  subscribe(func: SubscribeFunction) {
    this.subscribers.push(func);
  }
}
