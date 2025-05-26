import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ManagementSystem {
    //initialize the instance model for each class of data structures.
    private LinkedList itemList;
    private Stack undoStack;
    private Queue urgentQueue;
    private Queue normalQueue;
    private BST searchTree;
    private Scanner scanner;
    //constactor of the class
    public ManagementSystem() {
        itemList = new LinkedList();
        undoStack = new Stack();
        urgentQueue = new Queue();
        normalQueue = new Queue();
        searchTree = new BST();
        scanner = new Scanner(System.in);
    }
    //run method to call in the main that calls all the the methods
    public void run() {
        System.out.println("=== ITEM MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to the Item Management System!");

        while (true) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    searchByCategory();
                    break;
                case 5:
                    displayAllItems();
                    break;
                case 6:
                    displayPriorityQueues();
                    break;
                case 7:
                    processUrgentItems();
                    break;
                case 8:
                    undoLastOperation();
                    break;
                case 9:
                    exportToJson();
                    break;
                case 10:
                    displayBSTInOrder();
                    break;
                case 0:
                    System.out.println("Thank you for using Item Management System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    //display the menu
    private void displayMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Search by Name");
        System.out.println("4. Search by Category");
        System.out.println("5. Display All Items");
        System.out.println("6. Display Priority Queues");
        System.out.println("7. Process Urgent Items");
        System.out.println("8. Undo Last Operation");
        System.out.println("9. Export to JSON");
        System.out.println("10. Display Items (BST Order)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addItem() {
        System.out.println("\n=== ADD ITEM ===");
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Item name cannot be empty!");
            return;
        }

        // Check if item already exists
        if (searchTree.search(name) != null) {
            System.out.println("Item with this name already exists!");
            return;
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter priority (urgent/normal): ");
        String priority = scanner.nextLine().trim().toLowerCase();

        if (!priority.equals("urgent") && !priority.equals("normal")) {
            priority = "normal";
            System.out.println("Invalid priority. Set to 'normal' by default.");
        }

        Item newItem = new Item(name, description, category, priority);

        // Add to linked list
        itemList.add(newItem);

        // Add to BST for efficient searching
        searchTree.insert(newItem);

        // Add to appropriate priority queue
        if (priority.equals("urgent")) {
            urgentQueue.enqueue(newItem);
        } else {
            normalQueue.enqueue(newItem);
        }

        // Push to undo stack
        undoStack.push("ADD", newItem);

        System.out.println("Item added successfully!");
    }

    private void removeItem() {
        System.out.println("\n=== REMOVE ITEM ===");
        System.out.print("Enter item name to remove: ");
        String name = scanner.nextLine().trim();

        Item item = searchTree.search(name);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        // Remove from linked list
        if (itemList.remove(name)) {
            // Remove from BST
            searchTree.delete(name);

            // Push to undo stack
            undoStack.push("REMOVE", item);

            System.out.println("Item removed successfully!");
        } else {
            System.out.println("Failed to remove item!");
        }
    }

    private void searchByName() {
        System.out.println("\n=== SEARCH BY NAME ===");
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();

        Item item = searchTree.search(name);
        if (item != null) {
            System.out.println("Item found:");
            System.out.println(item);
        } else {
            System.out.println("Item not found!");
        }
    }

    private void searchByCategory() {
        System.out.println("\n=== SEARCH BY CATEGORY ===");
        System.out.print("Enter category: ");
        String category = scanner.nextLine().trim();

        Item[] items = itemList.toArray();
        boolean found = false;

        System.out.println("Items in category '" + category + "':");
        for (Item item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                System.out.println(item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items found in this category!");
        }
    }

    private void displayAllItems() {
        System.out.println("\n=== ALL ITEMS ===");
        if (itemList.isEmpty()) {
            System.out.println("No items in the system.");
        } else {
            itemList.display();
        }
    }

    private void displayPriorityQueues() {
        System.out.println("\n=== PRIORITY QUEUES ===");
        System.out.println("\nUrgent Items Queue:");
        urgentQueue.display();

        System.out.println("\nNormal Items Queue:");
        normalQueue.display();
    }

    private void processUrgentItems() {
        System.out.println("\n=== PROCESS URGENT ITEMS ===");
        if (urgentQueue.isEmpty()) {
            System.out.println("No urgent items to process.");
            return;
        }

        System.out.println("Processing urgent items in order:");
        while (!urgentQueue.isEmpty()) {
            Item item = urgentQueue.dequeue();
            System.out.println("Processing: " + item);
        }
        System.out.println("All urgent items processed!");
    }

    private void undoLastOperation() {
        System.out.println("\n=== UNDO LAST OPERATION ===");
        if (undoStack.isEmpty()) {
            System.out.println("No operations to undo!");
            return;
        }

        Stack.StackNode lastOp = undoStack.pop();
        if (lastOp.operation.equals("ADD")) {
            // Undo add operation by removing the item
            String name = lastOp.item.getName();
            itemList.remove(name);
            searchTree.delete(name);
            System.out.println("Undone: Add operation for item '" + name + "'");
        } else if (lastOp.operation.equals("REMOVE")) {
            // Undo remove operation by adding the item back
            Item item = lastOp.item;
            itemList.add(item);
            searchTree.insert(item);
            if (item.getPriority().equals("urgent")) {
                urgentQueue.enqueue(item);
            } else {
                normalQueue.enqueue(item);
            }
            System.out.println("Undone: Remove operation for item '" + item.getName() + "'");
        }
    }

    private void exportToJson() {
        System.out.println("\n=== EXPORT TO JSON ===");
        System.out.print("Enter filename (without .json extension): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            System.out.println("invalid name???we will name it items");
            filename = "items";
        }

        try {
            FileWriter writer = new FileWriter(filename + ".json");
            writer.write("{\n");
            writer.write("  \"items\": [\n");

            Item[] items = itemList.toArray();
            for (int i = 0; i < items.length; i++) {
                writer.write("  " + items[i].toJson());
                if (i < items.length - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  ],\n");
            writer.write("  \"total_items\": " + items.length + ",\n");
            writer.write("}\n");

            writer.close();
            System.out.println("Items exported successfully to " + filename + ".json");
        } catch (IOException e) {
            System.out.println("Error exporting to JSON: " + e.getMessage());
        }
    }

    private void displayBSTInOrder() {
        System.out.println("\n=== ITEMS IN BST ORDER ===");
        System.out.println("Items sorted alphabetically by name:");
        searchTree.inorderTraversal();
    }
    }