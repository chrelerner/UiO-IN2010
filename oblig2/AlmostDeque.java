public class AlmostDeque {
    
    class Node {
        String s;
        Node prev = null;
        Node next = null;

        Node (String string) {
            s = string;
        }
    }

    Node start = null;
    Node end = null;

    int size = 0;

    public void push_back (String s) {
        Node temp = new Node(s);
        if (this.size == 0) {
            this.start = temp;
            this.end = temp;
        }
        else if (this.size == 1) {
            this.end = temp;
            this.start.next = temp;
            temp.prev = this.start;
        }
        else {
            this.end.next = temp;
            temp.prev = this.end;
            this.end = temp;
        }
        this.size ++;
    }

    public void push_front (String s) {
        Node temp = new Node(s);
        if (this.size == 0) {
            this.start = temp;
            this.end = temp;
        }
        else if (this.size == 1) {
            this.start = temp;
            this.start.next = this.end;
            this.end.prev = this.start;
        }
        else {
            temp.next = this.start;
            this.start = temp;
        }
        this.size ++;
    }

    public String remove_front () {
        Node temp = this.start;
        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            this.start = null;
            this.end = null;
        }
        else if (size == 2) {
            this.end.prev = null;
            this.start = this.end;
        }
        else {
            this.start = this.start.next;
            this.start.prev = null;
        }

        this.size --;
        return temp.s;
    }

    public String remove_back () {
        Node temp = this.end;
        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            this.start = null;
            this.end = null;
        }
        else if (size == 2) {
            this.start.next = null;
            this.end = this.start;
        }
        else {
            this.end = this.end.prev;
            this.end.next = null;
        }

        this.size --;
        return temp.s;
    }

    public int size () {
        return this.size;
    }


}
