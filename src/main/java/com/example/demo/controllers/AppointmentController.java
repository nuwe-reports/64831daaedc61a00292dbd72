package com.example.demo.controllers;

import com.example.demo.entities.Appointment;
import com.example.demo.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()){
            return new ResponseEntity<>(appointment.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment){

        // Check if the appointment is valid (startsAt is before finishesAt)
        if (appointment.getStartsAt().isEqual(appointment.getFinishesAt()))
            return ResponseEntity.badRequest().build(); // 400 Bad Request

        // Check if the appointment is overlapped with any other appointment
        ResponseEntity NOT_ACCEPTABLE = checkIsOverlapped(appointment);
        if (NOT_ACCEPTABLE != null) return NOT_ACCEPTABLE; // 406 Not Acceptable

        // Save the appointment in the database
        List<Appointment> appointments = Collections.singletonList(appointmentRepository.save(appointment));

        // If all OK, return the appointment with the 200 OK status
        return ResponseEntity.status(HttpStatus.OK).body(appointments); // 200 OK
    }

    private ResponseEntity checkIsOverlapped(Appointment appointment) {
        /*Create one appointment out of two conflict date*/
        List<Appointment> existingAppointments = appointmentRepository.findAll(); // Get all appointments from the database

        // Check if any of the existing appointments overlaps with the new appointment
        boolean isOverlapped = existingAppointments.stream().anyMatch(existingAppointment -> existingAppointment.overlaps(appointment));

        if (isOverlapped)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        return null;
    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id){

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
        
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments(){
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
