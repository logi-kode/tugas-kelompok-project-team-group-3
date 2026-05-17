public class Student {
    private String nim;
    private String name;
    private double gpa;

    public Student(String nim, String name, double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
    }

    public String getNim()    { return nim; }
    public String getName()   { return name; }
    public double getGpa()    { return gpa; }

    public void setName(String name) { this.name = name; }
    public void setGpa(double gpa)   { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("NIM: %-10s | Nama: %-20s | IPK: %.2f", nim, name, gpa);
    }
}
