import Entities.Investment.Bond;
import Entities.Investment.InvestmentItem;
import Entities.Investment.Stock;
import Entities.Player;
import Places.Bank;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BankTest {


    private Player player;
    private static InvestmentItem[] investmentItems;

    @Before
    public void setup() {
        player = TestItems.getPlayers()[0];

        // adding some stocks and bonds taken out by each player
        Date date = new GregorianCalendar(2019, Calendar.JULY, 5).getTime();
        investmentItems = new InvestmentItem[]{
                new Bond("First Bond", 100, 2.7, date, player, 1.0),
                new Stock("First Stock", 100, 8.6, date, player, 6.0),
                new Bond("Second Bond", 500, 2.7, date, player, 1.0),
                new Stock("Second Stock", 500, 8.6, date, player, 6.0)
        };
    }

    @Test
    public void testRedeem() {

        // returns false when trying to redeem an item that has been been given out
        assertFalse("Item that should not have been able to be redeemed", Bank.redeem(investmentItems[0]));

        // returns true when redeeming an item that has been given out
        Bank.invest(investmentItems[1]);
        assertTrue("Item should have been redeemed", Bank.redeem(investmentItems[1]));

        Bank.clearInvestmentItemsGivenOut();


        // redeemed item goes from itemsGivenOut to expiredItems
        Bank.invest(investmentItems[0]);
        Bank.redeem(investmentItems[0]);
        assertFalse("Redeemed item must not be in investment items given out anymore", Bank.getInvestmentItemsGivenOut().contains(investmentItems[0]));
        assertTrue("Redeemed item must now be in expired items", Bank.getExpiredItems().contains(investmentItems[0]));

        Bank.clearExpiredItems();

        // TODO: Add more tests
    }

    @Test
    public void testCalculateReturn() {
        // TODO: Add more tests
    }

    @Test
    public void testCalculateDaysHeld() {
        // can't really tests this without doing what the method does because this value changes based on what day it is

        // cannot return value less than or equal to 0
        for (InvestmentItem investmentItem : investmentItems) {
            assertTrue("Must have held for positive amount of days", Bank.calculateDaysHeld(investmentItem) > 0);
        }

        // as of 5 Jul 2020, this date should be greater than 365
        assertTrue(Bank.calculateDaysHeld(investmentItems[0]) > 365);




    }

}
