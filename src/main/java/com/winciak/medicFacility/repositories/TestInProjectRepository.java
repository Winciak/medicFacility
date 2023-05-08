package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.TestInProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestInProjectRepository extends JpaRepository<TestInProject, Integer> {

    TestInProject findTestInProjectByResearchProjectIdAndLabTestId(int projectId, int testId);

    List<TestInProject> findAllByResearchProjectId(int id);

}
