package ru.otus.l7;

public class Banknote implements Comparable<Banknote>{
    private Integer denomination;

    Banknote(Integer denomination) {
        this.denomination=denomination;
    }

    Integer getDenomination() {
        return denomination;
    }

    @Override
    public int compareTo(Banknote b) {
        return b.getDenomination().compareTo(this.denomination);
    }

    @Override
    public String toString() {
        return "Банкнот по " + denomination + " рублей ";
    }
}
