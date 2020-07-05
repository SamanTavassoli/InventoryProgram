import Entities.Item;
import Entities.Player;

public class TransactionManager {

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
}
