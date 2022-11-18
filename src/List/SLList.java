package List;
import List.*;
public class SLList {
    public Singly_Linked_List head;
    public SLList(int x){
        this.head = new Singly_Linked_List(x);
    }

    public static void main(String[] args) {
        SLList p = new SLList(10);
        SLList temp = new SLList(2);
        p.head.next = temp.head;
        SLList a = p;
        a.head.write(7);
        System.out.println(p.head.val);
    }
}
