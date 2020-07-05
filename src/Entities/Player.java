package Entities;

import java.util.ArrayList;

public class Player {

    public final String NAME;
    public final int PLAYER_ID; // specific to each player
    private int balance;

    public static final int MAX_INVENTORY_SIZE = 10;

    private ArrayList<Item> inventory;

    public Player(String name, int playerID) {
        NAME = name;
        PLAYER_ID = playerID;
        this.balance = 0;
        inventory = new ArrayList<>();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    // Balance

    public int getBalance() { return balance; }
    public void addToBalance(int amount) { balance += amount; }
    public void subtractFromBalance(int amount) {
        balance -= amount;
    }


    // Inventory

    /**
     * Inventory is full when it has reached max inventory size
     * @return true if inventory is full
     */
    public boolean inventoryIsFull() {
        return inventory.size() == MAX_INVENTORY_SIZE;
    }

    /**
     * Adds the item to the inventory if it's not full
     * @param item item to be added
     * @return true if item could be added, otherwise false
     */
    public boolean addToInventory(Item item) {
        if (inventoryIsFull()) {
            return false;
        } else {
            inventory.add(item);
            return true;
        }
    }

    /**
     * Removes item from inventory if it exists
     * @param item item to remove
     * @return true if the inventory contained item prior to deletion
     */
    public boolean removeFromInventory(Item item) {
        return inventory.remove(item);
    }

    /**
     * Checks if inventory has the item looked for
     * @param item item looked for
     * @return true if inventory contains item
     */
    public boolean inventoryContains(Item item) {
        return inventory.contains(item);
    }

    /**
     * Clears all items from the inventory
     * @return true if there was at least one object in the inventory before clearing
     */
    public boolean clearInventory() {
        if (inventory.isEmpty()) {
            return false;
        } else {
            inventory.clear();
            return true;
        }
    }
}
