class BST {
    private TreeNode root;

    private class TreeNode {
        Item data;
        TreeNode left;
        TreeNode right;

        TreeNode(Item data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public BST() {
        root = null;
    }

    public void insert(Item item) {
        root = insertRec(root, item);
    }

    private TreeNode insertRec(TreeNode root, Item item) {
        if (root == null) {
            root = new TreeNode(item);
            return root;
        }

        if (item.getName().compareToIgnoreCase(root.data.getName()) < 0) {
            root.left = insertRec(root.left, item);
        } else if (item.getName().compareToIgnoreCase(root.data.getName()) > 0) {
            root.right = insertRec(root.right, item);
        }

        return root;
    }

    public Item search(String name) {
        return searchRec(root, name);
    }

    private Item searchRec(TreeNode root, String name) {
        if (root == null || root.data.getName().equalsIgnoreCase(name)) {
            return root != null ? root.data : null;
        }

        if (name.compareToIgnoreCase(root.data.getName()) < 0) {
            return searchRec(root.left, name);
        }

        return searchRec(root.right, name);
    }

    public void delete(String name) {
        root = deleteRec(root, name);
    }

    private TreeNode deleteRec(TreeNode root, String name) {
        if (root == null) return root;

        if (name.compareToIgnoreCase(root.data.getName()) < 0) {
            root.left = deleteRec(root.left, name);
        } else if (name.compareToIgnoreCase(root.data.getName()) > 0) {
            root.right = deleteRec(root.right, name);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getName());
        }

        return root;
    }

    private Item minValue(TreeNode root) {
        Item minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    public void inorderTraversal() {
        inorderRec(root);
    }

    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.data);
            inorderRec(root.right);
        }
    }
}