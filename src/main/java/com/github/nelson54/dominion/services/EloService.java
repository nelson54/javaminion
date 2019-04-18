package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class EloService {

    private final AccountRepository accountRepository;

    public EloService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void updateEloForAccounts(Collection<Account> accounts, Long userIdWinner) {
        final HashMap<Long, Long> userRating = new HashMap<>();

        accounts.forEach((account) -> {
            userRating.put(account.getId(), account.getElo());
        });

        HashMap<Long, Long> updatedUserRating = calculateMultiplayer(userRating, userIdWinner);

        accounts.forEach((account) -> {
            account.setElo(updatedUserRating.get(account.getId()));
        });

        Iterable<AccountEntity> accountEntities = accountRepository.findAllById(
                accounts.parallelStream()
                    .map(Account::getId)
                        .collect(Collectors.toList()));

        accountEntities.forEach(accountEntity -> {
            accountEntity.setElo(updatedUserRating.get(accountEntity.getId()));
            accountRepository.save(accountEntity);
        });
    }


    private HashMap<Long, Long> calculateMultiplayer(HashMap<Long, Long> usersRating, Long userIdWinner) {

        if (usersRating.size() == 0) {
            return usersRating;
        }

        HashMap<Long, Long> newUsersPoints = new HashMap<Long, Long>();

        // K-factor
        int K = 32;

        // Calculate total Q
        double Q = 0.0;
        for (long userId : usersRating.keySet()) {
            Q += Math.pow(10.0, ((double) usersRating.get(userId) / 400));
        }

        // Calculate new rating
        for (long userId : usersRating.keySet()) {

            /**
             * Expected rating for an user
             * E = Q(i) / Q(total)
             * Q(i) = 10 ^ (R(i)/400)
             */
            double expected = (double) Math.pow(10.0, ((double) usersRating.get(userId) / 400)) / Q;

            /**
             * Actual score is
             * 1 - if player is winner
             * 0 - if player losses
             * (another option is to give fractions of 1/number-of-players instead of 0)
             */
            long actualScore;
            if (userIdWinner == userId) {
                actualScore = 1;
            } else {
                actualScore = 0;
            }

            // new rating = R1 + K * (S - E);
            long newRating = Math.round(usersRating.get(userId) + K * (actualScore - expected));

            // Add to HashMap
            newUsersPoints.put(userId, newRating);

        }

        return newUsersPoints;
    }

    /**
     * Calculate rating for 2 players
     *
     * @param player1Rating
     *            The rating of Player1
     * @param player2Rating
     *            The rating of Player2
     * @param outcome
     *            A string representing the game result for Player1
     *            "+" winner
     *            "=" draw
     *            "-" lose
     * @return New player rating
     */
    int calculate2PlayersRating(int player1Rating, int player2Rating, String outcome) {

        double actualScore;

        // winner
        if (outcome.equals("+")) {
            actualScore = 1.0;
            // draw
        } else if (outcome.equals("=")) {
            actualScore = 0.5;
            // lose
        } else if (outcome.equals("-")) {
            actualScore = 0;
            // invalid outcome
        } else {
            return player1Rating;
        }

        // calculate expected outcome
        double exponent = (double) (player2Rating - player1Rating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-factor
        int K = 32;

        // calculate new rating
        int newRating = (int) Math.round(player1Rating + K * (actualScore - expectedOutcome));

        return newRating;
    }

    /**
     * Determine the rating constant K-factor based on current rating
     *
     * @param rating
     *            Player rating
     * @return K-factor
     */
    public int determineK(int rating) {
        int K;
        if (rating < 2000) {
            K = 32;
        } else if (rating >= 2000 && rating < 2400) {
            K = 24;
        } else {
            K = 16;
        }
        return K;
    }

}