package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.ConsentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentFileRepository extends JpaRepository<ConsentFile, Integer> {

}
