package ru.otus.l7;

import java.util.*;

public class ATMImpl implements ATM {

    ATMImpl(Map<Banknote, Integer> banknoteCells){
        this.banknoteCells= banknoteCells;
    }


    private Map<Banknote, Integer> banknoteCells;

    @Override
    public void depositCash(Map<Banknote, Integer> moneyBundle) {
        Set<Banknote> keys = moneyBundle.keySet();
        for (Banknote b : keys) {
            Integer currentBanknoteCount = banknoteCells.get(b);
            if (currentBanknoteCount != null) banknoteCells.put(b, currentBanknoteCount + moneyBundle.get(b));
            else banknoteCells.put(b, moneyBundle.get(b));
        }
    }

    @Override
    public Map<Banknote, Integer> withdrawCash(Integer amount) {

        if (amount > getBalance()) throw new RuntimeException("Не достаточно денег в банкомате");

        Map<Banknote, Integer> moneyBundle = new HashMap<>();
        Map<Banknote, Integer> banknoteCellsSorted = new TreeMap<>(banknoteCells);

        Integer lastAmount = amount;
        for (Banknote b : banknoteCellsSorted.keySet()) {
            Integer currentBanknoteCount = banknoteCells.get(b);
            Integer banknoteCount = lastAmount / b.getDenomination();

            if (banknoteCount <= currentBanknoteCount) {
                moneyBundle.put(b, banknoteCount);
                lastAmount -= banknoteCount * b.getDenomination();
            } else {
                moneyBundle.put(b, currentBanknoteCount);
                lastAmount -= currentBanknoteCount * b.getDenomination();
            }
        }

        if (amount == countMoneyInHeap(moneyBundle)) decreaseMoneyInCells(moneyBundle);
        else throw new RuntimeException("Не удается выдать запрошенную сумму");

        return moneyBundle;
    }

    @Override
    public long getBalance() {
        return countMoneyInHeap(banknoteCells);
    }

    private long countMoneyInHeap(Map<Banknote, Integer> bundle) {
        Integer balance = 0;
        for (Map.Entry<Banknote, Integer> entry : bundle.entrySet()) {
            balance += entry.getKey().getDenomination() * entry.getValue();
        }
        return balance;
    }

    private void decreaseMoneyInCells(Map<Banknote, Integer> bundle) {
        for (Map.Entry<Banknote, Integer> entry : bundle.entrySet()) {
            banknoteCells.put(entry.getKey(), banknoteCells.get(entry.getKey()) - entry.getValue());
        }
    }

}
