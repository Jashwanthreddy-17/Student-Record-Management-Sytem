import java.util.*;

// Patient Class
class Patient {
    int id;
    String name;
    int age;
    String disease;
    int priority;

    Patient(int id, String name, int age, String disease, int priority) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.priority = priority;
    }

    public String toString() {
        return "ID: " + id + " Name: " + name + " Age: " + age + " Disease: " + disease + " Priority: " + priority;
    }
}

// Linked List for Medical History
class Node {
    String record;
    Node next;

    Node(String record) {
        this.record = record;
    }
}

class MedicalHistory {

    Node head;

    void addRecord(String rec) {

        Node newNode = new Node(rec);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;
        while (temp.next != null)
            temp = temp.next;

        temp.next = newNode;
    }

    void displayHistory() {

        Node temp = head;

        if (temp == null) {
            System.out.println("No history records");
            return;
        }

        while (temp != null) {
            System.out.println(temp.record);
            temp = temp.next;
        }
    }
}

public class HealthDashboard {

    static Scanner sc = new Scanner(System.in);

    static Patient[] patients = new Patient[100];
    static int count = 0;

    static HashMap<Integer, Patient> patientMap = new HashMap<>();

    static Queue<Patient> appointmentQueue = new LinkedList<>();

    static PriorityQueue<Patient> emergencyQueue =
            new PriorityQueue<>((a, b) -> b.priority - a.priority);

    static Stack<String> actionStack = new Stack<>();

    static MedicalHistory history = new MedicalHistory();

    // Linear Search
    static Patient searchPatient(int id) {

        for (int i = 0; i < count; i++) {
            if (patients[i].id == id)
                return patients[i];
        }

        return null;
    }

    // Bubble Sort
    static void sortPatients() {

        for (int i = 0; i < count - 1; i++) {

            for (int j = 0; j < count - i - 1; j++) {

                if (patients[j].id > patients[j + 1].id) {

                    Patient temp = patients[j];
                    patients[j] = patients[j + 1];
                    patients[j + 1] = temp;
                }
            }
        }
    }

    // Display Patients
    static void displayPatients() {

        if (count == 0) {
            System.out.println("No patients available");
            return;
        }

        for (int i = 0; i < count; i++)
            System.out.println(patients[i]);
    }

    // Display Appointments
    static void displayAppointments() {

        if (appointmentQueue.isEmpty()) {
            System.out.println("No appointments");
            return;
        }

        for (Patient p : appointmentQueue)
            System.out.println(p);
    }

    // Display Emergency Queue
    static void displayEmergency() {

        if (emergencyQueue.isEmpty()) {
            System.out.println("No emergency patients");
            return;
        }

        for (Patient p : emergencyQueue)
            System.out.println(p);
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n==== PERSONAL HEALTH DASHBOARD ====");
            System.out.println("1 Add Patient");
            System.out.println("2 Display Patients");
            System.out.println("3 Search Patient");
            System.out.println("4 Sort Patients");
            System.out.println("5 Add Appointment");
            System.out.println("6 Serve Appointment");
            System.out.println("7 Display Appointments");
            System.out.println("8 Add Emergency Patient");
            System.out.println("9 Treat Emergency Patient");
            System.out.println("10 Display Emergency Queue");
            System.out.println("11 Add Medical History");
            System.out.println("12 Display Medical History");
            System.out.println("13 Undo Last Action");
            System.out.println("14 Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    System.out.print("Enter Name: ");
                    String name = sc.next();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();

                    System.out.print("Enter Disease: ");
                    String disease = sc.next();

                    System.out.print("Enter Priority (1-5): ");
                    int priority = sc.nextInt();

                    Patient p = new Patient(id, name, age, disease, priority);

                    patients[count++] = p;
                    patientMap.put(id, p);

                    actionStack.push("Added Patient " + name);

                    System.out.println("Patient Added");

                    break;

                case 2:
                    displayPatients();
                    break;

                case 3:

                    System.out.print("Enter ID: ");
                    int sid = sc.nextInt();

                    Patient found = searchPatient(sid);

                    if (found != null)
                        System.out.println(found);
                    else
                        System.out.println("Patient Not Found");

                    break;

                case 4:

                    sortPatients();
                    System.out.println("Patients Sorted");

                    break;

                case 5:

                    System.out.print("Enter Patient ID: ");
                    int aid = sc.nextInt();

                    Patient ap = patientMap.get(aid);

                    if (ap != null) {
                        appointmentQueue.add(ap);
                        System.out.println("Appointment Added");
                    }

                    break;

                case 6:

                    Patient served = appointmentQueue.poll();

                    if (served != null)
                        System.out.println("Serving: " + served.name);
                    else
                        System.out.println("No Appointments");

                    break;

                case 7:
                    displayAppointments();
                    break;

                case 8:

                    System.out.print("Enter Patient ID: ");
                    int eid = sc.nextInt();

                    Patient ep = patientMap.get(eid);

                    if (ep != null) {
                        emergencyQueue.add(ep);
                        System.out.println("Emergency Added");
                    }

                    break;

                case 9:

                    Patient emergency = emergencyQueue.poll();

                    if (emergency != null)
                        System.out.println("Treating: " + emergency.name);
                    else
                        System.out.println("No Emergency Patients");

                    break;

                case 10:
                    displayEmergency();
                    break;

                case 11:

                    sc.nextLine();
                    System.out.print("Enter History Record: ");
                    String rec = sc.nextLine();

                    history.addRecord(rec);

                    break;

                case 12:
                    history.displayHistory();
                    break;

                case 13:

                    if (!actionStack.isEmpty())
                        System.out.println("Undo: " + actionStack.pop());
                    else
                        System.out.println("Nothing to undo");

                    break;

                case 14:
                    System.exit(0);
            }
        }
    }
}