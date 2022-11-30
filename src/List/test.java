package List;

import sun.tools.jconsole.inspector.XObject;

import java.util.Comparator;
import java.util.PriorityQueue;

class listnode{
    public int val;
    public listnode next;
    public listnode(int val){
        this.val = val;
        next = null;
    }
}
public class test {
    public static void main(String[] args) {
        listnode head = new listnode(1);
        listnode cur = head;
        cur.next = new listnode(3);
        cur = cur.next;
        cur.next = new listnode(2);
        cur = head;
//        while(cur!=null){
//            System.out.println(cur.val);
//            cur = cur.next;
//        }
        PriorityQueue<listnode> q = new PriorityQueue<>(new ListComparator());
        q.offer(head);
        listnode dummy = new listnode(-1);
        dummy.next = head;
        listnode a = dummy;
        while(!q.isEmpty()){
            a.next = q.poll();
            System.out.println(a.val);
            a = a.next;
            if(a.next!=null)
                q.offer(a.next);
        }

    }
}
class ListComparator implements Comparator<listnode>{
    public int compare(listnode a,listnode b){
        if(a.val>b.val)
            return 1;
        return -1;
    }
}
