/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientsmanagment;

/**
 *
 * @author Palesa
 */
import java.util.*;

public class PatientsManagment {
    static class Patient {
        String patientID, firstName, lastName, gender, medicalCondition, category;
        int age;

        Patient(String patientID, String firstName, String lastName, int age,
                String gender, String medicalCondition, String category) {
            this.patientID = patientID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.medicalCondition = medicalCondition;
            this.category = category;
        }

        void display() {
            System.out.println("ID: " + patientID + " | Name: " + firstName + " " + lastName +
                    " | Age: " + age + " | Gender: " + gender +
                    " | Category: " + category + " | Condition: " + medicalCondition);
        }
    }

    static ArrayList<Patient> patients = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== PATIENT MANAGEMENT ===");
            System.out.println("1. Register Patient\n2. Search Patient\n3. Update Patient\n4. Delete Patient\n5. Display All\n0. Exit");
            int choice = getInt("Enter choice: ");

            switch (choice) {
                case 1: registerPatient(); break;
                case 2: searchPatient(); break;
                case 3: updatePatient(); break;
                case 4: deletePatient(); break;
                case 5: displayAll(); break;
                case 0: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        String id = getString("Patient ID: ");
        if (findPatient(id) != null) { System.out.println("ID already exists"); return; }

        String fn = getString("First Name: ");
        String ln = getString("Last Name: ");
        int age = getInt("Age: ");
        String gender = getString("Gender: ");
        String condition = getString("Medical Condition: ");
        String category = getString("Category [Inpatient/Outpatient/Emergency]: ");

        patients.add(new Patient(id, fn, ln, age, gender, condition, category));
        System.out.println("Patient registered successfully");
    }

    static void searchPatient() {
        String id = getString("Enter Patient ID: ");
        Patient p = findPatient(id);
        if (p != null) p.display(); else System.out.println("Patient not found");
    }

    static void updatePatient() {
        String id = getString("Enter Patient ID: ");
        Patient p = findPatient(id);
        if (p == null) { System.out.println("Patient not found"); return; }

        p.firstName = getString("New First Name: ");
        p.lastName = getString("New Last Name: ");
        p.age = getInt("New Age: ");
        p.gender = getString("New Gender: ");
        p.medicalCondition = getString("New Medical Condition: ");
        System.out.println("Patient updated");
    }

    static void deletePatient() {
        String id = getString("Enter Patient ID: ");
        Patient p = findPatient(id);
        if (p != null) { patients.remove(p); System.out.println("Patient deleted"); }
        else System.out.println("Patient not found");
    }

    static void displayAll() {
        System.out.println("\n--- All Patients ---");
        if (patients.isEmpty()) System.out.println("No patients");
        for (Patient p : patients) p.display();
    }

    static Patient findPatient(String id) {
        for (Patient p : patients) if (p.patientID.equals(id)) return p;
        return null;
    }

    static String getString(String prompt) { System.out.print(prompt); return sc.nextLine(); }
    
    static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}

   
