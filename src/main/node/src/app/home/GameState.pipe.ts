import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'gameStatePipe' })
export class GameStatePipe implements PipeTransform {
  transform(input: string) {
    switch (input) {
      case 'IN_PROGRESS':
        return 'In Progress';
      case 'WAITING_FOR_PLAYERS':
        return 'Waiting';
      case 'FINISHED':
        return 'Finished';
    }
  }
}
