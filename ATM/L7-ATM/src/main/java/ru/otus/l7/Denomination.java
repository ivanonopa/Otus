package ru.otus.l7;

public enum Denomination {
    FIFTY(50), HUNDRED(100), TWOHUNDRED(200), FIVEHUNDRED(500), THOUSEND(1000), TWOTHOUSEND(2000), FIVETHOUSEND(5000);
    private int denomination;

    public int getDenomination() {
        return denomination;
    }

    Denomination(int denomination) {
        this.denomination = denomination;
    }
}
