package ru.otus.l7;

import java.util.Map;

public interface ATM {

    void depositCash(Map<Banknote,Integer> moneyBundle);

    Map<Banknote, Integer> withdrawCash(Integer amount);

    Map<Banknote, Integer> getBanknoteCells();

    void setBanknoteCells(Map<Banknote, Integer> banknoteCells);

    long getBalance();

}