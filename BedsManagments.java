/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bedsmanagment;

/**
 *
 * @author Palesa
 */
import java.util.*;

public class BedsManagment {
    static class Bed {
        String bedNumber;
        boolean occupied;
        String patientID;

        Bed(String bedNumber) { this.bedNumber = bedNumber; }

        void allocate(String pid) { occupied = true; patientID = pid; }
        void release() { occupied = false; patientID = null; }
    }

    static Bed[][] ward = new Bed[4][5];
    static HashMap<String, String> inpatientBeds = new HashMap<>(); // patientID -> bedNumber
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initBeds();
        while (true) {
            System.out.println("\n=== BED MANAGEMENT ===");
            System.out.println("1. Allocate Bed\n2. Release Bed\n3. Display Ward Layout\n4. Display Available Beds\n5. Display Occupied Beds\n0. Exit");
            int choice = getInt("Enter choice: ");

            switch (choice) {
                case 1: allocateBed(); break;
                case 2: releaseBed(); break;
                case 3: displayLayout(); break;
                case 4: displayAvailable(); break;
                case 5: displayOccupied(); break;
                case 0: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    static void initBeds() {
        int count = 1;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++)
                ward[i][j] = new Bed(String.format("B%02d", count++));
    }

    static void allocateBed() {
        String pid = getString("Enter Inpatient ID: ");
        if (inpatientBeds.containsKey(pid)) { System.out.println("Patient already has a bed"); return; }

        Bed bed = getAvailableBed();
        if (bed == null) { System.out.println("No beds available"); return; }

        bed.allocate(pid);
        inpatientBeds.put(pid, bed.bedNumber);
        System.out.println("Bed " + bed.bedNumber + " allocated to " + pid);
    }

    static void releaseBed() {
        String pid = getString("Enter Inpatient ID to discharge: ");
        String bedNum = inpatientBeds.get(pid);
        if (bedNum == null) { System.out.println("Patient has no bed"); return; }

        for (Bed[] row : ward)
            for (Bed b : row)
                if (b.bedNumber.equals(bedNum)) b.release();

        inpatientBeds.remove(pid);
        System.out.println("Bed " + bedNum + " released");
    }

    static void displayLayout() {
        System.out.println("\n--- Ward Layout 4x5 ---");
        for (Bed[] row : ward) {
            for (Bed b : row) {
                System.out.print(b.occupied? "[" + b.bedNumber + ":X] " : "[" + b.bedNumber + ": ] ");
            }
            System.out.println();
        }
    }

    static void displayAvailable() {
        System.out.print("Available Beds: ");
        for (Bed[] row : ward) for (Bed b : row) if (!b.occupied) System.out.print(b.bedNumber + " ");
        System.out.println();
    }

    static void displayOccupied() {
        System.out.println("Occupied Beds:");
        for (Bed[] row : ward) for (Bed b : row) if (b.occupied) System.out.println(b.bedNumber + " -> " + b.patientID);
    }

    static Bed getAvailableBed() {
        for (Bed[] row : ward) for (Bed b : row) if (!b.occupied) return b;
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
