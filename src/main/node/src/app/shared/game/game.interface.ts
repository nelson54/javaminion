import { Turn } from '@app/shared/game/turn.interface';
import { Market } from '@app/shared/game/market.interface';
import { Player } from '@app/shared/game/player.interface';

export interface Game {
  id: string;
  players: { [key: string]: Player };
  pastTurns: Turn[];
  scores: [];
  matchState: string;
  kingdom: Market;
  commonCards: Market;
  logs: string[];
  turn: Turn;
}
