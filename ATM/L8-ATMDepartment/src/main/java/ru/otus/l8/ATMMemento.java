package ru.otus.l8;

import ru.otus.l7.ATM;
import ru.otus.l7.ATMImpl;

public class ATMMemento {
    private final ATM atm;

    public ATMMemento (ATM atm) {
        this.atm = new ATMImpl(atm);
    }

    public ATM getState() {
        return atm;
    }
}
