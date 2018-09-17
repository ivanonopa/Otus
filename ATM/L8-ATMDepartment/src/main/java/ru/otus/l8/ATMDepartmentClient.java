package ru.otus.l8;

import ru.otus.l7.ATM;
import ru.otus.l7.ATMImpl;
import ru.otus.l7.Banknote;
import ru.otus.l7.Denomination;

import java.util.HashMap;
import java.util.Map;

public class ATMDepartmentClient {
    public static void main(String[] args) {
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        Banknote banknote100 = new Banknote(Denomination.HUNDRED);
        Banknote banknote50 = new Banknote(Denomination.FIFTY);
        Banknote banknote500 = new Banknote(Denomination.FIVEHUNDRED);
        Banknote banknote200 = new Banknote(Denomination.TWOHUNDRED);


        moneyBundle.put(banknote500, 5);
        moneyBundle.put(banknote50, 20);
        moneyBundle.put(banknote100, 2);

        ATM atm = new ATMImpl(moneyBundle);

        moneyBundle.put(banknote200, 20);

        ATM atm2 = new ATMImpl(moneyBundle);

        ATMDepartment atmDepartment = new ATMDepartmentImpl();

        atmDepartment.addAtm(atm);
        atmDepartment.addAtm(atm2);

        System.out.println(atmDepartment.getRemainderInAllAtms());

//        atmDepartment.removeAtm(atm);

        atm.withdrawCash(300);
        atm.withdrawCash(400);

        System.out.println(atmDepartment.getRemainderInAllAtms());

        atmDepartment.setInitialStateOfAtms();

        System.out.println(atmDepartment.getRemainderInAllAtms());

    }
}
