import java.util.Scanner;

public class Main {

    private static HashTable hashTable = new HashTable(16);
    private static BST bst = new BST();
    private static Scanner sc = new Scanner(System.in);

        private static void menuTambah() {
        System.out.println("\n── TAMBAH MAHASISWA ──");
        System.out.print("NIM   : "); String nim = sc.nextLine().trim();
        System.out.print("Nama  : "); String nama = sc.nextLine().trim();
        double ipk = readDouble("IPK (0.00 - 4.00): ");

        if (ipk < 0 || ipk > 4.0) {
            System.out.println("IPK harus antara 0.00 dan 4.00.");
            return;
        }

        Student student = new Student(nim, nama, ipk);
        boolean isNew = hashTable.insert(student);

        if (isNew) {
            bst.insert(student);
            System.out.println("Mahasiswa berhasil ditambahkan!");
            System.out.println("   " + student);
        }
    }

    private static void menuCari() {
        System.out.println("\n── CARI MAHASISWA ──");
        System.out.print("Masukkan NIM: ");
        String nim = sc.nextLine().trim();

        Student result = hashTable.search(nim);

        if (result != null) {
            System.out.println("Mahasiswa ditemukan:");
            System.out.println("   " + result);
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
        }
    }

    private static void menuHapus() {
        System.out.println("\n── HAPUS MAHASISWA ──");
        System.out.print("Masukkan NIM yang akan dihapus: ");
        String nim = sc.nextLine().trim();

        boolean deleted = hashTable.delete(nim);

        if (deleted) {
            bst.delete(nim);
            System.out.println("Mahasiswa dengan NIM " + nim + " berhasil dihapus.");
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
        }
    }

    private static void menuFilterIPK() {
        System.out.println("\n── FILTER MAHASISWA BY IPK ──");
        double threshold = readDouble("Tampilkan mahasiswa dengan IPK di atas: ");
        bst.displayAboveGpa(threshold);
    }

    private static void loadSampleData() {
        System.out.println("\n⏳ Memuat 15 data mahasiswa awal...");

        Student[] samples = {
            new Student("2021001", "Andi Pratama",       3.75),
            new Student("2021002", "Budi Santoso",       3.20),
            new Student("2021003", "Citra Dewi",         3.90),
            new Student("2021004", "Dani Kusuma",        2.85),
            new Student("2021005", "Eva Melani",         3.55),
            new Student("2021006", "Fajar Hidayat",      3.10),
            new Student("2021007", "Gita Rahayu",        3.80),
            new Student("2021008", "Hendra Wijaya",      2.60),
            new Student("2021009", "Indah Permata",      3.95),
            new Student("2021010", "Joko Susilo",        3.40),
            new Student("2021011", "Karina Sari",        2.75),
            new Student("2021012", "Leo Mahendra",       3.65),
            new Student("2021013", "Maya Anggraini",     3.30),
            new Student("2021014", "Niko Prasetyo",      2.50),
            new Student("2021015", "Olivia Wulandari",   3.85),
        };

        for (Student s : samples) {
            hashTable.insert(s);
            bst.insert(s);
        }

        System.out.println("✅ 15 data mahasiswa berhasil dimuat.\n");
    }

    private static void printMenu() {
        System.out.println("MENU UTAMA:");
        System.out.println("[1] Tambah Mahasiswa");
        System.out.println("[2] Cari Mahasiswa (by NIM)");
        System.out.println("[3] Hapus Mahasiswa");
        System.out.println("[4] Tampilkan Semua (Hash Table)");
        System.out.println("[5] Ranking IPK Tertinggi (BST)");
        System.out.println("[6] Ranking IPK Terendah (BST)");
        System.out.println("[7] Filter Mahasiswa by IPK");
        System.out.println("[8] Visualisasi Struktur BST");
        System.out.println("[9] Statistik Hash Table");
        System.out.println("[0] Keluar");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Masukkan angka bulat yang valid.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Masukkan angka desimal yang valid (gunakan titik, contoh: 3.75).");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("SISTEM MANAJEMEN DATA MAHASISWA");
        System.out.println("Menggunakan Hash Table + BST (Java)");

        loadSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Pilih menu: ");

            switch (choice) {
                case 1 -> menuTambah();
                case 2 -> menuCari();
                case 3 -> menuHapus();
                case 4 -> hashTable.displayAll();
                case 5 -> bst.displayDescending();
                case 6 -> bst.displayAscending();
                case 7 -> menuFilterIPK();
                case 8 -> bst.displayTree();
                case 9 -> hashTable.displayStats();
                case 0 -> {
                    System.out.println("\nTerima kasih! Program selesai.");
                    running = false;
                }
                default -> System.out.println("❌ Pilihan tidak valid. Coba lagi.");
            }
        }

        sc.close();
    }
}
