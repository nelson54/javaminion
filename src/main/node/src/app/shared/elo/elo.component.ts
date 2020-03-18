import { Component, Input } from '@angular/core';

const template = `<span class="badge badge-secondary">ELO: {{score}}</span>`;

@Component({
  selector: 'elo',
  template
})
export class EloComponent {
  @Input() score: number;
  constructor() {}
}
