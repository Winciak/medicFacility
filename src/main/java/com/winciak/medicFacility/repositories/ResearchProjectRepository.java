package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.ResearchProject;
import com.winciak.medicFacility.entities.TestInProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Integer> {

    ResearchProject findResearchProjectByName(String name);


}
