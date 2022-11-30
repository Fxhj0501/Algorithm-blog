# Content

[toc]

## Singly Linked List

### 1. Principle

#### 1.1 What is a singly link list like?

Linked list is a linear data structure,but the elements of it are not stored an a contiguous location(pointers).

#### 1.2 Why we use it?

- **Advantages**:

  - The size of arrays is fixed. So when we don't know the upper limit of arrays, we can't use it. But when we need a new linked list node, we can create a linked list node dynamically and connect it by using  pointer to connect with former nodes.

  - It's easy for us to insert/delete linked list node.

- Drawbacks:


### 2.Build and Basic Operation

#### 2.1 Build a Singly Linked List

#### 2.2 Get/Add/Delete

#### 2.3 findMiddle/isCycle

- In this type of question, we often use fast/slow pointers to solve it.

- [876.Middle of the Linked List](https://leetcode.cn/problems/middle-of-the-linked-list/)

```java
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
    public ListNode middleNode(ListNode head) {
        ListNode dummy = new ListNode(-1,head);
        ListNode fast = head;
        ListNode slow = head;
        while(fast !=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
```

- [141.Linked List Cycle](https://leetcode.cn/problems/linked-list-cycle/)

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow)
                return true;
        }
        return false;
    }
}
```

- [142. Linked List Cycle II](https://leetcode.cn/problems/linked-list-cycle-ii/)
  - Let the list have `a+b` nodes in total, in which there are ` a` nodes from the head of the list to the cycle entry (excluding the list entry node), and there are `b` nodes in the list cycle. And we use `f` express the distance of faster pointer, `s` for slow pointer.
  - From the definition of fast and slow pointers, we know that `f=2s`
  - When fast pointer meets slow pointer, that's mean faster pointer exceed slow pointer `n` cycles. So `f=s+nb`
  - From `f=2s` and `f=s+nb`, we konw that `s=nb`
  - So when slow pointer move `a` steps again, it will appear at the entrance of the cycle. So let's start a new pointer from the head. When it meets the slow pointer, we will find the position of the cycle entrance.

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast!=null && fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                fast = head;
                while(fast!=slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }
}
```

#### 2.4 Merge

[23. Merge k Sorted Lists](https://leetcode.cn/problems/merge-k-sorted-lists/)

```java
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
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        PriorityQueue<ListNode> q = new PriorityQueue<>(new ListnodeCom());
        for(ListNode list:lists){
            if(list!= null)
                q.offer(list);
        }
        while(!q.isEmpty()){
            cur.next = q.poll();
            cur = cur.next;
            if(cur.next!=null)
                q.offer(cur.next);
        }
        return dummy.next;
    }
    public class ListnodeCom implements Comparator<ListNode>{
        public int compare(ListNode a,ListNode b){
            if(a.val>b.val)
                return 1;
            return -1;
        }
    }
}
```

#### 2.6 isPalindrome: findMiddle+reverse

[234. Palindrome Linked List](https://leetcode.cn/problems/palindrome-linked-list/)

```java
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
    public boolean isPalindrome(ListNode head) {
        ListNode slow=head;
        ListNode fast = head;
        while(fast!=null&&fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = null;
        if(fast == null)
            mid = reverse(slow);
        else
            mid = reverse(slow.next);
        ListNode p = head;
        ListNode a = mid;
        while(a!=null){
            if(a.val!=p.val)
                return false;
            p = p.next;
            a = a.next;
        }
        return true;
    }
    public ListNode reverse(ListNode head){
        ListNode cur = head;
        ListNode pre = null;
        while(cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
```

