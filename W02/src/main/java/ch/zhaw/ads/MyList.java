package ch.zhaw.ads;

import java.util.AbstractList;
import java.util.Objects;

public class MyList extends AbstractList {
    protected ListNode head = new ListNode(null);

    protected class ListNode {
        Object value;
        ListNode next = this, prev = this;

        ListNode(Object value) {
            this.value = value;
        }
    }

    @Override
    public boolean add(Object o) {
        ListNode newNode = new ListNode(o);
        ListNode last = head.prev;

        last.next = newNode;
        head.prev = newNode;
        newNode.next = head;
        newNode.prev = last;

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
}
