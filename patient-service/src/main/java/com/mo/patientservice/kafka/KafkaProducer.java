package com.mo.patientservice.kafka;

import com.mo.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String,byte[]> template;
    private Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    public void sendEvent(Patient patient){
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        try{
            template.send("patient_topic",patientEvent.toByteArray());
            log.info("the message is {}",patientEvent.getName() + " - " + patientEvent.getEmail());
        }catch (Exception e){
            log.error("Error Sending Patient Created {}",e.getMessage());
        }
    }
}
