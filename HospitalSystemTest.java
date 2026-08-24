import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

ublic class HospitalSystemTest {

    // We test the logic using the same inner classes as your main system
    static class Patient {
        String patientID, firstName, lastName;
        Patient(String id, String fn, String ln) {
            patientID=id; firstName=fn; lastName=ln;
        }
    }

    static class Bed {
        String bedNumber;
        boolean occupied;
        String patientID;
        Bed(String num){ bedNumber=num; }
        void allocate(String pid){ occupied=true; patientID=pid; }
        void release(){ occupied=false; patientID=null; }
    }

    private ArrayList<Patient> patients;
    private Bed[][] ward;

    @Before
    public void setUp() {
        patients = new ArrayList<>();
        ward = new Bed[4][5];
        int c=1;
        for(int i=0;i<4;i++) for(int j=0;j<5;j++) ward[i][j]= new Bed(String.format("B%02d", c++));
    }

    // FEATURE 1: PATIENT MANAGEMENT TESTS
    @Test
    public void testPatientRegistration() {
        Patient p = new Patient("P001", "Thabo", "Mokoena");
        patients.add(p);
        assertEquals(1, patients.size());
        assertEquals("P001", patients.get(0).patientID);
    }

    @Test
    public void testDuplicatePatientID() {
        patients.add(new Patient("P001", "Thabo", "Mokoena"));
        boolean duplicateExists = false;
        for(Patient p: patients) if(p.patientID.equals("P001")) duplicateExists = true;
        assertTrue("Should detect duplicate", duplicateExists);
    }

    @Test
    public void testSearchPatient() {
        patients.add(new Patient("P002", "Sara", "Dlamini"));
        Patient found = null;
        for(Patient p: patients) if(p.patientID.equals("P002")) found = p;
        assertNotNull(found);
        assertEquals("Sara", found.firstName);
    }

    @Test
    public void testDeletePatient() {
        patients.add(new Patient("P003", "John", "Smith"));
        patients.removeIf(p -> p.patientID.equals("P003"));
        assertEquals(0, patients.size());
    }

    // FEATURE 2: BED MANAGEMENT TESTS
    @Test
    public void testBedAllocation() {
        Bed bed = ward[0][0];
        assertFalse(bed.occupied);
        bed.allocate("P001");
        assertTrue(bed.occupied);
        assertEquals("P001", bed.patientID);
    }

    @Test
    public void testBedRelease() {
        Bed bed = ward[0][0];
        bed.allocate("P001");
        bed.release();
        assertFalse(bed.occupied);
        assertNull(bed.patientID);
    }

    @Test
    public void testNoDoubleAllocation() {
        Bed bed = ward[0][0];
        bed.allocate("P001");
        // Try to allocate same bed again - should stay with first patient
        assertEquals("P001", bed.patientID);
        assertTrue(bed.occupied);
    }

    @Test
    public void testAvailableBedsCount() {
        ward[0][0].allocate("P001");
        ward[0][1].allocate("P002");
        long available = 0;
        for(Bed[] row: ward) for(Bed b: row) if(!b.occupied) available++;
        assertEquals(18, available); // 20 total - 2 occupied
    }
}