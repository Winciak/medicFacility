package com.winciak.medicFacility.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "test_in_project")
public class TestInProject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_test_in_project", nullable = false)
  private int id;

  @ManyToOne
  @JoinColumn(name = "research_project_id")
  private ResearchProject researchProject;

  @ManyToOne
  @JoinColumn(name = "lab_test_id")
  private LabTest labTest;

  @OneToMany(mappedBy = "testInProject", cascade = CascadeType.ALL)
  private List<PatientTest> patientTestList;


  public TestInProject(int id, ResearchProject researchProject, LabTest labTest, List<PatientTest> patientTestList) {
    this.id = id;
    this.researchProject = researchProject;
    this.labTest = labTest;
    this.patientTestList = patientTestList;
  }

  public TestInProject() {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ResearchProject getResearchProject() {
    return researchProject;
  }

  public void setResearchProject(ResearchProject researchProject) {
    this.researchProject = researchProject;
  }

  public LabTest getLabTest() {
    return labTest;
  }

  public void setLabTest(LabTest labTest) {
    this.labTest = labTest;
  }

  public List<PatientTest> getPatientTestList() {
    return patientTestList;
  }

  public void setPatientTestList(List<PatientTest> patientTestList) {
    this.patientTestList = patientTestList;
  }

  @Override
  public String toString() {
    return labTest.getName();
  }
}
