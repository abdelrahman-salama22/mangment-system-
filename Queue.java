class Queue {
    private QueueNode front;
    private QueueNode rear;
    private int size;

    private class QueueNode {
        Item data;
        QueueNode next;

        QueueNode(Item data) {
            this.data = data;
            this.next = null;
        }
    }

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }

    public void enqueue(Item item) {
        QueueNode newNode = new QueueNode(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) return null;
        Item item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        QueueNode current = front;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.data);
            current = current.next;
            index++;
        }
    }
}