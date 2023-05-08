package com.winciak.medicFacility.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "research_project")
public class ResearchProject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_project")
  private int id;

  @NotEmpty(message = "is required")
  private String name;

  @OneToMany(mappedBy = "researchProject", cascade = CascadeType.ALL)
  private List<PatientsProjects> patientsProjects;

  @OneToMany(mappedBy = "researchProject", cascade = CascadeType.ALL)
  private List<TestInProject> testInProjects;

  public ResearchProject() {
  }

  public ResearchProject(int id, String name, List<PatientsProjects> patientsProjects, List<TestInProject> testInProjects) {
    this.id = id;
    this.name = name;
    this.patientsProjects = patientsProjects;
    this.testInProjects = testInProjects;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<PatientsProjects> getPatientsProjects() {
    return patientsProjects;
  }

  public void setPatientsProjects(List<PatientsProjects> patientsProjects) {
    this.patientsProjects = patientsProjects;
  }

  public List<TestInProject> getTestInProjects() {
    return testInProjects;
  }

  public void setTestInProjects(List<TestInProject> testInProjects) {
    this.testInProjects = testInProjects;
  }

}
