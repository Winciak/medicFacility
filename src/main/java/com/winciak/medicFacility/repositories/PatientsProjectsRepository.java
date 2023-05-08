package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.PatientsProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsProjectsRepository extends JpaRepository<PatientsProjects, Integer> {

    PatientsProjects findPatientsProjectsByPatientUserIdAndResearchProjectId(int userId, int projectId);
}
