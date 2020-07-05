package Places;

import Entities.Investment.Bond;
import Entities.Investment.InvestmentItem;
import Entities.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Bank {

    public static void main(String[] args) {

        testDateAndReturn();
    }

    // testing a bond made at a previous date to obtain days in between and money returned
    private static void testDateAndReturn() {

        investmentItemsGivenOut.add(new Bond("Example Bond", 100, 2.7, new Date(), new Player("Bud", 100)));

        InvestmentItem investmentItem = investmentItemsGivenOut.get(0);
        Date date = new GregorianCalendar(2019, Calendar.JULY, 5).getTime();
        investmentItem.getDATE_OF_PURCHASE().setTime(date.getTime());

        System.out.println("Days held: " + calculateDaysHeld(investmentItem));
        System.out.println("Principal + interest: " + calculateReturn(investmentItem));


    }

    // playerID -> Bond
    private static ArrayList<InvestmentItem> investmentItemsGivenOut = new ArrayList<>();

    // bonds that have been redeemed or canceled go here
    private static ArrayList<InvestmentItem> expiredItems = new ArrayList<>();


    /**
     * Calculates the return of an investment item based on
     * - the date of purchase
     * - the amount purchased
     * - the interest rate on the item
     *
     * This rounds the amount you get down so a 2.7% return is actually 2%
     *
     * @param investmentItem item who's return is calculated
     * @return the amount of money that should be credited to the player including the price
     */
    public static int calculateReturn(InvestmentItem investmentItem) {

        double principal = investmentItem.getVALUE();
        double r = investmentItem.getINTEREST_RATE(); // interest rate
        double t = (double) calculateDaysHeld(investmentItem) / 365; // years invested

        double finalAmount = principal;
        int yearsAccountedFor = 0;
        // calculate yearly increase for each whole year passed
        for (int i = (int) t; i > 0; i-- ) {
            yearsAccountedFor++;
            finalAmount += finalAmount * r / 100;
        }
        double remainingTime = t - yearsAccountedFor; // should be less than 1

        // calculate increase in the remaining time which is less than a year
        finalAmount += finalAmount * r / 100 * remainingTime;

        return (int) finalAmount; // TODO: get correct interest rate back not rounded down amount

    }

    /**
     * Calculates the number of days between the time the investmentItem was purchased and today's date
     */
    public static int calculateDaysHeld(InvestmentItem investmentItem) {
        long timeHeld = (new Date()).getTime() - investmentItem.getDATE_OF_PURCHASE().getTime();
        long daysHeld = TimeUnit.DAYS.convert(timeHeld, TimeUnit.MILLISECONDS);

        return (int) daysHeld;
    }

    /**
     * Redeem an investment item if it exists in the list of investmentItemsGivenOut
     * @param investmentItem item to be redeemed
     * @return true if the item was given out and has been redeemed
     */
    public static boolean redeem(InvestmentItem investmentItem) {

        if (investmentItemsGivenOut.remove(investmentItem)) {
            // give owner their return
            investmentItem.getOWNER().addToBalance(calculateReturn(investmentItem));

            expiredItems.add(investmentItem);
            return true;
        }

        return false;
    }






}
