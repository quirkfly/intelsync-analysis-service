package com.cogneworx.intelsync.analysis.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.cogneworx.intelsync.analysis.model.PersonEntity;

@Repository
public interface PersonRepository extends ArangoRepository<PersonEntity, String> {

    // Custom query methods can be defined here if needed
    // For example, to find persons by caseId or name

    // Example:
    // List<PersonEntity> findByCaseId(String caseId);
    // List<PersonEntity> findByNameContaining(String namePart);

}
