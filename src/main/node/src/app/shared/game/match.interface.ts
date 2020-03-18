interface Account {
  id: string;
  elo: number;
  user: User;
}

interface UserAccount {
  id: string;
  account: Account;
}

interface User {
  id: string;
  username: string;
}

export interface Match {
  id: string;
  matchState: string;
  createdAt: string;
  participants: UserAccount[];
  winner: UserAccount;
  scores: { string: number };
}
