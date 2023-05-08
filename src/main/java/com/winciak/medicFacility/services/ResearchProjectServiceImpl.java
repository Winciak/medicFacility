package com.winciak.medicFacility.services;

import com.winciak.medicFacility.entities.ResearchProject;
import com.winciak.medicFacility.entities.TestInProject;
import com.winciak.medicFacility.repositories.ResearchProjectRepository;
import com.winciak.medicFacility.repositories.TestInProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResearchProjectServiceImpl implements com.winciak.medicFacility.services.ResearchProjectService {

    private final ResearchProjectRepository researchProjectRepository;

    private final TestInProjectRepository testInProjectRepository;

    @Autowired
    public ResearchProjectServiceImpl(ResearchProjectRepository researchProjectRepository, TestInProjectRepository testInProjectRepository) {
        this.researchProjectRepository = researchProjectRepository;
        this.testInProjectRepository = testInProjectRepository;
    }

    @Override
    public List<ResearchProject> findAll() {
        return researchProjectRepository.findAll();
    }

    @Override
    public ResearchProject findById(int id) {

        Optional<ResearchProject> result = researchProjectRepository.findById(id);

        ResearchProject researchProject;

        if (result.isPresent()) {
            researchProject = result.get();
        }
        else {
            throw new RuntimeException("Did not find research project id - " + id);
        }

        return researchProject;
    }

    @Override
    public void save(ResearchProject researchProject) {
        researchProjectRepository.save(researchProject);
    }

    @Override
    public void deleteById(int id) {
        researchProjectRepository.deleteById(id);
    }

    @Override
    public ResearchProject findByName(String name) {
        return researchProjectRepository.findResearchProjectByName(name);
    }

    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public TestInProject findTestInProjectByResearchProjectIdAndLabTestId(int projectId, int testId) {
        return testInProjectRepository.findTestInProjectByResearchProjectIdAndLabTestId(projectId, testId);
    }

    @Override
    public void saveTestToProject(TestInProject testInProject) {
        testInProjectRepository.save(testInProject);
    }

    @Override
    public void deleteTestInProject(TestInProject testInProject) {
        testInProjectRepository.delete(testInProject);
    }

    @Override
    public List<TestInProject> findAllTestsByResearchProjectId(int id) {
        return testInProjectRepository.findAllByResearchProjectId(id);
    }
}
