export interface Cost {
  money: number;
  potions: number;
}

export interface Card {
  cost: Cost;
  id: string;
  name: string;
  ownerId: string;
}

export interface PlayerDetails {
  hand: Card[];
}

export interface CardStack {
  cardTypes?: string[];
  cost?: Cost;
  kingdom?: boolean;
  kingdomSortOrder?: number;
  name?: string;
  size: number;
}
