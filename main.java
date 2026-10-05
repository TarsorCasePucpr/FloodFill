class Position {
    int linha;
    int coluna;

    public Position(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
}

class Node {
    Position pos;
    Node proximo;

    public Node(Position pos) {
        this.pos = pos;
        this.proximo = null;
    }
}

class Stack {
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

class Queue {
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
