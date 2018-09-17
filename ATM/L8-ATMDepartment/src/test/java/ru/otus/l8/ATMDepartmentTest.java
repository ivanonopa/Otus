package ru.otus.l8;

import org.junit.Test;
import ru.otus.l7.ATM;
import ru.otus.l7.ATMImpl;
import ru.otus.l7.Banknote;
import ru.otus.l7.Denomination;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ATMDepartmentTest {
    private Banknote banknote100 = new Banknote(Denomination.HUNDRED);
    private Banknote banknote50 = new Banknote(Denomination.FIFTY);
    private Banknote banknote500 = new Banknote(Denomination.FIVEHUNDRED);
    private Banknote banknote200 = new Banknote(Denomination.TWOHUNDRED);
    private Banknote banknote2000 = new Banknote(Denomination.TWOTHOUSEND);

    @Test
    public void remainderTest(){
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote500, 5);
        ATM atm = new ATMImpl(moneyBundle);
        moneyBundle.put(banknote2000,3);
        ATM atm2 = new ATMImpl(moneyBundle);

        ATMDepartment atmDepartment = new ATMDepartmentImpl();

        atmDepartment.addAtm(atm);
        atmDepartment.addAtm(atm2);

        assertEquals("Проверка остатка во всез АТМ", 500*5+500*5+2000*3,atmDepartment.getRemainderInAllAtms());
    }

    @Test
    public void setInitialState() {
        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        moneyBundle.put(banknote200, 5);
        ATM atm = new ATMImpl(moneyBundle);

        Map<Banknote, Integer> moneyBundle2 = new HashMap<>();
        moneyBundle2.put(banknote50, 25);
        ATM atm2 = new ATMImpl(moneyBundle2);

        Map<Banknote, Integer> moneyBundle3 = new HashMap<>();
        moneyBundle3.put(banknote100, 15);
        ATM atm3 = new ATMImpl(moneyBundle3);

        ATMDepartment atmDepartment = new ATMDepartmentImpl();
        atmDepartment.addAtm(atm);
        atmDepartment.addAtm(atm2);
        atmDepartment.addAtm(atm3);

        atm.withdrawCash(200);
        assertEquals(800,atm.getBalance());

        atm2.withdrawCash(300);
        assertEquals(950,atm2.getBalance());

        atm3.withdrawCash(500);
        assertEquals(1000,atm3.getBalance());

        atmDepartment.setInitialStateOfAtms();

        assertEquals(1000,atm.getBalance());
        assertEquals(1250,atm2.getBalance());
        assertEquals(1500,atm3.getBalance());
    }
}
