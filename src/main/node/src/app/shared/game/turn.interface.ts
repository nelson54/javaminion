import { Card } from '@app/shared/game/card.interface';

export interface Turn {
  id: string;
  playerId: string;
  phase: string;
  play: Card[];
  buyPool: number;
}
