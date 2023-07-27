package com.ibm.practica.spital.repository;

import com.ibm.practica.spital.entity.Doctor;
import com.ibm.practica.spital.entity.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {

}
