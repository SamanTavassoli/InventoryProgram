import Entities.Item;
import Entities.Player;
import Utils.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TransactionManagerTest {

    private Player[] players;
    private ArrayList<Item> items;

    @Before
    public void setup() {
        players = TestItems.getPlayers();
        items = new ArrayList<>();
        items.addAll(Arrays.asList(TestItems.getTestItems()));
    }

    private void clearPlayerInventories() {
        for (Player player : players) {
            player.clearInventory();
        }
    }

    private void testSendItemForCase(Item item, boolean sendItemReturn, boolean player1HasItem, boolean player2HasItem) {
        assertEquals("Invalid return value for sendItem()", sendItemReturn, TransactionManager.sendItem(players[0], players[1], item) );
        assertEquals("Unexpected inventory for player1", player1HasItem, players[0].getInventory().contains(item));
        assertEquals("Unexpected inventory for player1", player2HasItem, players[1].getInventory().contains(item));
    }

    @Test
    public void testSendItem() {
        Item bag = new Item("鞄");

        // Sending player has one item and receiving player has empty inventory
        players[0].addToInventory(bag);
        testSendItemForCase(bag, true, false, true);

        // Sending player has one item and receiving player has full inventory
        clearPlayerInventories();
        players[0].addToInventory(bag);
        players[1].getInventory().addAll(items);
        testSendItemForCase(bag, false, true, false);

        // Sending player has no item and receiving player has empty inventory
        clearPlayerInventories();
        testSendItemForCase(bag, false, false, false);

        // Sending player has no item and receiving player has full inventory
        clearPlayerInventories();
        players[1].getInventory().addAll(items);
        testSendItemForCase(bag, false, false, false);
    }
}
