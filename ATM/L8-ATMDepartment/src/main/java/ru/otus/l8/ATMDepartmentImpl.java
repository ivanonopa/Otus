package ru.otus.l8;

import ru.otus.l7.ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMDepartmentImpl implements ATMDepartment {
    private List<ATM> atms = new ArrayList<>();
    private Map<ATM, ATMMemento> atmsInitialState = new HashMap<>();

    @Override
    public void addAtm(ATM atm) {
        atms.add(atm);
        atmsInitialState.put(atm, new ATMMemento(atm));
    }

    @Override
    public void removeAtm(ATM atm) {
        atms.remove(atm);
    }

    @Override
    public long getRemainderInAllAtms() {
        long remainder = 0;
        for (ATM atm : atms) {
            remainder += atm.getBalance();
        }
        return remainder;
    }

    @Override
    public void setInitialStateOfAtms() {
        atms.forEach(atm -> atm.setBanknoteCells(atmsInitialState.get(atm).getState().getBanknoteCells()));
    }
}
