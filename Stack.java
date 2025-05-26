class Stack {
    private StackNode top;
    private int size;

     class StackNode {
        String operation;
        Item item;
        StackNode next;

        StackNode(String operation, Item item) {
            this.operation = operation;
            this.item = item;
            this.next = null;
        }
    }

    public Stack() {
        top = null;
        size = 0;
    }

    public void push(String operation, Item item) {
        StackNode newNode = new StackNode(operation, item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public StackNode pop() {
        if (isEmpty()) return null;
        StackNode temp = top;
        top = top.next;
        size--;
        return temp;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}