package com.winciak.medicFacility.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_role", nullable = false)
  private int id;

  private String name;

  public Role() {
  }

  public Role(int id, String name) {
    this.id = id;
    this.name = name;
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

  @Override
  public String toString() {
    return name;
  }
}
