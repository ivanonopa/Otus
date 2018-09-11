package ru.otus.l7;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ATMTest {

    private Banknote banknote100 = new Banknote(100);
    private Banknote banknote50 = new Banknote(50);
    private Banknote banknote500 = new Banknote(500);

    @Test
    public void balanceTest() {
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        moneyBundle.put(banknote50, 5);
        moneyBundle.put(banknote100, 2);

        ATM atm = new ATMImpl(moneyBundle);
        long balance = atm.getBalance();
        assertEquals("Количетсво денег в банкомате:", 500*5 +50*5 + 100*2, balance);

    }

    @Test
    public void depositCashTest() {
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        ATM atm = new ATMImpl(moneyBundle);

        Map<Banknote, Integer> extraBundle = new HashMap<>();
        extraBundle.put(banknote50,5);
        extraBundle.put(banknote100,5);
        atm.depositCash(extraBundle);

        assertEquals("Количество денег в банкомате:", 500*5+50*5+100*5, atm.getBalance());
    }

    @Test
    public void withdrawCashTest(){
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        moneyBundle.put(banknote100,1);
        moneyBundle.put(banknote50,3);
        ATM atm = new ATMImpl(moneyBundle);

        atm.withdrawCash(750);
        assertEquals(1000,atm.getBalance());
    }

    @Test(expected = RuntimeException.class)
    public void withdrawCashTest2(){
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        moneyBundle.put(banknote100,1);
        moneyBundle.put(banknote50,3);
        ATM atm = new ATMImpl(moneyBundle);

        atm.withdrawCash(850);
    }
}
