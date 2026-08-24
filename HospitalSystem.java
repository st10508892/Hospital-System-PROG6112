package com.mycompany.hospitalsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class HospitalSystem {

    static ArrayList<Object> patients;

    
    // PATIENT CLASS - Stores information about one patient
    
    static class Patient {
        String id;
        String firstName;
        String lastName;
        int age;
        String gender;
        String condition;
        String category;   // "INPATIENT", "OUTPATIENT", or "EMERGENCY"
        String bedNumber;  // "none" if patient has no bed

        // Constructor - creates a new Patient object
        Patient(String id, String firstName, String lastName,
                int age, String gender, String condition, String category) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.gender = gender;
            this.condition = condition;
            this.category = category;
            this.bedNumber = "none";  // Initially no bed assigned
        }

        // Print all information about this patient
        void display() {
            System.out.println("  ID: " + id);
            System.out.println("  Name: " + firstName + " " + lastName);
            System.out.println("  Age: " + age);
            System.out.println("  Gender: " + gender);
            System.out.println("  Condition: " + condition);
            System.out.println("  Category: " + category);
            System.out.println("  Bed: " + bedNumber);
        }
    }

    
    // BED CLASS - Stores information about one bed
  
    static class Bed {
        String number;
        boolean taken;     // true = occupied, false = free
        String patientID;  // which patient is using this bed

        Bed(String number) {
            this.number = number;
            this.taken = false;
            this.patientID = "none";
        }
    }

 
    // DATA STORAGE
    
    static Patient[] patientList = new Patient[100]; // Max 100 patients
    static int patientCount = 0;                     // Current number of patients
    static Bed[] bedList = new Bed[20];              // 20 beds (B1 to B20)
    static Scanner scanner = new Scanner(System.in); // For reading user input

   
    // MAIN METHOD - Program starts here
    
    public static void main(String[] args) {

        // Create the 20 beds (B1, B2, B3 ... B20)
        for (int i = 0; i < 20; i++) {
            bedList[i] = new Bed("B" + (i + 1));
        }

        System.out.println("=====================================");
        System.out.println("   WELCOME TO HOSPITAL SYSTEM");
        System.out.println("=====================================");
        System.out.println("✓ " + 20 + " beds initialized");

        int choice;
        do {
            showMenu();                    // Print the menu
            choice = getIntInput();        // Get user's choice or input
            scanner.nextLine();            // Clear the Enter key

            // Run the correct method based on user's choice
            if (choice == 1) {
                registerPatient();
            } else if (choice == 2) {
                searchPatient();
            } else if (choice == 3) {
                updatePatient();
            } else if (choice == 4) {
                deletePatient();
            } else if (choice == 5) {
                showAllPatients();
            } else if (choice == 6) {
                allocateBed();
            } else if (choice == 7) {
                releaseBed();
            } else if (choice == 8) {
                showBedStatus();
            } else if (choice == 9) {
                generateReport();
            } else if (choice == 0) {
                System.out.println("\nThank you for using Hospital System!");
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
        
        scanner.close(); // Close the scanner when done
    }

    
    // MENU - Displays the menu options
    
    static void showMenu() {
        System.out.println("\n=========================================");
        System.out.println("           MAIN MENU"                       );
        System.out.println("=========================================");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Show All Patients");
        System.out.println("6. Allocate Bed");
        System.out.println("7. Release Bed");
        System.out.println("8. Show Bed Status");
        System.out.println("9. Generate Report");
        System.out.println("0. Exit");
        System.out.println("=========================================");
        System.out.print("Enter your choice: ");
    }

    

    // Get integer input with validation
    static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // Get string input with validation
    static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Find a patient by ID - returns index or -1 if not found
    static int findPatient(String id) {
        if (id == null || id.trim().isEmpty()) {
            return -1;
        }
        
        for (int i = 0; i < patientCount; i++) {
            if (patientList[i] != null && patientList[i].id.equals(id.trim())) {
                return i;
            }
        }
        return -1;
    }

    // Find first available bed - returns index or -1 if none available
    static int findAvailableBed() {
        for (int i = 0; i < 20; i++) {
            if (!bedList[i].taken) {
                return i;
            }
        }
        return -1;
    }

    // Release a bed occupied by a specific patient
    static void releaseBedFor(String patientID) {
        for (int i = 0; i < 20; i++) {
            if (bedList[i].taken && bedList[i].patientID.equals(patientID)) {
                bedList[i].taken = false;
                bedList[i].patientID = "none";
                System.out.println(" Bed " + bedList[i].number + " released");
                return;
            }
        }
    }

   
    // 1. REGISTER PATIENT
  
    static void registerPatient() {
        System.out.println("\n--- REGISTER NEW PATIENT ---");

        // Check if the patient list is full
        if (patientCount >= 100) {
            System.out.println(" Patient list is full! Maximum 100 patients.");
            return;
        }

        // Get patient ID
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        // Validate ID is not empty
        if (id.isEmpty()) {
            System.out.println(" ID cannot be empty!");
            return;
        }

        // Make sure ID is not already used
        if (findPatient(id) != -1) {
            System.out.println(" This ID already exists!");
            return;
        }

        // Get patient information
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        if (firstName.isEmpty()) {
            System.out.println(" First name cannot be empty!");
            return;
        }

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        if (lastName.isEmpty()) {
            System.out.println("Last name cannot be empty!");
            return;
        }

        System.out.print("Age: ");
        int age = getIntInput();

        System.out.print("Gender: ");
        String gender = scanner.nextLine().trim();

        System.out.print("Medical Condition: ");
        String condition = scanner.nextLine().trim();

        // Get patient category
        System.out.println("\nSelect Category:");
        System.out.println("  1 = Inpatient (stays overnight)");
        System.out.println("  2 = Outpatient (visits, goes home)");
        System.out.println("  3 = Emergency");
        System.out.print("Choose category (1-3): ");
        int catChoice = getIntInput();

        String category;
        if (catChoice == 1) {
            category = "INPATIENT";
        } else if (catChoice == 2) {
            category = "OUTPATIENT";
        } else if (catChoice == 3) {
            category = "EMERGENCY";
        } else {
            System.out.println(" Invalid category! Patient not registered.");
            return;
        }

        // Create the patient and add to the list
        Patient newPatient = new Patient(id, firstName, lastName, 
                                        age, gender, condition, category);
        patientList[patientCount] = newPatient;
        patientCount++;

        System.out.println("\n Patient registered successfully!");
        System.out.println("   Total patients: " + patientCount);
    }

    
    // 2. SEARCH PATIENT
    
    static void searchPatient() {
        System.out.println("\n--- SEARCH PATIENT ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        int index = findPatient(id);

        if (index == -1) {
            System.out.println(" Patient not found.");
        } else {
            System.out.println("\n Patient Found:");
            System.out.println("-------------------");
            patientList[index].display();
            System.out.println("-------------------");
        }
    }

    
    // 3. UPDATE PATIENT
    
    static void updatePatient() {
        System.out.println("\n--- UPDATE PATIENT ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        int index = findPatient(id);

        if (index == -1) {
            System.out.println(" Patient not found.");
            return;
        }

        Patient patient = patientList[index];

        System.out.println("\nCurrent details:");
        patient.display();
        System.out.println("\nPress Enter to keep current value.");

        // Update first name
        System.out.print("New First Name (" + patient.firstName + "): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            patient.firstName = input;
        }

        // Update last name
        System.out.print("New Last Name (" + patient.lastName + "): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            patient.lastName = input;
        }

        // Update age
        System.out.print("New Age (" + patient.age + "): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            try {
                patient.age = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Invalid age. Keeping current value.");
            }
        }

        // Update gender
        System.out.print("New Gender (" + patient.gender + "): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            patient.gender = input;
        }

        // Update condition
        System.out.print("New Condition (" + patient.condition + "): ");
        input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            patient.condition = input;
        }

        System.out.println("\n Patient updated successfully!");
    }

    
    // 4. DELETE PATIENT
   
    static void deletePatient() {
        System.out.println("\n--- DELETE PATIENT ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        int index = findPatient(id);

        if (index == -1) {
            System.out.println(" Patient not found.");
            return;
        }

        // Show patient before deleting
        System.out.println("\n Patient to delete:");
        patientList[index].display();

        // Confirm deletion
        System.out.print("\n Are you sure you want to delete this patient? (y/n): ");
        String confirm = scanner.nextLine().trim();
        
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        // If patient is an inpatient with a bed, free the bed first
        if (patientList[index].category.equals("INPATIENT") 
                && !patientList[index].bedNumber.equals("none")) {
            releaseBedFor(id);
        }

        // Shift patients left to fill the gap
        for (int i = index; i < patientCount - 1; i++) {
            patientList[i] = patientList[i + 1];
        }
        patientCount--;

        System.out.println("\n Patient deleted successfully!");
        System.out.println(" Remaining patients: " + patientCount);
    }

    
    // 5. SHOW ALL PATIENTS
    
    static void showAllPatients() {
        System.out.println("\n--- ALL REGISTERED PATIENTS ---");

        if (patientCount == 0) {
            System.out.println("No patients registered yet.");
            return;
        }

        System.out.println("Total: " + patientCount + " patients\n");

        for (int i = 0; i < patientCount; i++) {
            System.out.println("Patient #" + (i + 1) + ":");
            patientList[i].display();
            System.out.println();
        }
    }

    
    // 6. ALLOCATE BED
    
    static void allocateBed() {
        System.out.println("\n--- ALLOCATE BED ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        int index = findPatient(id);

        if (index == -1) {
            System.out.println(" Patient not found.");
            return;
        }

        Patient patient = patientList[index];

        // Only inpatients can get beds
        if (!patient.category.equals("INPATIENT")) {
            System.out.println(" Only INPATIENTS can be allocated beds.");
            System.out.println(" This patient is: " + patient.category);
            return;
        }

        // Check if patient already has a bed
        if (!patient.bedNumber.equals("none")) {
            System.out.println("Patient already has bed: " + patient.bedNumber);
            return;
        }

        // Find the first available bed
        int bedIndex = findAvailableBed();

        if (bedIndex == -1) {
            System.out.println("Sorry, no beds available!");
            System.out.println("All 20 beds are occupied.");
            return;
        }

        // Assign the bed to the patient
        bedList[bedIndex].taken = true;
        bedList[bedIndex].patientID = id;
        patient.bedNumber = bedList[bedIndex].number;

        System.out.println("\n Bed allocated successfully!");
        System.out.println("   Bed: " + bedList[bedIndex].number);
        System.out.println("   Patient: " + id);
    }

    
    // 7. RELEASE BED
    
    static void releaseBed() {
        System.out.println("\n--- RELEASE BED ---");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        int index = findPatient(id);

        if (index == -1) {
            System.out.println(" Patient not found.");
            return;
        }

        if (patientList[index].bedNumber.equals("none")) {
            System.out.println(" This patient has no bed assigned.");
            return;
        }

        // Confirm release
        System.out.print("Release bed " + patientList[index].bedNumber + "? (y/n): ");
        String confirm = scanner.nextLine().trim();
        
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Release cancelled.");
            return;
        }

        // Release the bed
        releaseBedFor(id);
        patientList[index].bedNumber = "none";

        System.out.println("\n Bed released successfully!");
    }

   
    // 8. SHOW BED STATUS
    
    static void showBedStatus() {
        System.out.println("\n--- BED STATUS ---");
        
        // Count beds
        int occupied = 0;
        int available = 0;
        
        System.out.println("\n occupied beds:");
        for (int i = 0; i < 20; i++) {
            if (bedList[i].taken) {
                System.out.println("   " + bedList[i].number + " -> Patient: " + bedList[i].patientID);
                occupied++;
            }
        }
        
        if (occupied == 0) {
            System.out.println("No occupied beds.");
        }

        System.out.println("\n AVAILABLE BEDS:");
        for (int i = 0; i < 20; i++) {
            if (!bedList[i].taken) {
                System.out.print(bedList[i].number + " ");
                available++;
            }
        }
        
        if (available == 0) {
            System.out.println("No available beds.");
        } else {
            System.out.println(); // New line after listing beds
        }

        System.out.println("\n SUMMARY:");
        System.out.println("   Total Beds: 20");
        System.out.println("   Occupied: " + occupied);
        System.out.println("   Available: " + available);
    }

    
    // 9. GENERATE REPORT
   
    static void generateReport() {
        System.out.println("\n=========================================");
        System.out.println("           HOSPITAL REPORT");
        System.out.println("=========================================");

        // Count occupied beds
        int occupiedBeds = 0;
        for (int i = 0; i < 20; i++) {
            if (bedList[i].taken) {
                occupiedBeds++;
            }
        }

        // Count patients by category
        int inpatients = 0;
        int outpatients = 0;
        int emergency = 0;
        
        for (int i = 0; i < patientCount; i++) {
            if (patientList[i].category.equals("INPATIENT")) {
                inpatients++;
            } else if (patientList[i].category.equals("OUTPATIENT")) {
                outpatients++;
            } else if (patientList[i].category.equals("EMERGENCY")) {
                emergency++;
            }
        }

        // Calculate occupancy rate
        double occupancyRate = (double) occupiedBeds / 20 * 100;

        // Display report
        System.out.println("\n PATIENT STATISTICS:");
        System.out.println("   Total Patients  : " + patientCount);
        System.out.println("   Inpatients      : " + inpatients);
        System.out.println("   Outpatients     : " + outpatients);
        System.out.println("   Emergency       : " + emergency);

        System.out.println("\n BED STATISTICS:");
        System.out.println(" Total Beds      : 20");
        System.out.println(" Occupied Beds   : " + occupiedBeds);
        System.out.println(" Available Beds  : " + (20 - occupiedBeds));
        System.out.printf(" Occupancy Rate  : %.2f%%\n", occupancyRate);

        // Show bed assignment details
        System.out.println("\n BED ASSIGNMENTS:");
        if (occupiedBeds == 0) {
            System.out.println(" No beds are currently occupied.");
        } else {
            for (int i = 0; i < 20; i++) {
                if (bedList[i].taken) {
                    Patient p = patientList[findPatient(bedList[i].patientID)];
                    System.out.println("   " + bedList[i].number + " -> " + 
                                     p.firstName + " " + p.lastName + " (" + p.id + ")");
                }
            }
        }

        System.out.println("\n=========================================");
    }
}