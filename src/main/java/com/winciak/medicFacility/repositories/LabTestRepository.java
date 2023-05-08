package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Integer> {

    LabTest findByName(String name);

}
