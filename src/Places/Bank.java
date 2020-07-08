package Places;

import Entities.Investment.InvestmentItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bank {

    // FIELDS ---------------------------------------------

    /** playerID -> Bond */
    private static ArrayList<InvestmentItem> investmentItemsGivenOut = new ArrayList<>();

    /** bonds that have been redeemed or canceled go here */
    private static ArrayList<InvestmentItem> expiredItems = new ArrayList<>();

    // Get Set ---------------------------------------------

    public static ArrayList<InvestmentItem> getInvestmentItemsGivenOut() {
        return investmentItemsGivenOut;
    }

    public static ArrayList<InvestmentItem> getExpiredItems() {
        return expiredItems;
    }

    // Methods -----------------------------------------------

    public static void clearExpiredItems() {
        expiredItems.clear();
    }

    public static void clearInvestmentItemsGivenOut() {
        investmentItemsGivenOut.clear();
    }

    /**
     * Temporary method for investing
     * @param investmentItem item that is added to the list of investments the bank has given out
     */
    public static void invest(InvestmentItem investmentItem) {
        investmentItemsGivenOut.add(investmentItem);
    }

    // Redeeming -----

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

        double t = (double) calculateDaysHeld(investmentItem) / 365; // years invested
        double finalAmount = investmentItem.getVALUE();

        // calculate yearly increase for each whole year passed
        int yearsAccountedFor = 0;
        for (int i = (int) t; i > 0; i-- ) {
            yearsAccountedFor++;
            double interestRateAdjustedForVolatility = getInterestRateAdjustedForVolatility(investmentItem);
            finalAmount += finalAmount * interestRateAdjustedForVolatility / 100; // add the year's profit or loss
        }
        double remainingTime = t - yearsAccountedFor; // less than 1

        // calculate increase in the remaining time which is less than a year
        finalAmount += finalAmount * getInterestRateAdjustedForVolatility(investmentItem) / 100 * remainingTime;

        return (int) finalAmount; // TODO: get correct interest rate back not rounded down amount
    }

    /**
     * Makes a random adjustment to an investment item's interest rate based on it's volatility
     * @param investmentItem item for which an adjusted interest rate is needed
     * @return the interest rate randomly adjusted based on it's volatility
     */
    private static double getInterestRateAdjustedForVolatility(InvestmentItem investmentItem) {
        Random random = new Random();
        return random.nextGaussian() // random normal distribution value with std dev of 1
                * investmentItem.getVolatility() // multiply by investment item's std dev
                + investmentItem.getINTEREST_RATE();
    }

    /**
     * Calculates the number of days between the time the investmentItem was purchased and today's date
     */
    public static int calculateDaysHeld(InvestmentItem investmentItem) {

        long timeHeld = (new Date()).getTime() - investmentItem.getDATE_OF_PURCHASE().getTime();
        long daysHeld = TimeUnit.DAYS.convert(timeHeld, TimeUnit.MILLISECONDS);

        return (int) daysHeld;
    }


    // Investing in other players






}
