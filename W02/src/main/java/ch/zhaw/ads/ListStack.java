package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.List;

public class ListStack implements Stack {
    private final List<Object> list = new ArrayList<>();

    @Override
    public void push(Object x) throws StackOverflowError {
        list.add(x);
    }

    @Override
    public Object pop() {
        if (list.size() == 0) {
            return null;
        }

        return list.remove(list.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Object peek() {
        return list.get(list.size() - 1);
    }

    @Override
    public void removeAll() {
        list.clear();
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
