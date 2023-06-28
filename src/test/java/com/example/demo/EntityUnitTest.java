package com.example.demo;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */

    @BeforeAll
    void setUp() {
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);

        Patient patient = new Patient("Hiram", "Chavez", 40, "hiram.chavez@email.com");
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Room room = new Room("Neurology");

        LocalDateTime startsAt2= LocalDateTime.parse("15:30 20/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("16:30 20/04/2023", formatter);

        a2 = new Appointment(patient, doctor, room, startsAt2, finishesAt2);

        Patient patient3 = new Patient("Laura", "Lopez", 18, "laura.lopez@email.com");
        Doctor doctor3 = new Doctor ("Carla", "Delgado", 21, "c.delgado@hospital.accwe");
        Room room3 = new Room("Pediatrics");

        LocalDateTime startsAt3 = LocalDateTime.parse("12:30 22/04/2023", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("13:30 22/04/2023", formatter);

        a3 = new Appointment(patient, doctor, room, startsAt2, finishesAt2);
    }


    /** Test creation of a valid Doctor object:
     ** Verifies that a Doctor object can be created with the correct attributes.
     ** Checks that the created object matches the set values.
     * */
    @Test
    void createDoctor_ValidAttributes_Success() {
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        entityManager.clear();

        assertNotNull(savedDoctor.getId());
        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertNotNull(retrievedDoctor);
        assertEquals(d1.getFirstName(), retrievedDoctor.getFirstName());
        assertEquals(d1.getLastName(), retrievedDoctor.getLastName());
        assertEquals(d1.getAge(), retrievedDoctor.getAge());
        assertEquals(d1.getEmail(), retrievedDoctor.getEmail());
    }

    /** Attribute modification test: Doctor
     ** Set values in the Doctor object's attributes and verify that they update correctly.
     ** Modify one or more attributes and make sure that the changes are correctly reflected in the object.
     * */
    @Test
    void updateDoctor_ModifyAttributes_Success() {
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        entityManager.clear();

        savedDoctor.setFirstName("Jane");
        savedDoctor.setLastName("Smith");
        savedDoctor.setAge(40);
        savedDoctor.setEmail("jane.smith@example.com");

        Doctor updatedDoctor = entityManager.merge(savedDoctor);
        entityManager.flush();
        entityManager.clear();

        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertNotNull(retrievedDoctor);
        assertEquals("Jane", retrievedDoctor.getFirstName());
        assertEquals("Smith", retrievedDoctor.getLastName());
        assertEquals(40, retrievedDoctor.getAge());
        assertEquals("jane.smith@example.com", retrievedDoctor.getEmail());
    }

    /** Equality test: Doctor
     ** Create two Doctor objects with the same attributes and verify that they are considered equal.
     * */
    @Test
    void compareDoctors_Equality_Success() {
        Doctor doctor1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        Doctor doctor2 = new Doctor("John", "Doe", 35, "john.doe@example.com");

        assertEquals(doctor1.getFirstName(), doctor2.getFirstName());
        assertEquals(doctor1.getLastName(), doctor2.getLastName());
        assertEquals(doctor1.getAge(), doctor2.getAge());
        assertEquals(doctor1.getEmail(), doctor2.getEmail());
    }

    /** Inequality test: Doctor
     ** Create two Doctor objects with different attributes and verify that they are considered different.
     * */
    @Test
    void compareDoctors_Inequality_Success() {
        Doctor doctor1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        Doctor doctor2 = new Doctor("Jane", "Smith", 40, "jane.smith@example.com");

        assertNotEquals(doctor1.getFirstName(), doctor2.getFirstName());
        assertNotEquals(doctor1.getLastName(), doctor2.getLastName());
        assertNotEquals(doctor1.getAge(), doctor2.getAge());
        assertNotEquals(doctor1.getEmail(), doctor2.getEmail());
    }

    /** Test that create a doctor with an age,
     ** change it and check that the age has been modified.
     * */
    @Test
    void modifyAge() {
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        doctor.setAge(30);

        assertEquals(doctor.getAge(), 30);
    }

    /** Test that create a doctor with an age,
     ** and check that the age is major to zero.
     * */
    @Test
    void ageIsMajorThanZero() {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");

        assertTrue(doctor.getAge() > 0);
    }

    /** Test creation of a valid Patient object:
     ** Verifies that a Patient object can be created with the correct attributes.
     ** Checks that the created object matches the set values.
     * */
    @Test
    void createPatient_ValidAttributes_Success() {
        Patient savedPatient = entityManager.persistAndFlush(p1);
        entityManager.clear();

        assertNotNull(savedPatient.getId());
        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertNotNull(retrievedPatient);
        assertEquals(p1.getFirstName(), retrievedPatient.getFirstName());
        assertEquals(p1.getLastName(), retrievedPatient.getLastName());
        assertEquals(p1.getAge(), retrievedPatient.getAge());
        assertEquals(p1.getEmail(), retrievedPatient.getEmail());
    }

    /** Attribute modification test: Patient
     ** Set values in the Patient object's attributes and verify that they update correctly.
     ** Modify one or more attributes and make sure that the changes are correctly reflected in the object.
     * */
    @Test
    void updatePatient_ModifyAttributes_Success() {
        Patient savedPatient = entityManager.persistAndFlush(p1);
        entityManager.clear();

        savedPatient.setFirstName("Jose Luis");
        savedPatient.setLastName("Olaya");
        savedPatient.setAge(37);
        savedPatient.setEmail("j.olaya@email.com");

        Patient updatedPatient = entityManager.merge(savedPatient);
        entityManager.flush();
        entityManager.clear();

        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertNotNull(retrievedPatient);
        assertEquals("Jose Luis", retrievedPatient.getFirstName());
        assertEquals("Olaya", retrievedPatient.getLastName());
        assertEquals(37, retrievedPatient.getAge());
        assertEquals("j.olaya@email.com", retrievedPatient.getEmail());
    }

    /** Equality test: Patient
     ** Create two Patient objects with the same attributes and verify that they are considered equal.
     * */
    @Test
    void comparePatients_Equality_Success() {
        Doctor Patient1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        Doctor Patient2 = new Doctor("John", "Doe", 35, "john.doe@example.com");

        assertEquals(Patient1.getFirstName(), Patient2.getFirstName());
        assertEquals(Patient1.getLastName(), Patient2.getLastName());
        assertEquals(Patient1.getAge(), Patient2.getAge());
        assertEquals(Patient1.getEmail(), Patient2.getEmail());
    }

    /** Test creation of a valid Room object:
     ** Verifies that a Room object can be created with the correct attributes.
     ** Checks that the created object matches the set values.
     * */
    @Test
    void createRoom_ValidAttributes_Success() {
        Room savedRoom = entityManager.persistAndFlush(r1);
        entityManager.clear();

        assertNotNull(savedRoom.getRoomName());
        Room retrievedRoom = entityManager.find(Room.class, savedRoom.getRoomName());
        assertNotNull(retrievedRoom);
        assertEquals(r1.getRoomName(), retrievedRoom.getRoomName());
    }

    /** Equality test: Room
     ** Create two Room objects with the same attributes and verify that they are considered equal.
     * */
    @Test
    void compareRooms_Equality_Success() {
        Room Room1 = new Room("Dermatology");
        Room Room2 = new Room("Dermatology");

        assertEquals(Room1.getRoomName(), Room2.getRoomName());
    }

    /** Inequality test: Room
     ** Create two Room objects with different attributes and verify that they are considered different.
     * */
    @Test
    void compareRooms_Inequality_Success() {
        Room Room1 = new Room("Dermatology");
        Room Room2 = new Room("Pediatrics");

        assertNotEquals(Room1.getRoomName(), Room2.getRoomName());
    }

    /** Test creation of a valid Appointment object:
     ** Verifies that an Appointment object can be created with the correct attributes.
     ** Checks that the created object matches the set values.
     * */
    @Test
    void createAppointment_ValidAttributes_Success() {
        Appointment savedAppointment = entityManager.persistAndFlush(a1);
        entityManager.clear();

        assertNotNull(savedAppointment.getId());
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        assertNotNull(retrievedAppointment);

        // Patient
        assertEquals(a1.getPatient().getId(), retrievedAppointment.getPatient().getId());
        assertEquals(a1.getPatient().getFirstName(), retrievedAppointment.getPatient().getFirstName());
        assertEquals(a1.getPatient().getLastName(), retrievedAppointment.getPatient().getLastName());
        assertEquals(a1.getPatient().getAge(), retrievedAppointment.getPatient().getAge());
        assertEquals(a1.getPatient().getEmail(), retrievedAppointment.getPatient().getEmail());

        // Doctor
        assertEquals(a1.getDoctor().getId(), retrievedAppointment.getDoctor().getId());
        assertEquals(a1.getDoctor().getFirstName(), retrievedAppointment.getDoctor().getFirstName());
        assertEquals(a1.getDoctor().getLastName(), retrievedAppointment.getDoctor().getLastName());
        assertEquals(a1.getDoctor().getAge(), retrievedAppointment.getDoctor().getAge());
        assertEquals(a1.getDoctor().getEmail(), retrievedAppointment.getDoctor().getEmail());

        //Room
        assertEquals(a1.getRoom().getRoomName(), retrievedAppointment.getRoom().getRoomName());

        assertEquals(a1.getStartsAt(), retrievedAppointment.getStartsAt());
        assertEquals(a1.getFinishesAt(), retrievedAppointment.getFinishesAt());
    }

}
