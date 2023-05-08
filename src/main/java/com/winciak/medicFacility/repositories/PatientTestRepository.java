package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.PatientTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientTestRepository extends JpaRepository<PatientTest, Integer> {

    PatientTest findByPatientUserIdAndTestInProjectId(int patientId, int testInProjectId);
}
