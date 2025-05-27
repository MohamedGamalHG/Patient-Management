package com.mo.patientservice.controller;

import com.mo.patientservice.dto.PatientRequestDto;
import com.mo.patientservice.dto.PatientResponseDto;
import com.mo.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients(){
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @PostMapping
    public ResponseEntity<PatientRequestDto> createPatient(@RequestBody PatientRequestDto patientRequestDto){
        return ResponseEntity.ok().body(patientService.savePatient(patientRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientRequestDto> updatePatient(@PathVariable UUID id,
                                                           @RequestBody PatientRequestDto patientRequestDto){
        return ResponseEntity.ok().body(patientService.updatePatient(id,patientRequestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id){
        return ResponseEntity.ok().body(patientService.deletePatient(id));
    }
}
