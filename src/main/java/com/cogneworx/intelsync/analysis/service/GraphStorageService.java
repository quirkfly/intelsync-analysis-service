package com.cogneworx.intelsync.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cogneworx.intelsync.analysis.model.PersonEntity;
import com.cogneworx.intelsync.analysis.repository.PersonRepository;


@Service
public class GraphStorageService {

    @Autowired
    private PersonRepository personRepository;

    public void storeEntities(List<String> persons, String caseId) {
        for (String name : persons) {
            PersonEntity entity = new PersonEntity();
            entity.setName(name);
            entity.setCaseId(caseId);
            personRepository.save(entity);
        }
    }
}
