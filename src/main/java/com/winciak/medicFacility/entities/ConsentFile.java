package com.winciak.medicFacility.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "consent_file")
public class ConsentFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_file", nullable = false)
  private int id;

  @Lob
  @Column(name = "file")
  private byte[] file;

  @Column(name = "file_name")
  @Size(max = 45)
  private String fileName;

  @Column(name = "file_type")
  @Size(max = 20)
  private String fileType;

  public ConsentFile() {
  }

  public ConsentFile(int id, byte[] file) {
    this.id = id;
    this.file = file;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
}
