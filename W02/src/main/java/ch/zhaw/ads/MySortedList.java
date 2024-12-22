package ch.zhaw.ads;

import java.util.AbstractList;
import java.util.Objects;

public class MySortedList extends AbstractList {
    protected ListNode head = new ListNode(null);

    protected class ListNode {
        Object value;
        ListNode next = this, prev = this;

        ListNode(Object value) {
            this.value = value;
        }
    }

    @Override
    public boolean add(Object val){
        ListNode newNode = new ListNode(val);
        ListNode seekHead = this.head.next;

        while (seekHead != this.head){
            int compare = seekHead.value == null ? 1 : compare(seekHead.value, val);

            if (compare > 0) {
                break;
            }

            seekHead = seekHead.next;
        }

        newNode.prev = seekHead.prev;
        newNode.next = seekHead;
        seekHead.prev.next = newNode;
        seekHead.prev = newNode;

        return true;
    }

    @Override
    public boolean remove(Object obj) {
        ListNode seekHead = this.head.next;

        while (seekHead != this.head) {
            if (Objects.equals(seekHead.value, obj)) {
                seekHead.prev.next = seekHead.next;
                seekHead.next.prev = seekHead.prev;
                return true;
            }

            seekHead = seekHead.next;
        }

        return false;
    }

    @Override
    public Object get(int pos) {
        ListNode seekHead = this.head;

        // Skip initial null-node
        for (int i = -1; i < pos; i++) {
            seekHead = seekHead.next;

            if (seekHead == this.head) {
                throw new IndexOutOfBoundsException();
            }
        }

        return seekHead.value;
    }

    @Override
    public boolean isEmpty() {
        return head.prev == head;
    }

    @Override
    public int size() {
        ListNode seekHead = this.head;

        int count = 0;
            while (seekHead.next != this.head) {
            seekHead = seekHead.next;
            count++;
        }

        return count;
    }

    @Override
    public void clear() {
        head.next = head;
        head.prev = head;
    }

    protected int compare(Object o1, Object o2) {
        if (o1 instanceof String && o2 instanceof String) {
            return ((String) o1).compareTo((String) o2);
        }

        if (o1 instanceof Character && o2 instanceof Character) {
            return ((Character) o1).compareTo((Character) o2);
        }

        throw new IllegalStateException("Unable to compare other kind of objects");
    }
}
