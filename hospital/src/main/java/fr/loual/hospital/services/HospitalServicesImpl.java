package fr.loual.hospital.services;

import fr.loual.hospital.entities.Consultation;
import fr.loual.hospital.entities.Medecin;
import fr.loual.hospital.entities.Patient;
import fr.loual.hospital.entities.RendezVous;
import fr.loual.hospital.repositories.ConsultationRepository;
import fr.loual.hospital.repositories.MedecinRepository;
import fr.loual.hospital.repositories.PatientRepository;
import fr.loual.hospital.repositories.RendezVousRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HospitalServicesImpl implements IHospitalServices {

    public HospitalServicesImpl(PatientRepository patientRepository,
                                MedecinRepository medecinRepository,
                                RendezVousRepository rendezVousRepository,
                                ConsultationRepository consultationRepository) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.consultationRepository = consultationRepository;
    }

    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private RendezVousRepository rendezVousRepository;
    private ConsultationRepository consultationRepository;

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient findPatientByName(String name) {
        return patientRepository.findByName(name);
    }

    @Override
    public Medecin findMedecinByName(String name) {
        return medecinRepository.findByName(name);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public RendezVous findRendezVousById(Long id) {
        return rendezVousRepository.findById(id).orElse(null);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}
