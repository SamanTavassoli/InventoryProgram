
import Entities.Item;
import Entities.Player;

public class TestItems {

    private static Item[] testItems = {
            new Item("名刺"),
            new Item("ケータイ"),
            new Item("スマホ"),
            new Item("電子"),
            new Item("ノート"),
            new Item("紙"),
            new Item("手帳"),
            new Item("ボールペン"),
            new Item("シャープペンシル"),
            new Item("鉛筆")
    };

    private static Player[] players = {
                    new Player("妹", 1),
                    new Player("弟", 2),
                    new Player("姉", 3),
                    new Player("兄", 4)
    };

    public static Item[] getTestItems() {
        return testItems;
    }

    public static Player[] getPlayers() {
        return players;
    }
}
