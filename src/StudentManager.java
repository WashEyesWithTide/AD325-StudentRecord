import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentManager {

    private final TreeMap<Integer, Student> records = new TreeMap<>();

    public boolean addStudent(int id, String name, double gpa) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name must not be blank.");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
        if (records.containsKey(id)) {
            System.out.printf("Add failed: Student ID %d already exists.%n", id);
            return false;
        }
        records.put(id, new Student(id, name, gpa));
        return true;
    }

    public boolean deleteStudent(int id) {
        if (records.remove(id) != null) {
            return true;
        }
        System.out.printf("Delete failed: Student ID %d not found.%n", id);
        return false;
    }

    public boolean updateGpa(int id, double newGpa) {
        if (newGpa < 0.0 || newGpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
        Student student = records.get(id);
        if (student == null) {
            System.out.printf("Update failed: Student ID %d not found.%n", id);
            return false;
        }
        student.setGpa(newGpa);
        return true;
    }

    public void displayAll() {
        if (records.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("=== All Student Records (sorted by ID) ===");
        for (Map.Entry<Integer, Student> entry : records.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public List<Student> findStudentsAboveGpa(double threshold) {
        List<Student> result = new ArrayList<>();
        for (Student s : records.values()) {
            if (s.getGpa() > threshold) {
                result.add(s);
            }
        }
        return result;
    }

    public void displayStudentsAboveGpa(double threshold) {
        List<Student> result = findStudentsAboveGpa(threshold);
        System.out.printf("=== Students with GPA > %.1f ===%n", threshold);
        if (result.isEmpty()) {
            System.out.println("None found.");
        } else {
            result.forEach(System.out::println);
        }
    }
    public static void main(String[] args) {
        StudentManager mgr = new StudentManager();

        // add five students
        mgr.addStudent(103, "Carol White",  3.5);
        mgr.addStudent(101, "Alice Smith",  3.8);
        mgr.addStudent(105, "Eve Davis",    2.9);
        mgr.addStudent(102, "Bob Johnson",  3.2);
        mgr.addStudent(104, "David Brown",  3.9);

        // sorted display
        mgr.displayAll();

        // attempt duplicate add
        System.out.println("\nAttempting to add duplicate ID 101:");
        mgr.addStudent(101, "Duplicate", 4.0);

        // update GPA
        System.out.println("\nUpdating GPA for ID 105 from 2.9 to 3.6:");
        mgr.updateGpa(105, 3.6);
        mgr.displayAll();

        // delete record
        System.out.println("\nDeleting student ID 102:");
        mgr.deleteStudent(102);
        mgr.displayAll();

        // students above 3.0
        System.out.println();
        mgr.displayStudentsAboveGpa(3.0);

        // invalid operations
        System.out.println("\nAttempting to delete non-existent ID 999:");
        mgr.deleteStudent(999);

        System.out.println("\nAttempting to update non-existent ID 999:");
        mgr.updateGpa(999, 3.5);
    }
}