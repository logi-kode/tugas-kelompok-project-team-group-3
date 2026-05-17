import java.util.LinkedList;


public class HashTable {

    private static final int DEFAULT_CAPACITY = 16;
    private LinkedList<Student>[] buckets;           
    private int size;                                 

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        buckets = new LinkedList[capacity];
        // Inisialisasi setiap bucket dengan LinkedList kosong
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        size = 0;
    }

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    private int hash(String nim) {
        int hashCode = 0;
        for (char c : nim.toCharArray()) {
            hashCode = (hashCode * 31 + c) % buckets.length;
        }
        return Math.abs(hashCode) % buckets.length;
    }

    public boolean insert(Student student) {
        int index = hash(student.getNim());
        LinkedList<Student> bucket = buckets[index];

        for (Student s : bucket) {
            if (s.getNim().equals(student.getNim())) {
                s.setName(student.getName());
                s.setGpa(student.getGpa());
                System.out.println("[UPDATE] Data mahasiswa NIM " + student.getNim() + " diperbarui.");
                return false;
            }
        }

        bucket.add(student);
        size++;
        return true; 
    }

    public Student search(String nim) {
        int index = hash(nim);
        LinkedList<Student> bucket = buckets[index];

        for (Student s : bucket) {
            if (s.getNim().equals(nim)) {
                return s;
            }
        }
        return null;
    }

    public boolean delete(String nim) {
        int index = hash(nim);
        LinkedList<Student> bucket = buckets[index];

        for (Student s : bucket) {
            if (s.getNim().equals(nim)) {
                bucket.remove(s);
                size--;
                return true;
            }
        }
        return false;
    }

    public void displayAll() {
        System.out.println("\n");
        System.out.println("ISI HASH TABLE (SEMUA DATA MAHASISWA)");

        boolean empty = true;
        for (int i = 0; i < buckets.length; i++) {
            if (!buckets[i].isEmpty()) {
                empty = false;
                System.out.printf("║ Bucket[%2d] → ", i);
                for (Student s : buckets[i]) {
                    System.out.println(s);
                    System.out.printf("║            → ", i);
                }
            }
        }

        if (empty) {
            System.out.println("(Tidak ada data)");
        }
        System.out.printf("Total mahasiswa tersimpan: %-33d | %n", size);
        System.out.println("");
    }

    public void displayStats() {
        int usedBuckets = 0;
        int maxChain = 0;
        int totalCollisions = 0;

        for (LinkedList<Student> bucket : buckets) {
            if (!bucket.isEmpty()) {
                usedBuckets++;
                if (bucket.size() > 1) {
                    totalCollisions += bucket.size() - 1;
                }
                maxChain = Math.max(maxChain, bucket.size());
            }
        }

        double loadFactor = (double) size / buckets.length;

        System.out.println("\nSTATISTIK HASH TABLE:");
        System.out.printf("- Total kapasitas bucket : %d%n", buckets.length);
        System.out.printf("- Bucket terpakai        : %d%n", usedBuckets);
        System.out.printf("- Total data             : %d%n", size);
        System.out.printf("- Load factor            : %.2f%n", loadFactor);
        System.out.printf("- Collision terjadi      : %d%n", totalCollisions);
        System.out.printf("- Rantai terpanjang      : %d%n", maxChain);
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }
}
