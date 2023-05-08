package com.winciak.medicFacility.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "patient_user")
public class PatientUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user", nullable = false)
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
  private String login;
  private String password;

  @Column(name = "phone_number")
  private String phoneNumber;
  private String email;

  @OneToMany(mappedBy = "patientUser", cascade = CascadeType.ALL)
  private List<PatientsProjects> patientsProjects;

  @OneToMany(mappedBy = "patientUser", cascade = CascadeType.ALL)
  private List<PatientTest> patientTestList;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "patient_user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Collection<Role> roles;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<PatientsProjects> getPatientsProjects() {
    return patientsProjects;
  }

  public void setPatientsProjects(List<PatientsProjects> patientsProjects) {
    this.patientsProjects = patientsProjects;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  public List<PatientTest> getPatientTestList() {
    return patientTestList;
  }

  public void setPatientTestList(List<PatientTest> patientTestList) {
    this.patientTestList = patientTestList;
  }
}
