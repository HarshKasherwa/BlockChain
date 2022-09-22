import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node {

    private Node left;
    private Node right;
    private String hash;

    public Node(Node left, Node right, String hash) {
        this.left = left;
        this.right = right;
        this.hash = hash;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getHash() {
        return hash;
    }
}

public class MerkleTree {

    private Node merkle_root;

    public Node getMerkle_root() {
        return merkle_root;
    }

    public static Node generateTree(ArrayList<Transaction> txn) {

        HashCal hashObj = new HashCal();
        ArrayList<Node> childNodes = new ArrayList<>();

        for (Transaction t : txn) {
            childNodes.add(new Node(null, null, t.getTxnID()));
        }

        return buildTree(childNodes);
    }

    private static Node buildTree(ArrayList<Node> children) {

        HashCal hashObj = new HashCal();

        if (children.size() == 1)
            children.add(children.get(0));
        ArrayList<Node> parents = new ArrayList<>();

        while (children.size() != 1) {
            int index = 0, length = children.size();
            while (index < length) {
                Node leftChild = children.get(index);
                Node rightChild = null;

                if ((index + 1) < length) {
                    rightChild = children.get(index + 1);
                } else {
                    rightChild = new Node(null, null, leftChild.getHash());
                }

                String parentHash = hashObj.calculateHash(leftChild.getHash() + rightChild.getHash());
                parents.add(new Node(leftChild, rightChild, parentHash));
                index += 2;
            }
            children = parents;
            parents = new ArrayList<>();
        }
        return children.get(0);
    }

    private static void printLevelOrderTraversal(Node root) {
        if (root == null) {
            return;
        }

        if ((root.getLeft() == null && root.getRight() == null)) {
            System.out.println(root.getHash());
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node != null) {
                System.out.println(node.getHash());
            } else {
                System.out.println();
                if (!queue.isEmpty()) {
                    queue.add(null);
                }
            }

            if (node != null && node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node != null && node.getRight() != null) {
                queue.add(node.getRight());
            }

        }

    }

    public MerkleTree(ArrayList<Transaction> txn_list) {
        this.merkle_root = generateTree(txn_list);
    }
}