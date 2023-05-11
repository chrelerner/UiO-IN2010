import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Teque {

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Teque list = new Teque();
        for (int i = 0; i < N; i ++) {
            String[] line = br.readLine().split(" ");
            String cmd = line[0];
            String x = line[1];

            if (cmd.equals("push_back")) {
                list.push_back(Integer.parseInt(x));
            }

            else if (cmd.equals("push_front")) {
                list.push_front(Integer.parseInt(x));
            }

            else if (cmd.equals("push_middle")) {
                list.push_middle(Integer.parseInt(x));
            }

            else if (cmd.equals("get")) {
                list.get(Integer.parseInt(x));
            }
        }
    }

    class Node {
        int x;
        Node prev = null;
        Node next = null;

        Node (int number) {
            x = number;
        }
    }

    Node start = null;
    Node end = null;

    int size = 0;

    public void push_back (int x) {
        Node temp = new Node(x);
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

    public void push_front (int x) {
        Node temp = new Node(x);
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

    public void push_middle (int x) {
        Node node1 = new Node(x);
        if (this.size == 0) {
            this.start = node1;
            this.end = node1;
        }
        else if (this.size == 1) {
            this.end = node1;
            this.start.next = node1;
            node1.prev = this.start;
        }
        else if (this.size == 2) {
            this.start.next = node1;
            node1.prev = this.start;
            node1.next = this.end;
            this.end.prev = node1;
        }
        else if (this.size == 3) {
            Node temp = this.end.prev;
            temp.next = node1;
            node1.prev = temp;
            node1.next = this.end;
            this.end.prev = node1;
        }
        else {
    
            Node temp = this.start;

            for (int i = 0; i < ((this.size+1)/2)-1; i ++) {
                temp = temp.next;
            }

            Node temp2 = temp.next;

            temp.next = node1;
            node1.prev = temp;

            node1.next = temp2;
            temp2.prev = node1;
        }
        this.size ++;
    }

    public void get (int i) {
        Node temp = this.start;

        for (int j = 0; j < i; j++) {
            temp = temp.next;
        }
        System.out.println(temp.x);
    }
}
