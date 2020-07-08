package Entities.Investment;

import Entities.Item;
import Entities.Player;

import java.util.Date;

/**
 * A contract can be created between two players where one player gives money
 * to the other player and expects a certain sum back by a certain time. The other player
 * has a specific amount of time to pay back the extra and if he doesn't have enough money
 * to pay back by the time of the agreement then he has go give away an agreed upon item.
 */
public class Contract {

    /** Player investing */
    private final Player PLAYER1;
    /** Player receiving investment */
    private final Player PLAYER2;
    /** Item put as collateral in case player 2 cannot pay back, it
     * cannot be used until sum agreed upon is paid back */
    private final Item COLLATERAL;
    /** Initial sum that player1 invests in player2 */
    private final int INITIAL_SUM;
    /** Sum that player 2 has to pay player1 back */
    private final int SUM_TO_BE_PAID_BACK;
    /** Date by which the sum to be paid back must be paid back */
    private final Date REPAYMENT_DATE;
    /** Has the contract ended either through payment or exchange of collateral */
    private boolean isContractValid;


    /**
     * Create a contract
     *
     * Checks that the conditions for creating a contract are met (initial sum, collateral...)
     * Collects initial sum from first player and gives it to second player
     * Collects collateral and stores it within the class
     *
     *
     * @param player1 player that is investing in second player
     * @param player2 player that is receiving the money
     * @param collateral item given to player1 if player2 does not pay back
     * @param initialSum sum player1 initially gives to player2
     * @param sumToBePaidBack sum player2 has to give back to player1 at the end of the lending period
     * @param repaymentDate date by which player2 has to pay back
     */
    public Contract(Player player1, Player player2, Item collateral, int initialSum, int sumToBePaidBack, Date repaymentDate) {

        checkPlayerHasSum(player1, initialSum);
        checkPlayerHasItem(player2, collateral);

        PLAYER1 = player1;
        PLAYER2 = player2;
        COLLATERAL = collateral;
        INITIAL_SUM = initialSum;
        SUM_TO_BE_PAID_BACK = sumToBePaidBack;
        REPAYMENT_DATE = repaymentDate;

        player2.removeFromInventory(collateral);
        player1.subtractFromBalance(initialSum);
        player2.addToBalance(initialSum);

        isContractValid = true;
    }

    private void checkPlayerHasSum(Player player1, int initialSum) {
        if (player1.getBalance() < initialSum) {
            throw new IllegalArgumentException("Player1's balance must have enough money to cover initialSum");
        }
    }

    private void checkPlayerHasItem(Player player2, Item collateral) {
        if (!player2.getInventory().contains(collateral)) {
            throw new IllegalArgumentException("Player2's inventory must contain the collateral item");
        }
    }

    public boolean payBackEarly() throws Exception {
        if (!isContractValid) { // should not ever be called in this case
            throw new Exception("This contract has already been terminated");
        }

        if (PLAYER2.inventoryIsFull()) {
            System.err.println("Player2 does not have enough inventory space to receive collateral item");
            return false;
        }

        checkPlayerHasSum(PLAYER2, SUM_TO_BE_PAID_BACK);
        PLAYER2.subtractFromBalance(SUM_TO_BE_PAID_BACK);
        PLAYER1.addToBalance(SUM_TO_BE_PAID_BACK);
        PLAYER2.addToInventory(COLLATERAL);

        // terminate contract
        isContractValid = false;

        return true;
    }

}
