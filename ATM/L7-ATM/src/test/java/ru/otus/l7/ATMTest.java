package ru.otus.l7;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ATMTest {

    private Banknote banknote100 = new Banknote(Denomination.HUNDRED);
    private Banknote banknote50 = new Banknote(Denomination.FIFTY);
    private Banknote banknote500 = new Banknote(Denomination.FIVEHUNDRED);
    private Banknote banknote200 = new Banknote(Denomination.TWOHUNDRED);
    private Banknote banknote2000 = new Banknote(Denomination.TWOTHOUSEND);

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

        Map<Banknote, Integer> withdrawBundle = atm.withdrawCash(750);
        assertEquals(2000,atm.getBalance());

        assertEquals(Optional.of(1), Optional.ofNullable(withdrawBundle.get(banknote500)));
        assertEquals(Optional.of(1), Optional.ofNullable(withdrawBundle.get(banknote100)));
        assertEquals(Optional.of(3), Optional.ofNullable(withdrawBundle.get(banknote50)));
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

    @Test
    public void getBanknoteTest(){
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        ATM atm = new ATMImpl(moneyBundle);

        Map<Banknote,Integer> banknotes = atm.getBanknoteCells();
        assertEquals(Optional.of(5), Optional.ofNullable(banknotes.get(banknote500)));
    }

    @Test
    public void setBanknoteTest() {
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote200, 5);
        ATM atm = new ATMImpl(moneyBundle);

        Map<Banknote,Integer> banknotes = new HashMap<>();
        banknotes.put(banknote2000, 3);

        atm.setBanknoteCells(banknotes);

        assertEquals(Optional.of(3), Optional.ofNullable(atm.getBanknoteCells().get(banknote2000)));
        assertEquals(6000,atm.getBalance());
    }
}
