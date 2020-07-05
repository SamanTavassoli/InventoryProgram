package Entities.Investment;

import Entities.Player;

import java.util.Date;

/**
 * Stocks are investment items that have high volatility and high return
 */
public class Stock implements InvestmentItem {

    private final String NAME;
    private final int VALUE; // can be any value the player wants to buy
    private final double INTEREST_RATE;
    private final Date DATE_OF_PURCHASE;
    private final Player OWNER;

    public Stock(String name, int value, double interest_rate, Date dateOfPurchase, Player owner) {
        NAME = name;
        VALUE = value;
        INTEREST_RATE = interest_rate;
        DATE_OF_PURCHASE = dateOfPurchase;
        OWNER = owner;
    }

    @Override
    public String getNAME() {
        return NAME;
    }

    @Override
    public int getVALUE() {
        return VALUE;
    }

    @Override
    public double getINTEREST_RATE() {
        return INTEREST_RATE;
    }

    @Override
    public Date getDATE_OF_PURCHASE() {
        return DATE_OF_PURCHASE;
    }

    @Override
    public Player getOWNER() {
        return OWNER;
    }


}
