package fr.loual.hospital.services;

import fr.loual.hospital.entities.Consultation;
import fr.loual.hospital.entities.Medecin;
import fr.loual.hospital.entities.Patient;
import fr.loual.hospital.entities.RendezVous;

public interface IHospitalServices {

    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    Patient findPatientById(Long id);
    Patient findPatientByName(String name);
    Medecin findMedecinByName(String name);

    RendezVous saveRendezVous(RendezVous rendezVous);
    RendezVous findRendezVousById(Long id);
    Consultation saveConsultation(Consultation consultation);



}
