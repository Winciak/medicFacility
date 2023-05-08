package com.winciak.medicFacility.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "lab_test")
public class LabTest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_lab_test", nullable = false)
  private int id;

  @NotEmpty(message = "is required")
  @Size(min = 5, max = 50)
  private String name;

  @Size(max = 500)
  private String description;

  @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL)
  private List<TestInProject> testInProjectList;



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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<TestInProject> getTestInProjectList() {
    return testInProjectList;
  }

  public void setTestInProjectList(List<TestInProject> testInProjectList) {
    this.testInProjectList = testInProjectList;
  }

}
