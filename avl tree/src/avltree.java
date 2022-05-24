
import java.util.Scanner;
class avltree
{
    Node root;
    int height(Node N)
    {
        if (N == null)
            return 0;
        return N.height;
    }
    int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
    Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        return x;
    }
    Node leftRotate(Node x)
    {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        return y;
    }
    int getBalance(Node N)
    {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }
    public boolean searchElement(int element)
    {
        return searchElement(root, element);
    }
    private boolean searchElement(Node head, int element)
    {
        boolean check = false;
        while ((head != null) && !check)
        {
            int headElement = head.key;
            if (element < headElement)
                head = head.left;
            else if (element > headElement)
                head = head.right;
            else
            {
                check = true;
                break;
            }
            check = searchElement(head, element);
        }
        return check;
    }
    Node insert(Node node, int key)
    {
        if (node == null)
            return (new Node(key));
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;
        node.height = 1 + max(height(node.left),
                height(node.right));
        int balance = getBalance(node);
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);
        if (balance > 1 && key > node.left.key)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    Node minValueNode(Node node)
    {
        Node current = node;
        while (current.left != null)
            current = current.left;

        return current;
    }
    Node deleteNode(Node root, int key)
    {
        if (root == null)
            return root;
        if (key < root.key)
            root.left = deleteNode(root.left, key);
        else if (key > root.key)
            root.right = deleteNode(root.right, key);
        else
        {
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else
                    root = temp;
            }
            else
            {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteNode(root.right, temp.key);
            }
        }
        if (root == null)
            return root;
        root.height = max(height(root.left), height(root.right)) + 1;
        int balance = getBalance(root);
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    void printingtree(Node node)
    {
        if (node != null)
        {
            System.out.print(node.key + " ");
            printingtree(node.left);
            printingtree(node.right);
        }
    }
    public static void main(String[] args) {
        avltree tree = new avltree();
        Scanner sc = new Scanner(System.in);
        String end="yes";
        while (end.equals("yes")) {
            System.out.println("Select the number of the operation:");
            System.out.println("1. Insert a node");
            System.out.println("2. Search a node");
            System.out.println("3. Delete a node");
            System.out.println("4. Height of the tree");
            int number = sc.nextInt();
            switch (number) {
                case 1:
                    System.out.println("Please enter an element to insert in AVL Tree");
                    tree.root = tree.insert(tree.root,sc.nextInt());
                    tree.printingtree(tree.root);
                    break;
                case 2:
                    boolean verify;
                    System.out.println("Enter integer element to search");
                    verify= tree.searchElement(sc.nextInt());
                    if(verify==true)
                    {
                        System.out.println("found");
                    }
                    else
                    {
                        System.out.println("not found");
                    }
                    break;
                case 3:
                    System.out.println("Enter integer element to delete");
                    tree.root = tree.deleteNode(tree.root,sc.nextInt());
                    tree.printingtree(tree.root);
                    break;
                case 4:
                    System.out.println("Height:" +tree.height(tree.root));
                    break;
            }
            System.out.println();
            System.out.println("if you want to complete operations write yes ");
            end = sc.next();
            System.out.println(end);
        }
    }
}


