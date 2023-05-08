package com.winciak.medicFacility.services;

import com.winciak.medicFacility.entities.LabTest;
import com.winciak.medicFacility.repositories.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTestServiceImpl implements LabTestService {

    private final LabTestRepository labTestRepository;

    @Autowired
    public LabTestServiceImpl(LabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    @Override
    public List<LabTest> findAll() {
        return labTestRepository.findAll();
    }

    @Override
    public LabTest findById(int id) {

        Optional<LabTest> result = labTestRepository.findById(id);

        LabTest labTest;

        if (result.isPresent()) {
            labTest = result.get();
        }
        else {
            throw new RuntimeException("Did not find lab test id - " + id);
        }

        return labTest;
    }

    @Override
    public void save(LabTest labTest) {
        labTestRepository.save(labTest);
    }

    @Override
    public void deleteById(int theId) {
        labTestRepository.deleteById(theId);
    }

    @Override
    public LabTest findByName(String name) {
        return labTestRepository.findByName(name);
    }
}
