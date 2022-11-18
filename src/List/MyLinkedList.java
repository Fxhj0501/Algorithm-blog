package List;

class MyLinkedList {
    public Node head;
    int size;
    public MyLinkedList() {
        this.size = 0;
        this.head = new Node(-1);
    }

    public int get(int index) {
        if(index >= size || index<0)
            return -1;
        Node temp = head;
        while(index>=0){
            temp = temp.next;
            index--;
        }
        return temp.val;
    }

    public void addAtHead(int val) {
        addAtIndex(0,val);
    }

    public void addAtTail(int val) {
        addAtIndex(size,val);
    }

    public void addAtIndex(int index, int val) {
        if(index>size)
            return ;
        Node temp = head;
        for(int i=0;i<index;i++)
            temp = temp.next;
        Node store = new Node(val);
        store.next = temp.next;
        temp.next = store;
        size++;
    }

    public void deleteAtIndex(int index) {
        if(index>=size || index<0)
            return;
        Node temp = head;
        for(int i=0;i<index-1;i++){
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return;
    }

    public void browse(){
        Node temp = head;
        while(temp!=null){
            System.out.println(temp.val);
            temp = temp.next;
        }
    }
}

class Node{
    int val;
    Node next;
    public Node(int val){
        this.val = val;
    }

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        myLinkedList.addAtTail(3);
        myLinkedList.addAtIndex(1,2);
        //myLinkedList.browse();
        int a= myLinkedList.get(0);
        System.out.println(a);
    }
}
/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */