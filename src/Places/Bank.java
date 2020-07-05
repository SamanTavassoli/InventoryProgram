package Places;

import Entities.Investment.Bond;
import Entities.Investment.InvestmentItem;
import Entities.Player;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        investmentItemsGivenOut.add(new Bond("Example Bond", 500, 1.1, new Date(), new Player("Bud", 100)));

        InvestmentItem investmentItem = investmentItemsGivenOut.get(0);
        Date date = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
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
     * - compounded annually
     *
     * @param investmentItem item who's return is calculated
     * @return the amount of money that should be credited to the player including the price
     */
    public static int calculateReturn(InvestmentItem investmentItem) {
        //TODO: fix this it's not working properly
        int principal = investmentItem.getVALUE();
        double interestRate = investmentItem.getINTEREST_RATE();
        double yearsInvested = (double) calculateDaysHeld(investmentItem) / 365;
        int compoundTimes = 1; // per year (compounded annually)

        System.out.println(principal);
        System.out.println(interestRate);
        System.out.println(yearsInvested);
        System.out.println(compoundTimes);

        // compound interest formula (did not subtract initial amount)
        return (int) (principal * ( Math.pow( 1.0 + (interestRate / yearsInvested), yearsInvested * (double) compoundTimes)));
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
