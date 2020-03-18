import { Cost } from '@app/shared/game/cost.interface';

export interface CardMarket {
  cardTypes: string[];
  cost: Cost;
  id: string;
  isKingdom: boolean;
  kingdom: boolean;
  kingdomSortOrder: number;
  moneyCost: number;
  moneyValue: number;
  name: string;
  remaining: number;
}
