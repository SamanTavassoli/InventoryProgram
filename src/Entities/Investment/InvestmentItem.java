package Entities.Investment;

import Entities.Player;

import java.util.Date;

public interface InvestmentItem {

    public String getNAME();

    public int getVALUE();

    public double getINTEREST_RATE();

    public Date getDATE_OF_PURCHASE();

    public Player getOWNER();
}
