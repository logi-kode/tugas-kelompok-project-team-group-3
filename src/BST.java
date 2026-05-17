public class BST {
    private static class BSTNode {
        Student data;
        BSTNode left, right;

        BSTNode(Student data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;

    public BST() {
        root = null;
    }

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private BSTNode insertRec(BSTNode node, Student student) {
        if (node == null) {
            return new BSTNode(student);
        }

        // Bandingkan IPK untuk menentukan arah
        if (student.getGpa() <= node.data.getGpa()) {
            node.left = insertRec(node.left, student);   // IPK lebih kecil → kiri
        } else {
            node.right = insertRec(node.right, student); // IPK lebih besar → kanan
        }

        return node;
    }

    public void delete(String nim) {
        root = deleteRec(root, nim);
    }

    private BSTNode deleteRec(BSTNode node, String nim) {
        if (node == null) return null;

        if (node.data.getNim().equals(nim)) {
            // Case 1: Node adalah daun (tidak punya anak)
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: Node punya satu anak
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Case 3: Node punya dua anak
            // Ganti dengan successor terkecil dari subtree kanan (inorder successor)
            BSTNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getNim());
        } else {
            node.left  = deleteRec(node.left, nim);
            node.right = deleteRec(node.right, nim);
        }

        return node;
    }

    /** Mencari node dengan IPK terkecil (paling kiri) */
    private BSTNode findMin(BSTNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void displayAscending() {
        System.out.println("\n");
        System.out.println("RANKING MAHASISWA (IPK Terkecil → Terbesar):");

        if (root == null) {
            System.out.println("(Tidak ada data)");
        } else {
            int[] rank = {1};
            inorderAsc(root, rank);
        }
    }

    private void inorderAsc(BSTNode node, int[] rank) {
        if (node == null) return;
        inorderAsc(node.left, rank);
        System.out.printf("Rank %-3d  %s%n", rank[0]++, node.data);
        inorderAsc(node.right, rank);
    }

    public void displayDescending() {
        System.out.println("\n");
        System.out.println("RANKING MAHASISWA (IPK Terbesar → Terkecil)");

        if (root == null) {
            System.out.println("(Tidak ada data)");
        } else {
            int[] rank = {1};
            inorderDesc(root, rank);
        }
    }

    private void inorderDesc(BSTNode node, int[] rank) {
        if (node == null) return;
        inorderDesc(node.right, rank);
        System.out.printf("║  Rank %-3d  %s%n", rank[0]++, node.data);
        inorderDesc(node.left, rank);
    }

    public void displayAboveGpa(double minGpa) {
        System.out.printf("MAHASISWA DENGAN IPK DI ATAS %.2f%n", minGpa);

        boolean[] found = {false};
        searchAboveGpa(root, minGpa, found);

        if (!found[0]) {
            System.out.println("(Tidak ada mahasiswa dengan IPK di atas threshold)");
        }
    }

    private void searchAboveGpa(BSTNode node, double minGpa, boolean[] found) {
        if (node == null) return;
        searchAboveGpa(node.left, minGpa, found);
        if (node.data.getGpa() > minGpa) {
            System.out.println("║  " + node.data);
            found[0] = true;
        }
        searchAboveGpa(node.right, minGpa, found);
    }

    public void displayTree() {
        System.out.println("\n🌳 VISUALISASI STRUKTUR BST (berdasarkan IPK):");
        if (root == null) {
            System.out.println("   (Tree kosong)");
        } else {
            printTree(root, "", true);
        }
    }

    private void printTree(BSTNode node, String prefix, boolean isLeft) {
        if (node == null) return;
        System.out.printf("%s%s[IPK: %.2f | %s]%n",
                prefix,
                isLeft ? "├── " : "└── ",
                node.data.getGpa(),
                node.data.getName());
        printTree(node.left,  prefix + (isLeft ? "│   " : "    "), true);
        printTree(node.right, prefix + (isLeft ? "│   " : "    "), false);
    }

    public boolean isEmpty() { return root == null; }
}
