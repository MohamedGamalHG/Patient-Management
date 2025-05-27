package com.mo.patientservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PatientRequestDto {
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}
