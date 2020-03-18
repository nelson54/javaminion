'use strict';

import { Component, Input } from '@angular/core';

@Component({
  selector: 'identicon',
  template:
    '<img src="https://identicon-api.herokuapp.com/{{username}}/{{size}}?format=png" class="img-thumbnail bg-white" alt="" />'
})
export class IdenticonComponent {
  @Input() size = 32;
  @Input() username: any;

  constructor() {}
}
