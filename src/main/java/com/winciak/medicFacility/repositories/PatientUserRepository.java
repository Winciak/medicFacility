package com.winciak.medicFacility.repositories;

import com.winciak.medicFacility.entities.PatientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientUserRepository extends JpaRepository<PatientUser, Integer> {

    List<PatientUser> findAllByOrderByLastNameAsc();

    PatientUser findUserByLogin(String login);
}
