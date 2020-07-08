import { Pipe, PipeTransform } from '@angular/core';
import { Match } from '@app/shared/game/match.interface';

@Pipe({ name: 'gameOrder' })
export class GameOrder implements PipeTransform {
  matchHasUser(match: Match, username: string) {
    return (
      match.participants.filter(participant => {
        return participant.account.user.username === username;
      }).length > 0
    );
  }

  transform(matches: Match[], username: string): Match[] {
    if (!matches) {
      return [];
    }

    matches.sort((m1, m2) => {
      if (m1 && !m2) {
        return 1;
      } else if (!m1 && m2) {
        return -1;
      } else if (!m1 && !m2) {
        return 0;
      }

      const m1HasUser = this.matchHasUser(m1, username);
      const m2HasUser = this.matchHasUser(m2, username);

      if (m1HasUser && !m2HasUser) {
        return 1;
      } else if (!m1HasUser && m2HasUser) {
        return -1;
      }

      if (m1.createdAt && !m2.createdAt) {
        return 1;
      } else if (!m1.createdAt && m2.createdAt) {
        return -1;
      } else if (!m1.createdAt && !m2.createdAt) {
        return 0;
      }

      return m1.createdAt.localeCompare(m2.createdAt) * -1;
    });

    return matches;
  }
}
