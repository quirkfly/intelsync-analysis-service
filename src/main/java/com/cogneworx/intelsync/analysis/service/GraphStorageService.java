package com.cogneworx.intelsync.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arangodb.springframework.core.ArangoOperations;

import com.cogneworx.intelsync.analysis.model.PersonEntity;
import com.cogneworx.intelsync.analysis.repository.PersonRepository;
import com.cogneworx.intelsync.analysis.dto.GraphEdge;
import com.cogneworx.intelsync.analysis.dto.GraphNode;

@Service
public class GraphStorageService {
    @Autowired
    private ArangoOperations arangoOperations;

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


    public Map<String, List<?>> fetchGraphForCase(String caseId) {
        List<GraphNode> nodes = new ArrayList<>();
        List<GraphEdge> edges = new ArrayList<>();

        // Fetch PersonEntities for the case
        List<PersonEntity> persons = arangoOperations.query(
            "FOR p IN persons FILTER p.caseId == @caseId RETURN p",
            Map.of("caseId", caseId),
            PersonEntity.class
        ).asListRemaining();

        for (PersonEntity person : persons) {
            nodes.add(new GraphNode(person.getId(), person.getName(), "Person"));
        }

        // (Optional) Fetch edges if implemented
        // Add mock edge logic here if needed

        return Map.of("nodes", nodes, "edges", edges);
    }
}