package ru.otus.l8;

import ru.otus.l7.ATM;

public interface ATMDepartment {
    void addAtm(ATM atm);

    void removeAtm(ATM atm);

    long getRemainderInAllAtms();

    void setInitialStateOfAtms();
}
