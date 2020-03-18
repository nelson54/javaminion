import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'phaseFilter' })
export class PhasePipe implements PipeTransform {
  transform(input: string) {
    switch (input) {
      case 'ACTION':
        return 'Action';
      case 'WAITING_FOR_CHOICE':
        return 'Choice';
      case 'BUY':
        return 'Buy';
      case 'END_OF_GAME':
        return 'Game Over';
      default:
        return input;
    }
  }
}
