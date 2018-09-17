package ru.otus.l7;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) {

        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        Banknote banknote100 = new Banknote(Denomination.HUNDRED);
        Banknote banknote50 = new Banknote(Denomination.FIFTY);
        Banknote banknote500 = new Banknote(Denomination.FIVEHUNDRED);


        moneyBundle.put(banknote500, 5);
        moneyBundle.put(banknote50, 20);
        moneyBundle.put(banknote100, 2);

        ATM atm = new ATMImpl(moneyBundle);

        atm.depositCash(moneyBundle);
        System.out.println(atm.getBalance());
        try {
            System.out.println(atm.withdrawCash(1350));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println(atm.getBalance());
    }
}
