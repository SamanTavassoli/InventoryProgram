package Places;

import Entities.Item;
import Entities.Player;

import java.util.HashMap;
import java.util.Map;

public class Shop {

    /**
     * Stock of the shop
     * Each item corresponds to a price
     */
    public static HashMap<Item, Integer> stock = new HashMap<>();


    /**
     * Players can buy items from the shop at the according price
     * The shop must have that item
     * The player must have enough money
     * The play must have enough space in their inventory
     *
     * The balance is first debited, then the item is removed
     * from the shop, the added to the player's inventory
     *
     * @param player player buying the item
     * @param item item requested
     * @return true if the item was bought
     */
    public static boolean buy(Player player, Item item) {
        int price = stock.get(item);

        if (!stock.containsKey(item)
                || player.getBalance() < price
                || player.inventoryIsFull()) {
            return false;
        }

        player.subtractFromBalance(price);
        stock.remove(item);
        player.addToInventory(item);
        return true;
    }

    public static void printStock() {
        for (Map.Entry<Item, Integer> entry : stock.entrySet()) {
            System.out.println(entry.getKey() + "price: " + entry.getValue());
        }
    }


}
