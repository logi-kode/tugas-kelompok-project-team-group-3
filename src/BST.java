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
            node.left = insertRec(node.left, student);   // IPK lebih kecil -> kiri
        } else {
            node.right = insertRec(node.right, student); // IPK lebih besar -> kanan
        }

        return node;
    }

    public void delete(String nim) {
        root = deleteRec(root, nim);
    }

    private BSTNode deleteRec(BSTNode node, String nim) {
        if (node == null) {
            return null;
        }

        if (node.data.getNim().equals(nim)) {
            // Case 1: Node adalah daun (tidak punya anak)
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Node punya satu anak
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            // Case 3: Node punya dua anak
            // Ganti dengan successor terkecil dari subtree kanan (inorder successor)
            BSTNode successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getNim());
        } else {
            node.left = deleteRec(node.left, nim);
            node.right = deleteRec(node.right, nim);
        }

        return node;
    }

    /** Mencari node dengan IPK terkecil (paling kiri) */
    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void displayAscending() {
        System.out.println();
        System.out.println("RANKING MAHASISWA (IPK Terkecil ke Terbesar):");
        printRankingTableHeader();

        if (root == null) {
            printEmptyRankingRow();
        } else {
            int[] rank = {1};
            inorderAsc(root, rank);
        }

        printRankingTableLine();
    }

    private void inorderAsc(BSTNode node, int[] rank) {
        if (node == null) {
            return;
        }
        inorderAsc(node.left, rank);
        printRankingRow(rank[0]++, node.data);
        inorderAsc(node.right, rank);
    }

    public void displayDescending() {
        System.out.println();
        System.out.println("RANKING MAHASISWA (IPK Terbesar ke Terkecil)");
        printRankingTableHeader();

        if (root == null) {
            printEmptyRankingRow();
        } else {
            int[] rank = {1};
            inorderDesc(root, rank);
        }

        printRankingTableLine();
    }

    private void inorderDesc(BSTNode node, int[] rank) {
        if (node == null) {
            return;
        }
        inorderDesc(node.right, rank);
        printRankingRow(rank[0]++, node.data);
        inorderDesc(node.left, rank);
    }

    public void displayAboveGpa(double minGpa) {
        System.out.printf("MAHASISWA DENGAN IPK DI ATAS %.2f%n", minGpa);
        printStudentTableHeader();

        boolean[] found = {false};
        searchAboveGpa(root, minGpa, found);

        if (!found[0]) {
            printEmptyStudentRow();
        }

        printStudentTableLine();
    }

    private void searchAboveGpa(BSTNode node, double minGpa, boolean[] found) {
        if (node == null) {
            return;
        }
        searchAboveGpa(node.left, minGpa, found);
        if (node.data.getGpa() > minGpa) {
            printStudentRow(node.data);
            found[0] = true;
        }
        searchAboveGpa(node.right, minGpa, found);
    }

    private void printRankingTableHeader() {
        printRankingTableLine();
        System.out.println("| Rank | NIM        | Nama                 | IPK  |");
        printRankingTableLine();
    }

    private void printRankingTableLine() {
        System.out.println("+------+------------+----------------------+------+");
    }

    private void printRankingRow(int rank, Student student) {
        System.out.printf("| %4d | %-10s | %-20s | %4.2f |%n",
                rank, fit(student.getNim(), 10), fit(student.getName(), 20), student.getGpa());
    }

    private void printEmptyRankingRow() {
        System.out.println("|  --  | --         | Tidak ada data       | --   |");
    }

    private void printStudentTableHeader() {
        printStudentTableLine();
        System.out.println("| NIM        | Nama                 | IPK  |");
        printStudentTableLine();
    }

    private void printStudentTableLine() {
        System.out.println("+------------+----------------------+------+");
    }

    private void printStudentRow(Student student) {
        System.out.printf("| %-10s | %-20s | %4.2f |%n",
                fit(student.getNim(), 10), fit(student.getName(), 20), student.getGpa());
    }

    private void printEmptyStudentRow() {
        System.out.println("| --         | Tidak ada data       | --   |");
    }

    private String fit(String text, int width) {
        if (text == null) {
            return "";
        }
        if (text.length() <= width) {
            return text;
        }
        return text.substring(0, width - 3) + "...";
    }

    public void displayTree() {
        System.out.println("\nVISUALISASI STRUKTUR BST (berdasarkan IPK):");
        if (root == null) {
            System.out.println("   (Tree kosong)");
        } else {
            printTree(root, "", true);
        }
    }

    private void printTree(BSTNode node, String prefix, boolean isLeft) {
        if (node == null) {
            return;
        }
        System.out.printf("%s%s[IPK: %.2f | %s]%n",
                prefix,
                isLeft ? "+-- " : "\\-- ",
                node.data.getGpa(),
                node.data.getName());
        printTree(node.left, prefix + (isLeft ? "|   " : "    "), true);
        printTree(node.right, prefix + (isLeft ? "|   " : "    "), false);
    }

    public boolean isEmpty() {
        return root == null;
    }
}
