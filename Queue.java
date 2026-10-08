public class Queue {
    Node start;
    Node end;

    public void enqueue(Node n) {
        if (this.end == null) {
            this.start = n;
            this.end = n;
        } else {
            this.end.proximo = n;
            this.end = n;
        }
    }

    public Node dequeue() {
        if (isEmpty()) return null;

        Node copy = this.start;
        this.start = this.start.proximo;
        if (this.start == null) {
            this.end = null;
        }

        return copy;
    }

    public boolean isEmpty() {
        return this.start == null;
    }
}
