package com.mo.patientservice.mapper;

import com.mo.patientservice.dto.PatientRequestDto;
import com.mo.patientservice.dto.PatientResponseDto;
import com.mo.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDto convertToDto(Patient patient){
        return PatientResponseDto.builder()
                .id(patient.getId().toString())
                .name(patient.getName())
                .address(patient.getAddress())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .build();
    }
    public static Patient convertToEntity(PatientRequestDto patientRequestDto){
        return Patient.builder()
                .name(patientRequestDto.getName())
                .address(patientRequestDto.getAddress())
                .email(patientRequestDto.getEmail())
                .dateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()))
                .registerDate(LocalDate.parse("2025-02-05"))
                .build();
    }
}
