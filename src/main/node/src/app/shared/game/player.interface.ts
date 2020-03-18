import { Turn } from '@app/shared/game/turn.interface';
import { Card } from '@app/shared/game/card.interface';
import { Choice } from '@app/shared/game/choice.interface';

interface Account {
  id: string;
}

export interface Player {
  id: string;
  name: string;
  account: Account;
  victoryPoints: number;
  discard: Card[];
  hand: Card[];
  deck: Card[];
  currentTurn: Turn;
  choices: Choice[];
}
