package com.winciak.medicFacility.services;


import com.winciak.medicFacility.dto.DtoUser;
import com.winciak.medicFacility.entities.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

	List<PatientUser> findAll();

	PatientUser findById(int theId);
	
	void save(PatientUser theUser);
	
	void deleteById(int theId);

	List<Role> findRoles();

	PatientUser findByLogin(String login);

	void save(DtoUser dtoUser);

    void update(DtoUser dtoUser);

	PatientsProjects findPatientsProjectsById(int id);

	PatientsProjects findPatientsProjectsByPatientUserIdAndResearchProjectId(int userId, int projectId);

	void savePatientsProjects(PatientsProjects patientsProjects);

	void deletePatientsProjectsById(int id);

	ConsentFile findFileById(int id);

	PatientTest findByPatientUserIdAndTestInProjectId(int patientId, int testInProjectId);

	void savePatientTest(PatientTest patientTest);

	void deletePatientTest(PatientTest patientTest);

	PatientTest findPatientTestById(int patientTestId);
}
