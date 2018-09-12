package ru.otus.l7;

public class Banknote implements Comparable<Banknote>{
    private Denomination denomination;

    Banknote(Denomination denomination) {
        this.denomination=denomination;
    }

    Integer getDenomination() {
        return denomination.getDenomination();
    }

    @Override
    public int compareTo(Banknote b) {
        return b.getDenomination().compareTo(this.denomination.getDenomination());
    }

    @Override
    public String toString() {
        return "Банкнот по " + denomination.getDenomination() + " рублей ";
    }
}
