
package com.example.demo;

import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.PatientController;
import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllDoctors() throws Exception {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("John", "Doe", 35, "john.doe@example.com"));
        doctors.add(new Doctor("Jane", "Smith", 40, "jane.smith@example.com"));

        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act and Assert
        mockMvc.perform(get("/api/doctors"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.length()").value(doctors.size()));
    }

    @Test
    void shouldReturnDoctorById() throws Exception {
        // Arrange
        long doctorId = 1;
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        doctor.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act and Assert
        mockMvc.perform(get("/api/doctors/{id}", doctorId))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(doctorId))
          .andExpect(jsonPath("$.firstName").value(doctor.getFirstName()))
          .andExpect(jsonPath("$.lastName").value(doctor.getLastName()))
          .andExpect(jsonPath("$.age").value(doctor.getAge()))
          .andExpect(jsonPath("$.email").value(doctor.getEmail()));
    }

    @Test
    void shouldReturnNotFoundForNonexistentDoctor() throws Exception {
        // Arrange
        long doctorId = 1;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/doctors/{id}", doctorId))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        // Arrange
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act and Assert
        mockMvc.perform(post("/api/doctor")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(doctor)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.firstName").value(doctor.getFirstName()))
          .andExpect(jsonPath("$.lastName").value(doctor.getLastName()))
          .andExpect(jsonPath("$.age").value(doctor.getAge()))
          .andExpect(jsonPath("$.email").value(doctor.getEmail()));
    }

    @Test
    void shouldDeleteDoctor() throws Exception {
        // Arrange
        long doctorId = 1;
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        doctor.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act and Assert
        mockMvc.perform(delete("/api/doctors/{id}", doctorId))
          .andExpect(status().isOk());

        verify(doctorRepository, times(1)).deleteById(doctorId);
    }

    @Test
    void shouldReturnNotFoundForNonexistentDoctorOnDelete() throws Exception {
        // Arrange
        long doctorId = 1;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(delete("/api/doctors/{id}", doctorId))
          .andExpect(status().isNotFound());

        verify(doctorRepository, times(0)).deleteById(doctorId);
    }
}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllPatients() throws Exception {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("John", "Doe", 35, "john.doe@example.com"));
        patients.add(new Patient("Jane", "Smith", 28, "jane.smith@example.com"));

        when(patientRepository.findAll()).thenReturn(patients);

        // Act and Assert
        mockMvc.perform(get("/api/patients"))
          .andExpect(status().isOk())
          .andExpect(content().json(objectMapper.writeValueAsString(patients)));

        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnNotFoundForNonexistentPatientOnGetById() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
          .andExpect(status().isNotFound());

        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void shouldReturnNotFoundForNonexistentPatient() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPatientById() throws Exception {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient("John", "Doe", 35, "john.doe@example.com");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act
        MvcResult result = mockMvc.perform(get("/api/patients/{id}", patientId))
          .andReturn();

        // Assert
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String content = result.getResponse().getContentAsString();
        JSONObject patientJson = new JSONObject(content);

        assertEquals("John", patientJson.getString("firstName"));
        assertEquals("Doe", patientJson.getString("lastName"));

        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void shouldCreatePatient() throws Exception {
        // Arrange
        Patient patient = new Patient("John", "Doe", 35, "john.doe@example.com");

        // Act
        MvcResult result = mockMvc.perform(post("/api/patient")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(patient)))
          .andReturn();

        // Assert
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);

        String content = result.getResponse().getContentAsString();
        JSONObject patientJson = new JSONObject(content);

        assertEquals("John", patientJson.getString("firstName"));
        assertEquals("Doe", patientJson.getString("lastName"));

        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void shouldReturnNotFoundForNonexistentPatientOnDelete() throws Exception {
        // Arrange
        long patientId = 1;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
          .andExpect(status().isNotFound());

        verify(patientRepository, times(0)).deleteById(patientId);
    }

    @Test
    void shouldDeletePatient() throws Exception {
        // Arrange
        long patientId = 1;
        Patient patient = new Patient("John", "Doe", 35, "john.doe@example.com");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act and Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
          .andExpect(status().isOk());

        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).deleteById(patientId);
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllRooms() throws Exception {
        // Arrange
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Dermatology"));
        rooms.add(new Room("Cardiology"));

        when(roomRepository.findAll()).thenReturn(rooms);

        // Act
        MvcResult result = mockMvc.perform(get("/api/rooms"))
          .andExpect(status().isOk())
          .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONArray roomsJsonArray = new JSONArray(responseContent);

        // Assert
        assertEquals(2, roomsJsonArray.length());

        JSONObject room1 = roomsJsonArray.getJSONObject(0);
        assertEquals("Dermatology", room1.getString("roomName"));

        JSONObject room2 = roomsJsonArray.getJSONObject(1);
        assertEquals("Cardiology", room2.getString("roomName"));

        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnRoomByRoomName() throws Exception {
        // Arrange
        String roomName = "Dermatology";
        Room room = new Room(roomName);

        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(room));

        // Act
        MvcResult result = mockMvc.perform(get("/api/rooms/{roomName}", roomName))
          .andExpect(status().isOk())
          .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONObject roomJson = new JSONObject(responseContent);

        // Assert
        assertEquals(roomName, roomJson.getString("roomName"));

        verify(roomRepository, times(1)).findByRoomName(roomName);
    }

    @Test
    void shouldCreateRoom() throws Exception {
        // Arrange
        Room room = new Room("Dermatology");

        // Act
        MvcResult result = mockMvc.perform(post("/api/room")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(room)))
          .andExpect(status().isCreated())
          .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        JSONObject roomJson = new JSONObject(responseContent);

        // Assert
        assertEquals("Dermatology", roomJson.getString("roomName"));

        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void shouldDeleteRoomByRoomName() throws Exception {
        // Arrange
        String roomName = "Dermatology";
        Room room = new Room(roomName);

        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(room));

        // Act and Assert
        mockMvc.perform(delete("/api/rooms/{roomName}", roomName))
          .andExpect(status().isOk());

        verify(roomRepository, times(1)).deleteByRoomName(roomName);
    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        // Act and Assert
        mockMvc.perform(delete("/api/rooms"))
          .andExpect(status().isOk());

        verify(roomRepository, times(1)).deleteAll();
    }

}