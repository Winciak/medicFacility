package com.winciak.medicFacility.repositories;


import com.winciak.medicFacility.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    public Role findRoleByName(String role);
}
