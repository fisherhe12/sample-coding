package com.github.fisherhe12.algorithm;

/**
 * 找出两个链表的相交点(LeeCode-160)
 *
 * @author Yu.He
 */
public class LinkedListIntersection {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;

        //if a and b have different lengths, then it will stop the loop after second iteration
        while (a != b) {
            //for the first iteration, it'll just reset the pointer to the head of another linkedlist
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }
}
