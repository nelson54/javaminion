import { Cost } from '@app/shared/game/cost.interface';

export interface Card {
  id: string;
  name: string;
  cardTypes: string[];
  cost: Cost;
  kingdomSortOrder: number;
  remaining?: number;
}
