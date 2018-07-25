package ru.otus.l31;

import java.util.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MyArrayList<E> extends AbstractList<E> implements List<E> {

    private Object[] elementData;
    private int size;
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    public MyArrayList() {
        this.elementData = new Object[100];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;

    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException();

    }

    @Override
    public Iterator<E> iterator() {
        return new ListItr(0);

    }

    @Override
    public Object[] toArray() {

        return Arrays.copyOf(elementData, size);

    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException();

    }

    @Override
    public boolean add(E e) {
        elementData[size] = e;
        size = size + 1;
        return true;

    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException();

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();

    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new RuntimeException();

    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new RuntimeException();

    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();

    }

    @Override
    public void clear() {
        throw new RuntimeException();

    }

    @Override
    public E get(int index) {
        return (E) elementData[index];

    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;

    }

    @Override
    public void add(int index, E element) {
        throw new RuntimeException();

    }

    @Override
    public E remove(int index) {
        throw new RuntimeException();
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new RuntimeException();
    }

    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    public static void main(String[] args) {
        MyArrayList<String> myList = new MyArrayList<>();
        myList.add("a");
        myList.add("x");
        myList.add("g");
        myList.add("b");
        System.out.println(myList.size);
        MyArrayList<String> newList = new MyArrayList<>();
        newList.add("p");
        System.out.println(newList.size);

        Collections.copy(myList,newList);
        Collections.addAll(myList,"yo","hoho","hihi");

        for(String e: myList)
            System.out.println(e);

        Collections.sort(myList);

        for (String s : myList)
            System.out.println(s);

    }


    public void printList(MyArrayList<? extends E> myList1){
        for(E e: myList1)
            System.out.println(e);
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class ListItr implements ListIterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        // prevent creating a synthetic constructor
        ListItr(int index) {
            cursor = index;
        }
        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = elementData;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size && modCount == expectedModCount; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastRet = i - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}