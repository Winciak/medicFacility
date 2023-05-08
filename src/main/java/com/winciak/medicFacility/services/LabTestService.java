package com.winciak.medicFacility.services;


import com.winciak.medicFacility.entities.LabTest;
import com.winciak.medicFacility.entities.ResearchProject;

import java.util.List;

public interface LabTestService {

	List<LabTest> findAll();

	LabTest findById(int theId);
	
	void save(LabTest labTest);
	
	void deleteById(int theId);

	LabTest findByName(String name);

}
