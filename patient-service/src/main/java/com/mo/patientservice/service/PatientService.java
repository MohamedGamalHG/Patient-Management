package com.mo.patientservice.service;

import com.mo.patientservice.dto.PatientRequestDto;
import com.mo.patientservice.dto.PatientResponseDto;
import com.mo.patientservice.exception.EmailAlreadyExistException;
import com.mo.patientservice.exception.PatientNotFoundException;
import com.mo.patientservice.grpc.BillingServiceGrpcClient;
import com.mo.patientservice.mapper.PatientMapper;
import com.mo.patientservice.model.Patient;
import com.mo.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    public List<PatientResponseDto> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        /*
         * if you make your function static then you can use the method reference instead of lambda
         * in this manner blow
         */
        return patients.stream().map(PatientMapper::convertToDto).toList();
        /*
        * if you didn't make your function static then this work fine
        * return patients.stream().map(patient -> (new PatientMapper()).convertToDto(patient)).toList();
        */
    }

    public PatientRequestDto savePatient(PatientRequestDto patientRequestDto){
            if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
                throw new EmailAlreadyExistException("Patient With This Email Is Already Exists = " + patientRequestDto.getEmail());
            }
            Patient patient = PatientMapper.convertToEntity(patientRequestDto);
            patientRepository.save(patient);

             billingServiceGrpcClient.createBillingAccountService(patient.getId().toString()
                     ,patient.getName()
                     , patient.getEmail()
             );

            return patientRequestDto;
    }

    public PatientRequestDto updatePatient(UUID id,PatientRequestDto patientRequestDto) {

            Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("This Patient Is Not Found"));
            if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(),id)){
                throw new EmailAlreadyExistException("Patient With This Email Is Already Exists = " + patientRequestDto.getEmail());
            }
            patient = PatientMapper.convertToEntity(patientRequestDto);
            patient.setId(id);
            patientRepository.save(patient);
            return patientRequestDto;
    }

    public String deletePatient(UUID id){
            Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("This Patient Is Not Found"));
            // this code blow if you want to delete the row permanent.
            patientRepository.delete(patient);

        /*
            // this code blow if you want to use softDelete
            patient.setDeletedAt(LocalDateTime.now());
            patientRepository.save(patient);

         */
            return "Patient Deleted Successfully :)";
    }
}
