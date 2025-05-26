class LinkedList {
    private Node head;
    private int size;

    private class Node {
        Item data;
        Node next;

        Node(Item data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void add(Item item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean remove(String itemName) {
        if (head == null) return false;

        if (head.data.getName().equals(itemName)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null && !current.next.data.getName().equals(itemName)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }
        return false;
    }

    public Item[] toArray() {
        Item[] result = new Item[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            result[index++] = current.data;
            current = current.next;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public void display() {
        if (head == null) {
            System.out.println("No items in the list.");
            return;
        }

        Node current = head;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.data);
            current = current.next;
            index++;
        }
    }
}