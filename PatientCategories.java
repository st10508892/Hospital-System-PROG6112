/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientcategories;

// ENUM: PatientCategory
// Defines the three types of patients

enum PatientCategory {
    INPATIENT,    // Patient who stays in the hospital
    OUTPATIENT,   // Patient who visits and goes home
    EMERGENCY     // Emergency patient
}


// CLASS: Patient (Base Class)
// This is the parent class for all patient types

class Patient {
    // Properties of a patient
    protected String patientID;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected String medicalCondition;
    protected PatientCategory category;

    // Constructor - creates a new Patient object
    public Patient(String patientID, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // Getter methods (to access protected fields)
    public String getPatientID() {
        return patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public PatientCategory getCategory() {
        return category;
    }

    // Setter methods (to modify fields)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    // Display patient details
    public void displayDetails() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Category: " + category);
        System.out.println("Condition: " + medicalCondition);
    }
}


// CLASS: Inpatient (Child Class)
// This class extends Patient and adds bed/ward information

class Inpatient extends Patient {
    // Additional attributes for inpatients
    private int wardNumber;
    private String bedNumber;

    // Constructor - calls the parent constructor using super()
    public Inpatient(String patientID, String firstName, String lastName, int age,
                     String gender, String medicalCondition, int wardNumber, String bedNumber) {
        // Call the parent (Patient) constructor
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // Getter methods for Inpatient-specific fields
    public int getWardNumber() {
        return wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    // Setter methods
    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    // Override the displayDetails method from Patient
    @Override
    public void displayDetails() {
        // Call the parent's displayDetails method
        super.displayDetails();
        // Add inpatient-specific information
        System.out.println("Ward: " + wardNumber);
        System.out.println("Bed: " + bedNumber);
    }
}


// MAIN CLASS, This class demonstrates how to use Patient and Inpatient

public class PatientCategories {
    
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("     PATIENT CATEGORIES DEMO");
        System.out.println("=========================================\n");

        // Create different types of patients
        System.out.println("--- Creating Patients ---\n");

        // 1. Create an Outpatient patient
        System.out.println("1. Creating Outpatient (Thabo)");
        Patient patient1 = new Patient("P001", "Thabo", "Mokoena", 30, 
                                      "Male", "Flu", PatientCategory.OUTPATIENT);

        // 2. Create an Emergency patient
        System.out.println("2. Creating Emergency patient (Sara)");
        Patient patient2 = new Patient("P002", "Sara", "Dlamini", 25, 
                                      "Female", "Fracture", PatientCategory.EMERGENCY);

        // 3. Create an Inpatient (uses the Inpatient class)
        System.out.println("3. Creating Inpatient (John)");
        Inpatient patient3 = new Inpatient("P003", "John", "Smith", 45, 
                                          "Male", "Surgery", 1, "B05");

        System.out.println(); // Empty line for spacing

        
        // Display patient details
        
        System.out.println("--- Displaying Patient Details ---\n");

        System.out.println("Patient 1 (Outpatient):");
        System.out.println("-----------------------");
        patient1.displayDetails();
        System.out.println();

        System.out.println("Patient 2 (Emergency):");
        System.out.println("----------------------");
        patient2.displayDetails();
        System.out.println();

        System.out.println("Patient 3 (Inpatient):");
        System.out.println("----------------------");
        patient3.displayDetails(); // Shows ward and bed because of override
        System.out.println();

       
        // Demonstrate polymorphism
        
        System.out.println("--- Polymorphism Demo ---");
        System.out.println("Using Patient reference for Inpatient object:");
        System.out.println();

        // A Patient reference can point to an Inpatient object
        Patient patient4 = new Inpatient("P004", "Mary", "Johnson", 60, 
                                        "Female", "Heart Condition", 2, "B12");

        // This will call the overridden displayDetails() method
        patient4.displayDetails();
        System.out.println();

        
        // Demonstrate Inpatient-specific methods
        
        System.out.println("--- Inpatient-Specific Methods ---");
        System.out.println("Accessing methods only available to Inpatients:");
        System.out.println();
        
        // We need to cast to Inpatient to access inpatient-specific methods
        if (patient4 instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient4;
            System.out.println("Patient: " + inpatient.getFirstName() + " " + inpatient.getLastName());
            System.out.println("Bed Number: " + inpatient.getBedNumber());
            System.out.println("Ward Number: " + inpatient.getWardNumber());
        } else {
            System.out.println("patient4 is not an Inpatient");
        }
        System.out.println();

        
        // Show category information
        
        System.out.println("--- Category Information ---");
        System.out.println("Patient 1 Category: " + patient1.getCategory());
        System.out.println("Patient 2 Category: " + patient2.getCategory());
        System.out.println("Patient 3 Category: " + patient3.getCategory());
        System.out.println("Patient 4 Category: " + patient4.getCategory());
        System.out.println();

        
        // Compare Patient vs Inpatient
        
        System.out.println("--- Comparing Patient vs Inpatient ---");
        System.out.println("Patient 1 is an instance of Patient: " + (patient1 instanceof Patient));
        System.out.println("Patient 1 is an instance of Inpatient: " + (patient1 instanceof Inpatient));
        System.out.println("Patient 3 is an instance of Patient: " + (patient3 instanceof Patient));
        System.out.println("Patient 3 is an instance of Inpatient: " + (patient3 instanceof Inpatient));
        System.out.println();

        System.out.println("=========================================");
        System.out.println("     DEMO COMPLETE");
        System.out.println("=========================================");
    }
}