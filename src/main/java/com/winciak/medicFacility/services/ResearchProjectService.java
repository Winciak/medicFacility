package com.winciak.medicFacility.services;



import com.winciak.medicFacility.entities.ResearchProject;
import com.winciak.medicFacility.entities.TestInProject;


import java.util.List;

public interface ResearchProjectService  {

	List<ResearchProject> findAll();

	ResearchProject findById(int theId);
	
	void save(ResearchProject researchProject);
	
	void deleteById(int theId);

	ResearchProject findByName(String name);

	TestInProject findTestInProjectByResearchProjectIdAndLabTestId(int projectId, int testId);

	void saveTestToProject(TestInProject testInProject);

	void deleteTestInProject(TestInProject testInProject);

	List<TestInProject> findAllTestsByResearchProjectId(int id);


}
