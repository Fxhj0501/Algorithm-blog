//package List;
//import List.*;
//public class SLList {
//    public Singly_Linked_List head;
//    public SLList(int x){
//        this.head = new Singly_Linked_List(x);
//    }
//
//    public static void main(String[] args) {
//        SLList p = new SLList(10);
//        System.out.println(p.head.val);
//    }
//}
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = null;
        int c = 0;
        while(l1 !=null || l2!= null){
            if(l1==null && l2 !=null){
                res.val = (l2.val+c)%10;
                c = l2.val+c>=10?1:0;
                res.next = new ListNode();
                res = res.next;
                continue;
            }
            if(l1!=null && l2 ==null){
                res.val = (l1.val+c)%10;
                c = l1.val+c>=10?1:0;
                res.next = new ListNode();
                res = res.next;
                continue;
            }
            if(l1!=null && l2 !=null){
                res.val = (l2.val+c+l1.val)%10;
                c = l2.val+c+l1.val>=10?1:0;
                res.next = new ListNode();
                res = res.next;
            }
        }
        if(c>0)
            res.val = 1;
        return res;
    }
}