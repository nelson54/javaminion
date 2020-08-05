export interface Cost {
  money: number;
  potions: number;
}

export interface Card {
  cost: Cost;
  id: string;
  name: string;
  ownerId: string;
  types: CardType[];
}

export interface PlayerDetails {
  deckSize: number;
  hand: Card[];
  discard: Card[];
  isMyTurn: boolean;
}

export interface CardStack {
  cardTypes?: string[];
  cost?: Cost;
  kingdom?: boolean;
  kingdomSortOrder?: number;
  name?: string;
  size: number;
}

export interface Turn {
  isMyTurn: boolean;
  money: number;
  buys: number;
  actions: number;
}

export interface CardRef {
  id ?: string;
  name ?: string;
}

export interface PlayersView {
  id: string;
  playerName: string;
  turnOrder: number;
  cardsInHand: number;
  cardsInDeck: number;
  cardsInDiscard: number;
  revealedThisTurn: CardRef[];
}

export enum Phase {
  WAITING_FOR_PLAYERS_TO_JOIN = 'WAITING_FOR_PLAYERS_TO_JOIN',
  ACTION = 'ACTION',
  BUY = 'BUY',
  WAITING_FOR_CHOICE = 'WAITING_FOR_CHOICE',
  WAITING_FOR_OPPONENT = 'WAITING_FOR_OPPONENT',
  END_OF_GAME = 'END_OF_GAME'
}

export enum CardType {
  ACTION = 'ACTION',
  ATTACK = 'ATTACK',
  VICTORY = 'VICTORY',
  TREASURE = 'TREASURE',
  REACTION = 'REACTION',
  CURSE = 'CURSE'
}

export interface GameView {
  hash: number;
  playerDetails: PlayerDetails;
  players: PlayersView[];
  gamePhase: Phase;
  cardMarket: CardStack[];
  turn: Turn;
  logs: string[];
}
