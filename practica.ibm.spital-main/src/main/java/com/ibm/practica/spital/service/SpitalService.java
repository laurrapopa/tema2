package com.ibm.practica.spital.service;

import com.ibm.practica.spital.DTO.*;
import com.ibm.practica.spital.entity.Doctor;
import com.ibm.practica.spital.entity.Pacient;
import com.ibm.practica.spital.entity.Reservation;
import com.ibm.practica.spital.repository.DoctorRepository;
import com.ibm.practica.spital.repository.PacientRepository;
import com.ibm.practica.spital.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
public class SpitalService {

 @Autowired
 PacientRepository pacientRepository;
 @Autowired ReservationRepository reservationRepository;
 @Autowired
 DoctorRepository doctorRepository;

 ModelMapper mapper = new ModelMapper();

 public List<PacientDTO> getAllPacients(){
  log.info("SpitalService.getAllPacients() retrieving all pacients...");

// alternativa clasica
//  List<Pacient> list = pacientRepository.findAll();
//  List<PacientDTO> result = new ArrayList<>();
//  for (Pacient pacient: list) {
//// alternativa la model mapper
////   PacientDTO dto = new PacientDTO(pacient.getFirstName(), pacient.getLastName(), pacient.getAge(),pacient.getIssue());
//   PacientDTO dto = mapper.map(pacient,PacientDTO.class);
//   result.add(dto);
//  }

  return pacientRepository.findAll().stream()
      .map(pacient -> mapper.map(pacient,PacientDTO.class))
      .collect(Collectors.toList());
 }

 public List<ReservationDTO> getReservations(){
  log.info("SpitalService.getReservations() retrieving all reservations...");
  return reservationRepository.findAll().stream()
      .map(reservation -> mapper.map(reservation,ReservationDTO.class))
      .collect(Collectors.toList());
 }

 public ReservationDTO getReservation(String reservationID){
  log.info("SpitalService.getReservation() retrieving reservation with ID: " + reservationID);
  return reservationRepository.findById(reservationID)
      .map(reservation -> mapper.map(reservation,ReservationDTO.class))
      .orElse(null);
 }

 public List<ReservationDTO> getReservationForPacient(String pacientID){
  log.info("SpitalService.getReservations() retrieving all reservations...");

//  Option 1
//  return reservationRepository.findAll().stream()
//      .filter(r -> r.getPacientID().equals(pacientID))
//      .map(reservation -> mapper.map(reservation,ReservationDTO.class))
//      .collect(Collectors.toList());

  //  Option 2
//  return reservationRepository.findAllReservationsByPacientID(pacientID).stream()
//      .map(reservation -> mapper.map(reservation,ReservationDTO.class))
//      .collect(Collectors.toList());

  //Option 3
    return reservationRepository.findAllByPacientID(pacientID).stream()
        .map(reservation -> mapper.map(reservation,ReservationDTO.class))
        .collect(Collectors.toList());
 }

 public boolean addReservation(AddReservationDTO dto){
  Reservation reservation = mapper.map(dto, Reservation.class);
  String id = UUID.randomUUID().toString();
  reservation.setId(id.replace("-",""));
  reservation.setReservationDate(LocalDateTime.now());
  Reservation fromDB = reservationRepository.save(reservation);
  log.info("addReservation() Reservation saved with ID: " + fromDB.getId());
  return ObjectUtils.isNotEmpty(fromDB);
 }

 public boolean addPacient(AddPacientDTO pacientDTO){

  Pacient pacient = mapper.map(pacientDTO,Pacient.class);
  String id = UUID.randomUUID().toString();
  pacient.setPacientID(id.replace("-",""));
  Pacient p = pacientRepository.save(pacient);
  log.info("saved pacient id is: " + p.getPacientID());
  return ObjectUtils.isNotEmpty(p);
 }

 public void deleteReservation(String reservationID){
  log.info("deleteReservation() started.");
  reservationRepository.deleteById(reservationID);
  log.info("deleteReservation() finished.");
 }

 public boolean deletePacient(String pacientID){
  log.info("deletePacient() started.");
// Option 1
//  if(!pacientRepository.existsById(pacientID)){
//   log.warn("deletePacient(): could not find pacient with ID: " + pacientID);
//   log.warn("deletePacient(): nothing was deleted...");
//   return false;
//  }
//  pacientRepository.deleteById(pacientID);

  // Option 2
  int deletedRows = pacientRepository.deletePacient(pacientID);
  log.info("deletePacient() deleted: " + deletedRows + " row(s)");
  return deletedRows > 0;
 }

 public boolean editPacient(PacientDTO pacientDTO){
  //log.info("editPacient() started.");
 // int updatedRows = pacientRepository.editPacient(String.valueOf(pacientDTO));
 // log.info("updatePacient() updated: " + updatedRows + " row(s)");
 //
  // return updatedRows > 0;
  return true;
 }
 public boolean editReservation(ReservationDTO reservationDTO){
  return true;
 }


 public boolean addDoctor(AddDoctorDTO addDoctorDTO){
  Doctor doctor = mapper.map(addDoctorDTO, Doctor.class);
  String id = UUID.randomUUID().toString();
  doctor.setDoctorID(id.replace(id, doctor.getDoctorID()));
  Doctor d = doctorRepository.save(doctor);
  return ObjectUtils.isNotEmpty(d);
 }
 public List<DoctorsDTO> getDoctors() {
  Doctor d = new Doctor();
  d.setDoctorID("1");
  d.setFirstName("Marius");
  d.setLastName("Paduraru");
  d.setSpecialization("Oncologie");

  d = doctorRepository.save(d);

  return doctorRepository.findAll().stream()
          .map(doctor -> mapper.map(doctor, DoctorsDTO.class))
          .collect(Collectors.toList());
 }


}
