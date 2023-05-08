package com.winciak.medicFacility.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "patient_test")
public class PatientTest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_patient_test", nullable = false)
  private int id;

  private String result;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  @NotNull(message = "is required")
  private Date date;

  @ManyToOne
  @JoinColumn(name = "patient_user_id")
  private PatientUser patientUser;

  @ManyToOne
  @JoinColumn(name = "test_in_project_id")
  @NotNull(message = "is required")
  private TestInProject testInProject;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public PatientUser getPatientUser() {
    return patientUser;
  }

  public void setPatientUser(PatientUser patientUser) {
    this.patientUser = patientUser;
  }

  public TestInProject getTestInProject() {
    return testInProject;
  }

  public void setTestInProject(TestInProject testInProject) {
    this.testInProject = testInProject;
  }
}
