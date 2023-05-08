package com.winciak.medicFacility.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients_projects")
public class PatientsProjects {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_patients_projects")
  private int id;

  @NotNull(message = "is required")
  private long consent;

  @ManyToOne
  @JoinColumn(name = "patient_user_id")
  private PatientUser patientUser;

  @ManyToOne
  @JoinColumn(name = "research_project_id")
  private ResearchProject researchProject;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "consent_file_id")
  private ConsentFile consentFile;

  public PatientsProjects() {
  }



  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getConsent() {
    return consent;
  }

  public void setConsent(long consent) {
    this.consent = consent;
  }

  public PatientUser getPatientUser() {
    return patientUser;
  }

  public void setPatientUser(PatientUser patientUser) {
    this.patientUser = patientUser;
  }

  public ResearchProject getResearchProject() {
    return researchProject;
  }

  public void setResearchProject(ResearchProject researchProject) {
    this.researchProject = researchProject;
  }

  public ConsentFile getConsentFile() {
    return consentFile;
  }

  public void setConsentFile(ConsentFile consentFile) {
    this.consentFile = consentFile;
  }
}
