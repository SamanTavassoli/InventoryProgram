package Utils;

import Entities.Investment.Contract;
import Entities.Item;
import Entities.Player;

import java.util.ArrayList;

public class TransactionManager {

    private static ArrayList<Contract> trackedContracts = new ArrayList<>();
    private static ArrayList<Contract> expiredContracts = new ArrayList<>();

    // ---- Sending items or money between players

    /**
     * Send an item from one player to another
     *
     * Transaction is successful if both:
     * - player1 possesses the required item
     * - player2 has enough inventory space
     *
     * @param player1 player sending the item
     * @param player2 player receiving the item
     * @param item item being sent/received
     * @return true transaction was successful
     */
    public static boolean sendItem(Player player1, Player player2, Item item) {
        if (!player1.inventoryContains(item) || player2.inventoryIsFull()) {
            return false;
        }

        player1.removeFromInventory(item);
        player2.addToInventory(item);
        return true;
    }

    /**
     * Send money from player1 to player2 based on amount provided
     *
     * @param player1 player sending the money
     * @param player2 player receiving the money
     * @param amount amount being sent
     * @return true if transaction was successful
     */
    public static boolean sendMoney(Player player1, Player player2, int amount) {
        if (!(player1.getBalance() >= amount)) {
            return false;
        }

        player1.subtractFromBalance(amount);
        player2.addToBalance(amount);
        return true;
    }

    // ---- Managing Contracts

    /**
     * Adds a contract to the list of contracts that will be tracked and checked for expiration
     * @param contract contract to be tracked
     * @return true if the contract was not already being tracked
     */
    public static boolean trackContract(Contract contract) {
        return trackedContracts.add(contract);
    }

    /**
     * Checks if each contract tracked is past their repayment date, calls the appropriate method
     * in the contract to handle expired contracts within the any expired contracts and adds to the
     * expired contracts ArrayList
     */
    public static void checkContracts() throws Exception {
        for (Contract contract : trackedContracts) {
            if (contract.isPastRepaymentDate()) {
                if (!endContract(contract)) {
                    contract.contractHasNotBeenFulfilled();
                }
                trackedContracts.remove(contract);
                expiredContracts.add(contract);
            }
        }
    }

    /**
     * Ends a contract if the players meet the conditions required
     * Moves the contract from the tracked list to the expired list if it was tracked to begin with
     * @param contract contract to be ended
     * @return true if the contract could be ended
     * @throws Exception if contract was already terminated or expired
     */
    public static boolean endContract(Contract contract) throws Exception {

        if (expiredContracts.contains(contract)) {
            throw new Exception("An expired contract cannot be ended");
        }

        boolean couldPayBack = contract.payBackAndEndContract();

        if (couldPayBack) {
            if (trackedContracts.contains(contract)) {
                trackedContracts.remove(contract);
                expiredContracts.add(contract);
            }
        }

        return couldPayBack;
    }

}
