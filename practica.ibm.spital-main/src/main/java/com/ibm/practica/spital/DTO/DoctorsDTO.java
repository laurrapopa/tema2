package com.ibm.practica.spital.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorsDTO {

    private String doctorID;
    private String firstName;
    private String lastName;
    private String specialization;

}