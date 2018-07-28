package ru.otus.l31;

import java.util.Collections;
import java.util.List;

public class MyArrayListTest {


    public static void main(String[] args) {
        MyArrayList<String> myList = new MyArrayList<>();
        myList.add("a");
        myList.add("x");
        myList.add("g");
        myList.add("b");
        System.out.println(myList.size());
        MyArrayList<String> newList = new MyArrayList<>();
        newList.add("p");
        System.out.println(newList.size());

        Collections.copy(myList,newList);
        Collections.addAll(myList,"yo","hoho","hihi");

        for(String e: myList)
            System.out.println(e);

        Collections.sort(myList);

        List<Integer> list = new MyArrayList<>();
        for(int i=0; i < 2000000; i++){
            list.add(i);
        }

        for (Integer i: list)
            System.out.println(i);

        for (String s : myList)
            System.out.println(s);


    }

}
