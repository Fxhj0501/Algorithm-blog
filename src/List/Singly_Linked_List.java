package List;
public class Singly_Linked_List {
    int val;
    Singly_Linked_List next;
    public Singly_Linked_List(int val){
        this.val = val;
        this.next = null;
    }
    public Singly_Linked_List(int val, Singly_Linked_List next){
        this.val = val;
        this.next = next;
    }

    public int size(){
        if(this.next==null)
            return 1;
        return 1+this.next.size();
    }
    public int get(int idx){
        if(idx == 0)
            return val;
        return next.get(idx-1);
    }

    public void write(int val){
        this.val = val;
    }
//    public static void main(String[] args) {
//        Singly_Linked_List head = new Singly_Linked_List(0);
//        //add new node behind the head
//        // head.next = new Singly_Linked_List(10);
//        //insert new node front of the head
//        head  = new Singly_Linked_List(10,head);
//        System.out.println(head.size());
//        System.out.println(head.get(1));
//    }
}
