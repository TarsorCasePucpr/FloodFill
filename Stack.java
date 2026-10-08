public class Stack {
    Node topo;

    public void push(Node n) {
        n.proximo = this.topo;
        this.topo = n;
    }

    public Node pop() {
        if (isEmpty()) return null;

        Node copy = this.topo;
        this.topo = this.topo.proximo;

        return copy;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}
